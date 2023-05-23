package com.ACGN.controller;

import com.ACGN.Service.ArticleService;
import com.ACGN.Service.DiscussPostService;
import com.ACGN.entity.Article;
import com.ACGN.entity.Comment;
import com.ACGN.entity.DiscussPost;
import com.ACGN.entity.User;
import com.ACGN.util.R;
import com.ACGN.util.RUtils;
import com.ACGN.util.Renum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class SearchContrller {
    @Autowired
    ArticleService articleService;
    @Autowired
    DiscussPostService discussPostService;
    @PostMapping("/search")
    @ResponseBody
    public R search(String searchKey, int Type){
        System.out.println(searchKey+"-----------------------------------------------------------");
        if(Type==0){
            Page<Article> page=new Page<>();
            page.setCurrent(1);
            page.setSize(10);
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.like("title", searchKey); // 根据标题字段进行模糊匹配
            return RUtils.success(articleService.page(page,queryWrapper));
        }
        else {
            Page<DiscussPost> page=new Page<>();
            page.setCurrent(1);
            page.setSize(10);
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.like("title", searchKey); // 根据标题字段进行模糊匹配
            return RUtils.success(discussPostService.page(page,queryWrapper));
        }
    }
}
