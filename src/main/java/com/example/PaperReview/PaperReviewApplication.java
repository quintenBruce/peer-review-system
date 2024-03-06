package com.example.PaperReview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class PaperReviewApplication {

	public static void main(String[] args) {

		SpringApplication.run(PaperReviewApplication.class, args);
		System.out.println("HIIIIIIIIIIIIIIIIIIIIIIi");
	}

}
