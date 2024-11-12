package com.linqiumeng.mediavault;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.linqiumeng.mediavault.mapper")
public class MediavaultApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediavaultApplication.class, args);
	}

}
