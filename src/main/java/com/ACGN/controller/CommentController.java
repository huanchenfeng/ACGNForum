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
        System.out.println(addCommentDto.toString()+"-----------------------------------");
        Comment comment=new Comment();
        if(addCommentDto.getReplyUserId()==""){
            comment.setPreReply(-1);
        }
        else {
            comment.setReplyNickName(userService.getById(String.valueOf(addCommentDto.getReplyUserId())).getUsername());
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
}
