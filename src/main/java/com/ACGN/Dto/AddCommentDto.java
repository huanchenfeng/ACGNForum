package com.ACGN.Dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AddCommentDto {
    private int commentId;

    private String userId;

    private String replyUserId;

    private String content;

    private int type;
    /**
     * 回复的文章或论坛帖子Id
     * **/
    private String discusspostId;

    private String headerUrl;
}
