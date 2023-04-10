package com.ACGN.controller;

import com.ACGN.Service.DiscussPostService;
import com.ACGN.entity.Discusspost;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TopDiscussPostController {


    @Autowired
    private DiscussPostService discussPostService;


    @GetMapping("/top")
    @ResponseBody
    public Page<Discusspost> wrapper(int current) {
        Page<Discusspost> page=new Page<>();
        page.setCurrent(current);
        page.setSize(10);
        LambdaQueryWrapper<Discusspost> queryWrapper = Wrappers.<Discusspost>lambdaQuery();
        // 按score排序，若score相同，则按create_time排序
        queryWrapper.orderByDesc(Discusspost::getScore);
        queryWrapper.orderByDesc(Discusspost::getCreateTime);

        return discussPostService.page(page, queryWrapper);

    }



}
