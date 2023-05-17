package com.ACGN.Service.Impl;

import com.ACGN.Service.AuthorizeService;
import com.ACGN.dao.UserMapper;
import com.ACGN.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserDetailsServiceImpl implements AuthorizeService {
    @Value("${spring.mail.username}")
    String from;
    @Autowired
    public UserMapper userMapper;
    @Resource
    MailSender mailSender;
    @Resource
    StringRedisTemplate template;
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
        return org.springframework.security.core.userdetails.User.withUsername(String.valueOf(user.getId())).password(user.getPassword()).roles("User").build();

    }
    /**
     * 1.生成相关验证码
     * 2.把邮箱和对应的验证码放进Redis(过期时间三分钟)
     * 3.发送验证码到指定邮箱
     * 4.发送失败吧Redis中的验证码删除
     * 5.注册时取出键值对，查看验证码
     */
    @Override
    public boolean sendValidateEmail(String email,String sessionId){
        String key="email"+sessionId+":"+email;
        if(Boolean.TRUE.equals(template.hasKey(key))){
            Long expire= Optional.ofNullable(template.getExpire(key,TimeUnit.SECONDS)).orElse(0L);
            if(expire > 120){
                return false;
            }
        }
        Random random=new Random();
        int code=random.nextInt(899999)+10000;
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("您的验证邮件");
        message.setText("验证码为："+code);
        try{
            template.opsForValue().set(key,String.valueOf(code),3, TimeUnit.MINUTES);
            mailSender.send(message);
            return  true;
        }catch (MailException e){
            e.printStackTrace();
            return  false;
        }

    }
}
