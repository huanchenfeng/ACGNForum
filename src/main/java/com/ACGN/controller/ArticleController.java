package com.ACGN.controller;

import com.ACGN.Dto.ArticleDto;
import com.ACGN.Service.ArticleService;
import com.ACGN.Service.UserService;
import com.ACGN.entity.Article;
import com.ACGN.util.R;
import com.ACGN.util.RUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class ArticleController {
    @Autowired
    public ArticleService articleService;
    @Autowired
    private UserService userService;
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
    public R addarticle(@RequestBody ArticleDto articleDto) {
        Article article=new Article();
        if(articleDto.getUserId()==0||articleDto.getContent()==null||articleDto.getTitle()==null||articleDto.getSynopsis()==null){
            return RUtils.Err(400,"数据为空");
        }
        article.setUserId(articleDto.getUserId());
        article.setUsername(userService.getById(article.getUserId()).getUsername());
        article.setContent(articleDto.getContent());
        article.setSynopsis(articleDto.getSynopsis());
        article.setTitle(articleDto.getTitle());
        article.setCreateTime(new Date());
        article.setType(0);
        article.setStatus(0);
        article.setScore(0);
        articleService.save(article);
        return RUtils.success();
    }

    @PostMapping("/articleDetail")
    @ResponseBody
    public R articleDetail(String articleId) {
        if(articleId==null){
            return RUtils.Err(400,"数据为空");
        }
        System.out.println(articleId);
        int id=Integer.parseInt(articleId);
        Article article=articleService.getById(id);
        return RUtils.success(article);
    }

    @PostMapping("/article")
    @ResponseBody
    public R articleType() {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.groupBy("type");
        queryWrapper.select("type,count(*) as sum");
        queryWrapper.eq("status",0);
        return RUtils.success(articleService.listMaps(queryWrapper));
    }
}
