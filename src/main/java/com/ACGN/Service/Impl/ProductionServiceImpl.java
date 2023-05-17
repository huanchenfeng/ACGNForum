package com.ACGN.Service.Impl;

import com.ACGN.Service.ProductionService;
import com.ACGN.dao.ProductionMapper;
import com.ACGN.entity.Production;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductionServiceImpl extends ServiceImpl<ProductionMapper, Production> implements ProductionService {
}
