package com.ACGN.controller;

import com.ACGN.Service.AuthorizeService;
import com.ACGN.Service.ProductionService;
import com.ACGN.entity.Comment;
import com.ACGN.entity.Production;
import com.ACGN.entity.User;
import com.ACGN.util.R;

import com.ACGN.util.RUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ProductionController {
    @Autowired
    private ProductionService productionService;


    @PostMapping("/production")
    @ResponseBody
    public R production(String type,int current){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("type",type);
        Page<Production> page=new Page<>();
        page.setCurrent(current);
        page.setSize(10);
        page=productionService.page(page,queryWrapper);
        return RUtils.success(page);
    }
}
