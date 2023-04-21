package com.ACGN.Service.Impl;

import com.ACGN.dao.UserMapper;
import com.ACGN.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",username);
        if(username==null){
            throw new UsernameNotFoundException("用户名不能为空");
        }
        User user=userMapper.selectOne(queryWrapper);
        if (user==null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).roles("User").build();

    }


}
