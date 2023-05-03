package com.ACGN.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Article {
    @TableId(value = "article_id", type = IdType.AUTO)
    private int articleId;

    private String username;

    private int userId;

    private int type;
    /**
     * 是否可用
     */
    private int status;

    private Date createTime;

    private String content;

    private double score;

    private String title;

    private String synopsis;
}
