package com.ACGN.controller;

import com.ACGN.Dto.ClassificationDto;
import com.ACGN.Dto.CommentDto;
import com.ACGN.Dto.DiscussPostDto;
import com.ACGN.Service.CommentService;
import com.ACGN.Service.DiscussPostService;
import com.ACGN.Service.UserService;
import com.ACGN.entity.Article;
import com.ACGN.entity.Comment;
import com.ACGN.entity.DiscussPost;
import com.ACGN.util.R;
import com.ACGN.util.RUtils;
import com.ACGN.util.Renum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    /**
     * 讨论帖分类
     * @param type
     * @return
     */
    @PostMapping("/classifiedDetails")
    @ResponseBody
    public R ClassifiedPost(String type,int current){
        int Type=Integer.parseInt(type);
        Page<DiscussPost> classifiedPage;
        /**
         * 置顶帖的数量
         */
        int sum=0;
        QueryWrapper queryWrapperOne=new QueryWrapper();
        queryWrapperOne.eq("discusspost_type",type);
        queryWrapperOne.eq("status",0);
        queryWrapperOne.eq("type",1);
        queryWrapperOne.orderByDesc("create_time");
        HashMap map=new HashMap();
        List<DiscussPost>list=discussPostService.list(queryWrapperOne);
        sum=list.size();
        QueryWrapper queryWrapperTwo=new QueryWrapper();
        queryWrapperTwo.eq("discusspost_type",type);
        queryWrapperTwo.eq("status",0);
        queryWrapperTwo.orderByDesc("create_time");
        Page<DiscussPost> pageTwo=new Page<>();
        pageTwo.setCurrent(current);
        pageTwo.setSize(10-sum);
        classifiedPage=discussPostService.page(pageTwo,queryWrapperTwo);
        map.put("normal",classifiedPage);
        map.put("top",list);
        if(classifiedPage!=null){
            return RUtils.success(map);
        }
            return RUtils.Err(Renum.DATA_IS_NULL.getCode(),Renum.DATA_IS_NULL.getMsg());
    }


    @PostMapping("/top")
    @ResponseBody
    public R top(Integer current) {
        Page<DiscussPost> page=new Page<>();
        page.setCurrent(current);
        page.setSize(10);
        LambdaQueryWrapper<DiscussPost> queryWrapper = Wrappers.<DiscussPost>lambdaQuery();
        // 按score排序，若score相同，则按create_time排序
        queryWrapper.orderByDesc(DiscussPost::getScore);
        queryWrapper.orderByDesc(DiscussPost::getCreateTime);
        Page<DiscussPost> topPage;
        topPage=discussPostService.page(page, queryWrapper);
        return RUtils.success(topPage);

    }

    @PostMapping("/addDiscussPost")
    @ResponseBody
    public R addDiscussPost(@RequestBody DiscussPostDto discussPostDto) {
        DiscussPost discussPost=new DiscussPost();
        System.out.println(discussPostDto.toString());
        if(discussPostDto.getUserId()==0||discussPostDto.getContent()==null||discussPostDto.getTitle()==null){
            return RUtils.Err(400,"数据为空");
        }
        discussPost.setUserId(discussPostDto.getUserId());
        discussPost.setUsername(userService.getById(discussPost.getUserId()).getUsername());
        discussPost.setContent(discussPostDto.getContent());
        discussPost.setTitle(discussPostDto.getTitle());
        discussPost.setCommentCount(0);
        discussPost.setCreateTime(new Date());
        discussPost.setType(0);
        discussPost.setStatus(0);
        discussPost.setDiscusspostType(discussPostDto.getDiscusspostType());
        discussPost.setScore(0);
        discussPost.setHeaderUrl(userService.getById(discussPost.getUserId()).getHeaderUrl());
        discussPostService.save(discussPost);
        return RUtils.success();
    }
    @PostMapping("/discussPostDetail")
    @ResponseBody
    public R discussPostDetail(String discusspostId) {
        int id=Integer.parseInt(discusspostId);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("status",0);
        queryWrapper.eq("discusspost_id",id);
        DiscussPost discussPost=discussPostService.getOne(queryWrapper);
        UpdateWrapper<DiscussPost> updateWrapper = new UpdateWrapper<>();
        // 设置更新条件，这里假设你要根据文章的id进行更新
        updateWrapper.eq("discusspost_id", discusspostId)
                .setSql("read_count = read_count + 1, score = score + 0.5");
        discussPostService.update(updateWrapper);
        return RUtils.success(discussPost);
    }
    @PostMapping("/discussPostComment")
    @ResponseBody
    public R articleComment(String discusspostId) {
        int DiscusspostId=Integer.parseInt(discusspostId);

        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("status",0);
        queryWrapper.eq("type",0);
        queryWrapper.eq("discusspost_id",DiscusspostId);
        List<Comment> commentList=commentService.list(queryWrapper);
        List<CommentDto> commentDtoList=buildCommentList(commentList);
        return RUtils.success(commentDtoList);
    }

    @PostMapping("/classification")
    @ResponseBody
    public R classification() {
       QueryWrapper queryWrapper=new QueryWrapper();
       queryWrapper.groupBy("discusspost_type");
       queryWrapper.select("discusspost_type,count(*) as sum");
       queryWrapper.eq("status",0);
       return RUtils.success(discussPostService.listMaps(queryWrapper));
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
}