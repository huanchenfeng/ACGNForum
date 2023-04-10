package com.ACGN.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Discusspost {

    private int discusspostId;
    private String userId;
    private String title;
    private String content;
    private int type;
    private int status;
    private int commentCount;
    private double score;
    private Date createTime;
}
