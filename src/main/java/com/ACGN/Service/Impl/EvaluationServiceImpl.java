package com.ACGN.Service.Impl;


import com.ACGN.Service.EvaluationService;

import com.ACGN.dao.EvaluationMapper;

import com.ACGN.entity.Evaluation;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationMapper, Evaluation> implements EvaluationService {
}
