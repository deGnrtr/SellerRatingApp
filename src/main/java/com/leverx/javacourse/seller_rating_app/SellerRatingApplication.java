package com.leverx.javacourse.seller_rating_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("src/main/java/repository")
public class SellerRatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SellerRatingApplication.class, args);
	}

}
