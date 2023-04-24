package com.ACGN.Service.Impl;
import com.ACGN.Service.UserService;
import com.ACGN.dao.UserMapper;
import com.ACGN.entity.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
   public UserMapper userMapper;



}
