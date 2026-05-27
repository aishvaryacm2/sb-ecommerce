package com.ecommerce.sb_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ecommerce")
public class SbEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbEcommerceApplication.class, args);
	}

}
