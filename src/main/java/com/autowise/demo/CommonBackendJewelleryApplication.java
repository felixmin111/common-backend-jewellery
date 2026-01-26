package com.autowise.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.autowise.demo")
public class CommonBackendJewelleryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonBackendJewelleryApplication.class, args);
	}

}
