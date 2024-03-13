package com.example.PaperReview;

import com.example.PaperReview.models.Category;
import com.example.PaperReview.models.Paper;
import com.example.PaperReview.models.Review;
import com.example.PaperReview.models.User;
import com.example.PaperReview.repositories.PaperRepository;
import com.example.PaperReview.repositories.UserRepository;
import com.example.PaperReview.services.PDFService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan("com.example.PaperReview")

public class PaperReviewApplication {
	public static User user = new User();

	public static void main(String[] args) {
		PaperRepository.putPaper(createTestPaper());



		user.setUsername("admin");
		UserRepository.putUser(user);

		SpringApplication.run(PaperReviewApplication.class, args);
//		String t = PDFService.extractText("src/main/java/com/example/PaperReview/Projects - Tagged.pdf");
//		System.out.println(t);
	}

	// Method to create a test paper object
	private static Paper createTestPaper() {
		Paper paper = new Paper();
		paper.setId(1);
		paper.setContent(PDFService.toByteArray("src/main/java/com/example/PaperReview/Projects - Tagged.pdf")); // Replace with actual content
		paper.setTitle("Test Paper");
		paper.setDescription("This is a test paper");
		paper.setCreationDate(new Date());
		paper.setSentimentLabel("Positive");
		paper.setSentimentScore(0f);
		paper.setPublished(true);

		// Add test reviews
		Review review1 = new Review();
		review1.setAuthor(new User("Becky Smith"));
		review1.setContent("Great paper!");

		Review review2 = new Review();
		review2.setAuthor(new User("Teddy Wilson"));
		review2.setContent("""
    The submitted content admirably explores the intricate realms of parallelism and concurrency, showcasing a thoughtful amalgamation of insights. The author skillfully navigates the landscape, emphasizing the pivotal role of concurrency in optimizing computational processes. The adept use of technical language and precise articulation underscores the author's command over the subject matter.

				The manuscript delves into the nuanced intricacies of parallel execution, shedding light on its significance in enhancing overall system performance. The seamless integration of theoretical frameworks with practical applications serves as a commendable aspect, contributing to the scholarly merit of the work.

						Furthermore, the author's adeptness in elucidating complex concepts is evident throughout the text. The discussion on parallel algorithms and their efficacy in real-world scenarios is particularly enlightening. The meticulous exploration of concurrency models and their impact on system efficiency adds depth to the narrative.

		In conclusion, the submitted manuscript presents a well-crafted examination of parallelism and concurrency, showcasing the author's expertise in the field. The positive tone, coupled with the comprehensive coverage of the subject matter, makes this contribution a valuable addition to the scientific discourse on the topic.""");

		Set<Review> reviews = new HashSet<>();
		reviews.add(review2);
		reviews.add(review1);

		paper.setReviews(reviews);

		Set<User> authors = new HashSet<>();


		paper.setAuthors(authors);

		paper.getAuthorizedReviewers().add(user);




		Set<Category> categories = new HashSet<>();

		paper.setCategories(categories);

		return paper;
	}
}
