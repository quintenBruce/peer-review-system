package com.example.PaperReview;

import com.example.PaperReview.Services.PDFService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class PaperReviewApplication {

	public static void main(String[] args) {

		SpringApplication.run(PaperReviewApplication.class, args);
		String t = PDFService.extractText("src/main/java/com/example/PaperReview/Projects - Tagged.pdf");
		System.out.println(t);
	}

}
