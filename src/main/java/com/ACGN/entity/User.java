package com.ACGN.entity;

import lombok.Data;

import java.util.Date;
@Data
public class User {
<<<<<<< HEAD
    private int id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private int type;
    private int status;
    private String activationCode;
=======

    private int id;
    private String username;
    private String password;
    private String salt;   //密码盐值
    private String email;
    private int type;
    private int status;   //是否激活
    private String activationCode;  //激活码
>>>>>>> origin/master
    private String headerUrl;
    private Date createTime;
}
