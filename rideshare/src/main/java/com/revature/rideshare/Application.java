package com.revature.rideshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class Application {
	
	@Autowired
	DispatcherServlet dispatcherServlet;

	public static void main(String[] args) throws Exception {
		@SuppressWarnings("unused")
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
	}

}
