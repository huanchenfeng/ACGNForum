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
    public R selectMyInfo(String userId){
        int id =Integer.parseInt(userId);
        User user=userService.getById(id);
        UserInfoDto userInfo=new UserInfoDto();
        userInfo.setCreateTime(user.getCreateTime());
        userInfo.setUsername(user.getUsername());
        userInfo.setPhone(user.getPhone());
        userInfo.setEmail(user.getEmail());
        userInfo.setActivationCode(user.getActivationCode());
        userInfo.setHeaderUrl(user.getHeaderUrl());
        userInfo.setSex(user.getSex());
        userInfo.setDress(user.getDress());
        userInfo.setSignature(user.getSignature());
        userInfo.setAuthor(user.getAuthor());
        return RUtils.success(userInfo);
    }
}
