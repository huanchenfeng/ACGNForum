package com.ACGN.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Like {
    @TableId(value = "like_id", type = IdType.AUTO)
    private Integer likeId;

    private Integer userId;

    private Integer worksId;

    private Integer type;

}