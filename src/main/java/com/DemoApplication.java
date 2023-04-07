package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//标识为springboot启动类,必须是父路径，其他包路径必须是其子路径
//@ComponentScan(basePackages = "com")
@SpringBootApplication(scanBasePackages="com")
@MapperScan("com.ACGN.dao")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

