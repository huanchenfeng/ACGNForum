package com.ACGN.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
@Data
public class Evaluation {
    @TableId(value = "evaluation_id", type = IdType.AUTO)
    private Integer evaluationId;

    private Integer worksId;

    private Double scores;

    private String content;

    private Integer userId;

}