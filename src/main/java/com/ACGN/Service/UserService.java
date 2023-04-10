package com.ACGN.Service;

import com.ACGN.dao.UserMapper;
import com.ACGN.entity.User;
import com.ACGN.util.CommunityUtil;
import com.ACGN.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UserService {
    @Autowired
    private UserMapper userMapper;
//    @Autowired
//    private TemplateEngine templateEngine;
    @Autowired
    private MailClient mailClient;
    /**
     * 域名
     */
    @Value("${community.path.domain}")
    private String domain;

    /**
     * 项目名
     */
    @Value("${server.servlet.context-path}")
    private String contextPath;


    public Map<String,Object> register(User user){
        Map<String,Object> map=new HashMap<>();
        //空值处理
        if(user==null){
            throw new IllegalArgumentException("参数不能为空！");
        }
        if(StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg","账号不能为空");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg","密码不能为空");
            return map;
        }
        if(StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg","邮箱不能为空");
            return map;
        }
        //验证账号
        User u=userMapper.selectByName(user.getUsername());
        if(u != null){
            map.put("usernameMsg","该用户名已被注册");
            return map;
        }
        u=userMapper.selectByEmail(user.getEmail());
        if(u != null){
            map.put("emailMsg","该邮箱已被注册");
            return map;
        }
        //注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowvoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);
//        //激活邮件
//        Context context=new Context();
//        context.setVariable("email",user.getEmail());
//        String url=domain+contextPath+"/activation/"+user.getId()+"/"+user.getActivationCode();
//        context.setVariable("url",url);
//        String content=templateEngine.process("/mail/activation",context);
//        mailClient.sendMail(user.getEmail(),"激活账号",content);
        return map;
    }

}
