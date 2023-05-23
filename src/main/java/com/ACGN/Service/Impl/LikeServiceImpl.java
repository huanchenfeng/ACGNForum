package com.ACGN.Service.Impl;
import com.ACGN.Service.LikeService;
import com.ACGN.dao.LikeMapper;
import com.ACGN.entity.Likes;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Likes> implements LikeService {
}
