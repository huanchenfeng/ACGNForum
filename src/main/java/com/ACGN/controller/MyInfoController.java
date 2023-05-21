package com.ACGN.controller;

import com.ACGN.Dto.UserInfoDto;
import com.ACGN.Service.ArticleService;
import com.ACGN.Service.UserService;
import com.ACGN.entity.Article;
import com.ACGN.entity.User;
import com.ACGN.util.R;
import com.ACGN.util.RUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        return RUtils.success(userInfo);
    }

    @PostMapping("/myAricle")
    @ResponseBody
    public R myAricle(String userId) throws IOException {

        int id =Integer.parseInt(userId);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",id);
        List<Article> articleList= articleService.list(queryWrapper);
        return RUtils.success(articleList);
    }
}
