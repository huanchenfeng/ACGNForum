package com.ACGN.Dto;

import lombok.Data;

@Data
public class DiscussPostDto {
    private int userId;
    private String title;
    private String content;
    private int discusspostType;
}
