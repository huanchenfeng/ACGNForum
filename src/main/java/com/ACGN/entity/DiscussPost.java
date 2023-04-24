package com.ACGN.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
@Data
public class DiscussPost {
    @TableId(value = "discusspost_id", type = IdType.AUTO)
    private int discusspostId;

    private String userId;
    private String title;
    private String content;
    private int type;
    private int status;
    private int commentCount;
    private double score;
    private Date createTime;
    private int discusspostType;
}
