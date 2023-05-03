package com.ACGN.Service.Impl;

import com.ACGN.Service.ArticleService;
import com.ACGN.dao.ArticleMapper;
import com.ACGN.entity.Article;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
