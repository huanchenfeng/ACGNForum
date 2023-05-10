package com.ACGN.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Attention {
    private int id;
    private int userId;
    private int focusUserId;
    private Date createTime;

}
