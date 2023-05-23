package com.ACGN.controller;

import com.ACGN.Service.LikeService;
import com.ACGN.entity.Article;
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
    public R myAricle(String type,String id) throws IOException {

        int Id =Integer.parseInt(id);
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
        queryWrapper.eq("user_id",Id);
        queryWrapper.eq("type",Integer.parseInt(type));
        queryWrapper.eq("worksId",Integer.parseInt(worksId));
        int sum= likeService.count(queryWrapper);
        return RUtils.success(sum);
    }


    @PostMapping("/selectLike")
    @ResponseBody
    public R selectLike(String userId,String worksId,String type) throws IOException {
        int Id =Integer.parseInt(userId);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",Id);
        queryWrapper.eq("type",Integer.parseInt(type));
        queryWrapper.eq("worksId",Integer.parseInt(worksId));
        if(likeService.getOne(queryWrapper).getLikeId()==null){
            return RUtils.success(0);
        }
        return RUtils.success(1);
    }
}
