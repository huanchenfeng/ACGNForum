package com.ACGN.Service.Impl;

import com.ACGN.Service.AttentionService;
import com.ACGN.dao.AttentionMapper;
import com.ACGN.entity.Attention;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AttentionServiceImpl extends ServiceImpl<AttentionMapper, Attention> implements AttentionService {
}
