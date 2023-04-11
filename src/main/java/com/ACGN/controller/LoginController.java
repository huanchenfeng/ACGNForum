package com.ACGN.controller;

import com.ACGN.Service.UserService;
import com.ACGN.entity.User;
import com.ACGN.util.R;
import com.ACGN.util.RUtils;
import com.ACGN.util.Renum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/regist")
    public R regist(User user){

        if (userService.save(user)){
            return RUtils.success();}
        return RUtils.Err(Renum.USER_IS_EXISTS.getCode(),Renum.USER_IS_EXISTS.getMsg());

    }
}
