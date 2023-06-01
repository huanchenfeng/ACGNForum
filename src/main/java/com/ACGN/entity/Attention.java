package com.ACGN.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Attention {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private int userId;
    private int focusUserId;
    private Date createTime;

}
