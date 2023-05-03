package com.ACGN.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Comment {
    private int commentId;

    private String userId;

    private String username;

    private String discusspostId;

    private String reply;
    /**
     * 上一级帖子Id
     **/
    private int preReply;

    private Date createTime;
}
