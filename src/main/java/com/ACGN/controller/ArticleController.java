package com.ACGN.controller;

import com.ACGN.Dto.ArticleDto;
import com.ACGN.Dto.Board;
import com.ACGN.Dto.CommentDto;
import com.ACGN.Service.ArticleService;
import com.ACGN.Service.CommentService;
import com.ACGN.Service.UserService;
import com.ACGN.entity.Article;
import com.ACGN.entity.Comment;
import com.ACGN.entity.User;
import com.ACGN.util.R;
import com.ACGN.util.RUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class ArticleController {
    @Autowired
    public ArticleService articleService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @PostMapping("/index")
    @ResponseBody
    public R top(Integer current) {
        Page<Article> page=new Page<>();
        page.setCurrent(current);
        page.setSize(10);
        LambdaQueryWrapper<Article> queryWrapper = Wrappers.<Article>lambdaQuery();
        // 按score排序，若score相同，则按create_time排序
        queryWrapper.orderByDesc(Article::getScore);
        queryWrapper.orderByDesc(Article::getCreateTime);
        Page<Article> topPage;
        topPage=articleService.page(page, queryWrapper);
        return RUtils.success(topPage);

    }

    @PostMapping("/addArticle")
    @ResponseBody
    public R addArticle(
            @RequestPart("headerUrl") MultipartFile headerUrl,
            @RequestParam("content") String content,
            @RequestParam("title") String title,
            @RequestParam("summary") String summary,
            @RequestParam("type") String type,
            @RequestParam("userId") String userId
    ) {
        String name=saveFile(headerUrl);
        Article article=new Article();
        if(userId=="0"||content==null||title==null||summary==null){
            return RUtils.Err(400,"数据为空");
        }
        article.setUserId(Integer.parseInt(userId));
        article.setUsername(userService.getById(article.getUserId()).getUsername());
        article.setContent(content);
        article.setSynopsis(summary);
        article.setTitle(title);
        article.setCreateTime(new Date());
        article.setType(Integer.parseInt(type));
        article.setStatus(0);
        article.setScore(0);
        article.setHeaderUrl("http://localhost:8080/ACGN/images/"+name);
        articleService.save(article);
        return RUtils.success();
    }

    @PostMapping("/articleDetail")
    @ResponseBody
    public R articleDetail(String articleId) {
        if(articleId==null){
            return RUtils.Err(400,"数据为空");
        }
            Article article= articleService.getById(articleId);
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        // 设置更新条件，这里假设你要根据文章的id进行更新
            updateWrapper.eq("article_id", articleId)

        .setSql("read_count = read_count + 1, score = score + 0.5");

        articleService.update(updateWrapper);
        String headerUrl=userService.getById(article.getUserId()).getHeaderUrl();
            // 将文章和头像URL存放在一个Map中
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("article", article);
            resultMap.put("headerUrl", headerUrl);
            return RUtils.success(resultMap);

    }

    @PostMapping("/article")
    @ResponseBody
    public R article() {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.groupBy("type");
        queryWrapper.select("type,count(*) as sum");
        queryWrapper.eq("status",0);
        return RUtils.success(articleService.listMaps(queryWrapper));
    }

    @PostMapping("/articleType")
    @ResponseBody
    public R articleType(String type,Integer current) {
        int Type=Integer.parseInt(type);
        Page<Article> page=new Page<>();
        page.setCurrent(current);
        page.setSize(10);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("status",0);
        queryWrapper.eq("type",Type);
        // 按score排序，若score相同，则按create_time排序
        queryWrapper.orderByDesc("score");

        Page<Article> articlePage;
        articlePage=articleService.page(page, queryWrapper);
        return RUtils.success(articlePage);
}

    @PostMapping("/articleComment")
    @ResponseBody
    public R articleComment(String articleId,Integer current) {
        int ArticleId=Integer.parseInt(articleId);
        Page<Comment> page=new Page<>();
        if(current==null){
            current=1;
        }
        page.setCurrent(current);
        page.setSize(10);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("status",0);
        queryWrapper.eq("type",1);
        queryWrapper.eq("discusspost_id",ArticleId);
        Page<Comment> commentPage;
        commentPage=commentService.page(page, queryWrapper);
        List<Comment> commentList=commentPage.getRecords();
        List<CommentDto> commentDtoList=buildCommentList(commentList);
        Page<CommentDto> commentDtoPage =new Page<>();
        commentDtoPage.setCurrent(current);
        commentDtoPage.setSize(50);
        commentDtoPage.setTotal(commentPage.getTotal());
        commentDtoPage.setPages(commentPage.getPages());
        commentDtoPage.setRecords(commentDtoList);
        return RUtils.success(commentDtoPage);
    }



    public List<CommentDto> buildCommentList(List<Comment> comments) {
        List<CommentDto> result = new ArrayList<>();

        // 构建评论ID与评论对象的映射关系
        Map<Integer, CommentDto> commentMap = new HashMap<>();
        for (Comment comment : comments) {
            CommentDto commentDto = convertToCommentDto(comment);
            commentMap.put(comment.getCommentId(), commentDto);
        }

        // 构建评论树结构
        for (Comment comment : comments) {
            int preReply = comment.getPreReply();
            if (preReply == -1) {
                CommentDto commentDto = commentMap.get(comment.getCommentId());
                // 顶层评论直接添加到结果列表中
                result.add(commentDto);
            } else {
                CommentDto parent = commentMap.get(preReply);
                if (parent != null) {
                    CommentDto commentDto = commentMap.get(comment.getCommentId());
                    // 将子评论添加到父评论的子评论列表中
                    parent.getChildren().add(commentDto);
                    // 递归构建子评论树
                    buildCommentTree(commentDto, commentMap);
                }
            }
        }

        return result;
    }

    private void buildCommentTree(CommentDto commentDto, Map<Integer, CommentDto> commentMap) {
        List<CommentDto> children = new ArrayList<>();
        for (CommentDto child : commentDto.getChildren()) {
            CommentDto childDto = commentMap.get(child.getCommentId());
            children.add(childDto);
            // 递归构建子评论树
            buildCommentTree(childDto, commentMap);
        }
        commentDto.setChildren(children);
    }

    private CommentDto convertToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(comment.getCommentId());
        commentDto.setUserId(comment.getUserId());
        commentDto.setReplyUserId(comment.getReplyUserId());
        commentDto.setUsername(comment.getUsername());
        commentDto.setReplyNickName(comment.getReplyNickName());
        commentDto.setContent(comment.getContent());
        commentDto.setType(comment.getType());
        commentDto.setDiscusspostId(comment.getDiscusspostId());
        commentDto.setStatus(comment.getStatus());
        commentDto.setPreReply(comment.getPreReply());
        commentDto.setCreateTime(comment.getCreateTime());
        // 初始化子评论列表为空
        commentDto.setChildren(new ArrayList<>());
        commentDto.setTopType(comment.getTopType());
        commentDto.setHeaderUrl(comment.getHeaderUrl());

        return commentDto;
    }
    public String getSavePath() {
        // 这里需要注意的是ApplicationHome是属于SpringBoot的类
        // 获取项目下resources/static/img路径
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());

        // 保存目录位置根据项目需求可随意更改
        return applicationHome.getDir().getParentFile()
                .getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\images";
    }
    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空！";
        }
        // 给文件重命名
        String fileName = UUID.randomUUID() + "." + file.getContentType()
                .substring(file.getContentType().lastIndexOf("/") + 1);
        try {
            // 获取保存路径
            String path = getSavePath();
            System.out.println(path+"-----------------------------------------------------------------------------------------------------------");
            File files = new File(path, fileName);
            File parentFile = files.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdir();
            }
            file.transferTo(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName; // 返回重命名后的文件名
    }
    @PostMapping("/article/loadBoard4Post")
    @ResponseBody
    public R loadBoard() {
        Board board1=new Board(0,"漫画");
        Board board2=new Board(1,"动画");
        Board board3=new Board(2,"游戏");
        Board board4=new Board(3,"轻小说");
        List<Board> boardList=new ArrayList<>();
        boardList.add(board1);
        boardList.add(board2);
        boardList.add(board3);
        boardList.add(board4);
        return RUtils.success(boardList);
    }
    @PostMapping("/articleWrite/uploadImage")
    @ResponseBody
    public R uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 获取静态目录的路径
            Path staticDirectory = Paths.get("src/main/static/resources/static/images/");

            // 创建保存图片的目标路径
            Path targetPath = staticDirectory.resolve(file.getOriginalFilename());

            // 保存图片到目标路径
            Files.copy(file.getInputStream(), targetPath);

            // 返回图片的URL，可以根据实际情况进行拼接
            String imageUrl = "http://localhost:8080/ACGN/images/" + file.getOriginalFilename();

            return RUtils.success(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return RUtils.Err(404,"错误");
        }
    }
}
