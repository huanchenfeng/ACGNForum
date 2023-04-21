package com.ACGN.Service.Impl;

import com.ACGN.Service.CommentService;
import com.ACGN.dao.CommentMapper;
import com.ACGN.entity.Comment;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl  extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
