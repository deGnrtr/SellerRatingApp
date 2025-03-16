package com.leverx.javacourse.seller.rating.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
public class SellerRatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SellerRatingApplication.class, args);
	}

}
