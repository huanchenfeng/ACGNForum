package com.ACGN.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Production {
    @TableId(value = "production_id", type = IdType.AUTO)
    private Integer productionId;

    private String name;

    private String content;

    private String headerUrl;

    private String year;

    private Integer type;

}