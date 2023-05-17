package com.ACGN.dao;

import com.ACGN.Dto.CommentDto;
import com.ACGN.entity.Article;
import com.ACGN.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<CommentDto> getComment(int articleId);
}
