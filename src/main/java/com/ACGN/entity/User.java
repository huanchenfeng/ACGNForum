package com.ACGN.entity;

import lombok.Data;

import java.util.Date;
@Data
public class User {


    private int id;
    private String username;
    private String password;
    private String salt;   //密码盐值
    private String email;
    private int type;
    private int status;   //是否激活
    private String activationCode;  //激活码
    private String headerUrl;
    private Date createTime;
}
