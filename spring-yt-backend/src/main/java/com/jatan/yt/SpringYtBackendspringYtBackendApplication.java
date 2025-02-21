package com.jatan.yt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.jatan.yt")
@EntityScan("com.jatan.yt.entities")
public class SpringYtBackendspringYtBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringYtBackendspringYtBackendApplication.class, args);
		System.out.println("heloooo bhaiya");

	}

} 
