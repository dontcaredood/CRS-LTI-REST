package com.lt.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.lt.controller.*;

@ComponentScan({"com.lt.*"})
@EnableAutoConfiguration
@EnableWebMvc
@SpringBootApplication
public class CrsRestProjectPracticeApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(CrsRestProjectPracticeApplication.class, args);
	}
	
}
