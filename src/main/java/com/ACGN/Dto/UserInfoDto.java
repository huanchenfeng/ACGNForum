package com.ACGN.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoDto {
    private String username;

    private String email;

    private String activationCode;  //激活码

    private String headerUrl;

    private String phone;
}
