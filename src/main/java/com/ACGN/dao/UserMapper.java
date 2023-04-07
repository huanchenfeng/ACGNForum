package com.ACGN.dao;

<<<<<<< HEAD
import com.ACGN.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
=======

import com.ACGN.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

>>>>>>> origin/master
    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

<<<<<<< HEAD
    int updateStatus(int id,int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id,String password);
=======
    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);

>>>>>>> origin/master
}
