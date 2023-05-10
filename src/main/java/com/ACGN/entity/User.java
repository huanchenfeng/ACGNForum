package com.ACGN.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
@Data
public class User implements UserDetails {

    @TableId(value = "user_id", type = IdType.AUTO)
    private int id;

    private String username;

    private String password;

    private String email;

    private int type;

    private int status;   //是否激活

    private String activationCode;  //激活码

    private String headerUrl;

    private Date createTime;

    private String phone;

    private String sex;

    private String dress;

    private String signature;

    private int author;

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime(){
        String time = null;
        Date date=this.createTime;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        time = dateformat.format(date);

        return time;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    /**
     * 是否账号过期
     * */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    /**
     * 是否账号被锁定
     * */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    /**
     * 是否凭证（密码）过期
     * */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    /**
     * 是否可用
     * */
    @Override
    public boolean isEnabled() {
        return false;
    }
}
