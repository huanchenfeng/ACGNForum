package com.ACGN.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
@Data
public class Comment {
    @TableId(value = "comment_id", type = IdType.AUTO)
    private int commentId;

    private int userId;

    private int replyUserId;

    private String username;

    private String replyNickName;

    private int type;

    private int discusspostId;

    private String content;
    /**
     * 上一级帖子Id
     **/
    private int preReply;

    private Date createTime;

    private int status;

    private String headerUrl;

    private int topType;
}
