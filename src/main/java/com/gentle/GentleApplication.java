package com.gentle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(value = "com.gentle.mapper")
public class GentleApplication {

    public static void main(String[] args) {
        SpringApplication.run(GentleApplication.class, args);
    }

}
