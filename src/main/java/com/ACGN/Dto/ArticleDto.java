package com.ACGN.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

@Data
public class ArticleDto {

    private int userId;

    private int type;

    private String content;

    private MultipartFile headerUrl;

    private String title;

    private String synopsis;


}
