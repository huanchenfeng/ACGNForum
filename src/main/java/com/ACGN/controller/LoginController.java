package com.ACGN.controller;

import com.ACGN.Service.UserService;
import com.ACGN.entity.User;
import com.ACGN.util.R;
import com.ACGN.util.RUtils;
import com.ACGN.util.Renum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/regist")
    @ResponseBody
    public R regist(User user){

            // 空值处理
            if(user == null){
                try {
                    throw new IllegalAccessException("参数不能为空！");
                } catch (IllegalAccessException e) {

                }
            }
            // 空值处理
            if(StringUtils.isBlank(user.getUsername())){
                return RUtils.Err(200, "账号不能为空!");
            }

            if(StringUtils.isBlank(user.getPassword())){
                return RUtils.Err(200, "密码不能为空!");
            }
            QueryWrapper queryWrapper=new QueryWrapper();
            //验证手机或邮箱是否为空
            if(StringUtils.isBlank(user.getEmail())&&user.getPhone()==0){
                if(StringUtils.isBlank(user.getEmail())){
                    return RUtils.Err(200,"未输入邮箱");
                }
                else {
                    return RUtils.Err(200,"未输入手机号码");
                }
        }
            else{
                if (StringUtils.isBlank(user.getEmail())){
                    queryWrapper.eq("phone",user.getPhone());
                    if(userService.getOne(queryWrapper)!=null){
                        return RUtils.Err(200,"重复的手机号");
                    };
                }
                else{
                    queryWrapper.eq("email",user.getEmail());
                    if(userService.getOne(queryWrapper)!=null){
                        return RUtils.Err(200,"重复的邮箱");
                    };
                }
            }
            user.setSalt("1111");
            user.setActivationCode("1111");
            user.setHeaderUrl("11111");
            user.setCreateTime(new Date());
        if (userService.save(user)){
            return RUtils.success();}
        return RUtils.Err(Renum.USER_IS_EXISTS.getCode(),Renum.USER_IS_EXISTS.getMsg());
    }
    @PostMapping("/login")
    @ResponseBody
    public R login(String username,String password){
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        if (userService.getOne(queryWrapper)==null){
            return RUtils.Err(200,"账号密码错误");
        }
        else{
            return RUtils.success();}
        }

    }

