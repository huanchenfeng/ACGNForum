package com.ACGN.Dto;

import com.ACGN.entity.Comment;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentDto {
    private int commentId;

    private int userId;

    private int replyUserId;

    private String username;

    private String replyNickName;

    private String content;

    private int type;
    /**
     * 回复的文章或论坛帖子Id
     * **/
    private int discusspostId;

    private int status;

    /**
     * 上一级帖子Id
     **/
    private int preReply;

    private Date createTime;

    private int topType;

    private String headerUrl;

    List<CommentDto> children;
}
