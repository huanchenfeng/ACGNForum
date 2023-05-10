package com.ACGN.controller;

import com.ACGN.Service.AttentionService;
import com.ACGN.entity.DiscussPost;
import com.ACGN.util.R;
import com.ACGN.util.RUtils;
import com.ACGN.util.Renum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AttentionController {
    @Autowired
    AttentionService attentionService;

    /**
     * 我的粉丝
     * @param userId
     * @return
     */
    @PostMapping("/myFans")
    @ResponseBody
    public R myFans(String userId){
        int id =Integer.parseInt(userId);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("focus_user_id",id);
        int count = attentionService.count(queryWrapper);
        return RUtils.success(count);
}

    /**
     * 我的关注
     * @param userId
     * @return
     */
    @PostMapping("/myConcern")
    @ResponseBody
    public R myConcern(String userId){
        int id =Integer.parseInt(userId);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",id);
        int count = attentionService.count(queryWrapper);
        return RUtils.success(count);
    }
}

