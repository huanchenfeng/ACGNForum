package com.ACGN.controller;

import com.ACGN.Service.LikeService;
import com.ACGN.entity.Article;
import com.ACGN.entity.Likes;
import com.ACGN.util.R;
import com.ACGN.util.RUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class LikeController {
    @Autowired
    LikeService likeService;
    @PostMapping("/likes")
    @ResponseBody
    public R myAricle(String type,String worksId) throws IOException {

        int Id =Integer.parseInt(worksId);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("works_id",Id);
        queryWrapper.eq("type",Integer.parseInt(type));
        int sum= likeService.count(queryWrapper);
        return RUtils.success(sum);
    }


    @PostMapping("/addLike")
    @ResponseBody
    public R addLike(String userId,String worksId,String type) throws IOException {

        int Id =Integer.parseInt(userId);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("works_id",Id);
        queryWrapper.eq("type",Integer.parseInt(type));
        if (likeService.getOne(queryWrapper)==null){
        Likes likes=new Likes();
        likes.setType(Integer.parseInt(type));
        likes.setUserId(Id);
        likes.setWorksId(Integer.parseInt(worksId));
        likeService.save(likes);
        return RUtils.success();}
        else {
            return RUtils.Err(400,"已点过赞");
        }
    }


    @PostMapping("/selectLike")
    @ResponseBody
    public R selectLike(String userId,String worksId,String type) throws IOException {
        int Id =Integer.parseInt(userId);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",Id);
        queryWrapper.eq("type",Integer.parseInt(type));
        queryWrapper.eq("works_id",Integer.parseInt(worksId));
        if(likeService.getOne(queryWrapper)==null){
            return RUtils.success(0);
        }
        return RUtils.success(1);
    }
}
