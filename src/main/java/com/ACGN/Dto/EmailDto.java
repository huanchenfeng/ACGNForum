package com.ACGN.Dto;

import lombok.Data;

import java.util.List;
@Data
public class EmailDto {

    /**
     * 发送邮箱列表
     */
    private List<String> tos;

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String content;

}
