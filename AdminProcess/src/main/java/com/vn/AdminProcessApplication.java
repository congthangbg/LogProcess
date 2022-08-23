package com.vn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@SpringBootApplication(scanBasePackages = {"com.vn.repository", "com.vn.controller"})
@SpringBootApplication
public class AdminProcessApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AdminProcessApplication.class, args);
	}

}
