package com.ACGN.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Comment {
    private int commentId;

    private int userId;

    private String username;

    private String content;

    private int discusspostId;

    private int reply;
    /**
     * 上一级帖子Id
     **/
    private int preReply;

    private Date createTime;
}
