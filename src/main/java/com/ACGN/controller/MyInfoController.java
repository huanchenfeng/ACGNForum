package com.ACGN.controller;

import com.ACGN.Dto.UserInfoDto;
import com.ACGN.Service.ArticleService;
import com.ACGN.Service.DiscussPostService;
import com.ACGN.Service.LikeService;
import com.ACGN.Service.UserService;
import com.ACGN.entity.Article;
import com.ACGN.entity.DiscussPost;
import com.ACGN.entity.User;
import com.ACGN.util.R;
import com.ACGN.util.RUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
public class MyInfoController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private LikeService likeService;
    @PostMapping("/myInfo")
    @ResponseBody
    public R selectMyInfo(String userId) throws IOException {

        int id =Integer.parseInt(userId);
        User user=userService.getById(id);
        UserInfoDto userInfo=new UserInfoDto();
        userInfo.setCreateTime(user.getCreateTime());
        userInfo.setUsername(user.getUsername());
        userInfo.setPhone(user.getPhone());
        userInfo.setEmail(user.getEmail());
        userInfo.setActivationCode(user.getActivationCode());
        userInfo.setSex(user.getSex());
        userInfo.setDress(user.getDress());
        userInfo.setSignature(user.getSignature());
        userInfo.setAuthor(user.getAuthor());
        userInfo.setHeaderUrl(user.getHeaderUrl());
        userInfo.setType(user.getType());
        return RUtils.success(userInfo);
    }

    @PostMapping("/myArticle")
    @ResponseBody
    public R myAricle(String userId) throws IOException {

        int id =Integer.parseInt(userId);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",id);
        List<Article> articleList= articleService.list(queryWrapper);
        return RUtils.success(articleList);
    }
    @PostMapping("/myDiscussPost")
    @ResponseBody
    public R myDiscussPost(String userId) throws IOException {

        int id =Integer.parseInt(userId);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",id);
        List<DiscussPost> discussPostList= discussPostService.list(queryWrapper);
        return RUtils.success(discussPostList);
    }

    @PostMapping("/updataUser")
    @ResponseBody
    public R updataUser(String userId,String sex,String dress,String signature) throws IOException {
        UpdateWrapper updateWrapper=new UpdateWrapper();

        updateWrapper.set("sex",sex);
        updateWrapper.set("dress",dress);
        updateWrapper.set("signature",signature);
        updateWrapper.eq("user_id",Integer.parseInt(userId));
        userService.update(updateWrapper);
        return RUtils.success();
    }
    @PostMapping("/myLike")
    @ResponseBody
    public R myLike(String userId) throws IOException {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_Id",Integer.parseInt(userId));
        queryWrapper.eq("type",2);
        int sum=likeService.count(queryWrapper);
        return RUtils.success(sum);
    }
    @PostMapping("/Audit")
    @ResponseBody
    public R myAudit() throws IOException {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("status",1);
        List<Article> articleList= articleService.list(queryWrapper);
        return RUtils.success(articleList);
    }

    @PostMapping("/myDeleteArticle")
    @ResponseBody
    public R myDeleteArticle(String articleId) throws IOException {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("article_id",Integer.parseInt(articleId));
        articleService.remove(queryWrapper);
        return RUtils.success();
    }
}
