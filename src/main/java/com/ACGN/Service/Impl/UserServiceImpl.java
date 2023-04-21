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


    public boolean sendValidateEmail(String email){
        /**
         * 1.生成相关验证码
         * 2.吧邮箱和对应的验证码放进Redis(过期时间三分钟)
         * 3.发送验证码到指定邮箱
         * 4.发送失败吧Redis中的验证码删除
         * 5.注册时取出键值对，查看验证码
         */
        return  false;
    }
}
