package com.inke.myidea;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan(value = "com.inke.myidea.dao")
public class MyideaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyideaApplication.class, args);
    }

}
