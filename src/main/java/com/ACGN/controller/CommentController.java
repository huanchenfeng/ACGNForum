package com.ACGN.controller;

import com.ACGN.Dto.AddCommentDto;
import com.ACGN.Service.CommentService;
import com.ACGN.Service.UserService;
import com.ACGN.entity.Comment;
import com.ACGN.util.R;
import com.ACGN.util.RUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;



@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @PostMapping("/addComment")
    @ResponseBody
    public R addComment(AddCommentDto addCommentDto){
        Comment comment=new Comment();
        if(addCommentDto.getReplyUserId()==null){
            comment.setPreReply(-1);
        }
        else {
            comment.setReplyNickName(userService.getById(Integer.valueOf(addCommentDto.getReplyUserId())).getUsername());
            comment.setReplyUserId(Integer.parseInt(addCommentDto.getReplyUserId()));
            comment.setPreReply(addCommentDto.getCommentId());
        }
        comment.setContent(addCommentDto.getContent());
        comment.setUserId(Integer.parseInt(addCommentDto.getUserId()));
        comment.setCreateTime(new Date());
        comment.setHeaderUrl(addCommentDto.getHeaderUrl());
        comment.setDiscusspostId(Integer.parseInt(addCommentDto.getDiscusspostId()));
        comment.setStatus(0);
        comment.setTopType(0);
        comment.setType(addCommentDto.getType());
        comment.setUsername(userService.getById(String.valueOf(addCommentDto.getUserId())).getUsername());
        commentService.save(comment);
        return RUtils.success();
    }
    @PostMapping("/commentCounts")
    @ResponseBody
    public R myAricle(String type,String id) throws IOException {

        int Id =Integer.parseInt(id);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("discusspost_id",Id);
        queryWrapper.eq("type",Integer.parseInt(type));
        int sum= commentService.count();
        return RUtils.success(sum);
    }
}
