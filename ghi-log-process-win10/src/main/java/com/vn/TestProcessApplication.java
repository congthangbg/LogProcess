package com.vn;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestProcessApplication.class, args);
		System.setProperty("java.awt.headless", "false");
	}

}
