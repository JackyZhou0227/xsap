package com.kclm.xsap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@MapperScan("com.kclm.xsap.dao")
public class XsapApplication {

    public static void main(String[] args) {
        SpringApplication.run(XsapApplication.class, args);
    }

}
