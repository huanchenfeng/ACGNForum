package com.ACGN.controller;

import com.ACGN.Dto.UserInfoDto;
import com.ACGN.Service.UserService;
import com.ACGN.entity.User;
import com.ACGN.util.R;
import com.ACGN.util.RUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class MyInfoController {
    @Autowired
    private UserService userService;
    @PostMapping("/myInfo")
    @ResponseBody
    public R selectMyInfo(String username){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",username);
        User user=userService.getOne(queryWrapper);
        UserInfoDto userInfo=new UserInfoDto();
        userInfo.setUsername(user.getUsername());
        userInfo.setPhone(user.getPhone());
        userInfo.setEmail(user.getEmail());
        userInfo.setActivationCode(user.getActivationCode());
        userInfo.setHeaderUrl(user.getHeaderUrl());
        return RUtils.success(userInfo);
    }
}
