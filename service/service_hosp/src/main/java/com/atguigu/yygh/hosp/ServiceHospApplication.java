package com.atguigu.yygh.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhusg02
 * @date 2021/5/21 17:03
 */
@ComponentScan(basePackages = "com.atguigu")
@SpringBootApplication
public class ServiceHospApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApplication.class, args);
    }
}
