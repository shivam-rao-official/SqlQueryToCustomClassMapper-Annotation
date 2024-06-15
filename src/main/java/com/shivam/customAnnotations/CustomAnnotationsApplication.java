package com.shivam.customAnnotations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CustomAnnotationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomAnnotationsApplication.class, args);
	}

}
