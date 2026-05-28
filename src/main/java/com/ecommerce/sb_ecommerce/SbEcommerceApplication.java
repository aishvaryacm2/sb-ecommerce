package com.ecommerce.sb_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.ecommerce")
@EnableJpaRepositories(basePackages = "com.ecommerce.repository")
@EntityScan(basePackages = "com.ecommerce.model")
public class SbEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbEcommerceApplication.class, args);
	}

}
