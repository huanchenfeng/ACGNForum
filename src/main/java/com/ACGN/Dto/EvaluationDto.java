package com.ACGN.Dto;

import lombok.Data;

@Data
public class EvaluationDto {
    private Integer evaluationId;

    private Integer worksId;

    private Double scores;

    private String content;

    private Integer userId;

    private String username;

    private String headerUrl;
}
