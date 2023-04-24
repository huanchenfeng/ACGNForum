package com.ACGN.controller;

import com.ACGN.Service.AuthorizeService;
import com.ACGN.Service.Impl.UserServiceImpl;
import com.ACGN.Service.UserService;
import com.ACGN.entity.User;
import com.ACGN.util.R;
import com.ACGN.util.RUtils;
import com.ACGN.util.Renum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Controller
public class RegistController {

    private  final String EMAIL_REGEX="^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AuthorizeService authorizeService;


    @PostMapping("/regist")
    @ResponseBody
    public R regist(User user){
            R r =duplicateVerification(user);
            if(r.getCode()!=200){
                return r;
            }
            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setActivationCode("1111");
            user.setHeaderUrl("11111");
            user.setCreateTime(new Date());
        if (userService.save(user)){
            return RUtils.success();}
        return RUtils.Err(Renum.USER_IS_EXISTS.getCode(),Renum.USER_IS_EXISTS.getMsg());
    }


        public R duplicateVerification(User user){

            // 空值处理
            if(user == null){
                try {
                    throw new IllegalAccessException("参数不能为空！");
                } catch (IllegalAccessException e) {

                }
            }
            // 空值处理
            if(StringUtils.isBlank(user.getUsername())){
                return RUtils.Err(400, "账号不能为空!");
            }
            else{
                QueryWrapper queryWrapper=new QueryWrapper();
                queryWrapper.eq("username",user.getUsername());
                if(userService.getOne(queryWrapper)!=null){
                    return RUtils.Err(400,"重复的用户名");
                }
            }

            if(StringUtils.isBlank(user.getPassword())){
                return RUtils.Err(400, "密码不能为空!");
            }
            //验证手机或邮箱是否为空
            if(StringUtils.isBlank(user.getEmail())&&StringUtils.isBlank(user.getPhone())){
                if(StringUtils.isBlank(user.getEmail())){
                    return RUtils.Err(400,"未输入邮箱或手机");
                }
                else {
                    return RUtils.Err(400,"未输入邮箱或手机");
                }
            }
            else{
                QueryWrapper queryWrapper=new QueryWrapper();
                if (StringUtils.isBlank(user.getEmail())){
                    queryWrapper.eq("phone",user.getPhone());
                    if(userService.getOne(queryWrapper)!=null){
                        return RUtils.Err(400,"重复的手机号");
                    };
                }
                else{
                    queryWrapper.eq("email",user.getEmail());
                    if(userService.getOne(queryWrapper)!=null){
                        return RUtils.Err(400,"重复的邮箱");
                    };
                }
            }
            return RUtils.success();
        }


    @PostMapping("/regist/valid-email")
    @ResponseBody
    public R validateEmail(@Pattern(regexp = EMAIL_REGEX) String email,HttpSession session){
        System.out.println(email);
        if(authorizeService.sendValidateEmail(email,session.getId())){

            return RUtils.success();
        }
        else{
            return RUtils.Err(500,"验证码错误");
        }

    }
    }

