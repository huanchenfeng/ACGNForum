package com.ACGN.Service;

import com.ACGN.dao.UserMapper;
import com.ACGN.entity.User;
import com.ACGN.util.CommunityUtil;
import com.ACGN.util.MailClient;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public interface UserService extends IService<User> {


}
