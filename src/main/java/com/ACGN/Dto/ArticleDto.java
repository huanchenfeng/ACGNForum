package com.ACGN.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleDto {

    private int userId;

    private int type;

    private String content;

    private String title;

    private String synopsis;

}
