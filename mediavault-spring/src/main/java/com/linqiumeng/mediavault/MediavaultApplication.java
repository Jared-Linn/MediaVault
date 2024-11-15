package com.linqiumeng.mediavault;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@MapperScan("com.linqiumeng.mediavault.mapper")
//@EntityScan("com.linqiumeng.mediavault.entity")
public class MediavaultApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediavaultApplication.class, args);
    }


}
