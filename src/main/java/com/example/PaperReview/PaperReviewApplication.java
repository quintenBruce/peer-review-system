package com.example.PaperReview;

import com.example.PaperReview.models.*;
import com.example.PaperReview.repositories.OrganizationRepository;
import com.example.PaperReview.repositories.PaperRepository;
import com.example.PaperReview.repositories.UserRepository;
import com.example.PaperReview.services.GoogleNLPService;
import com.example.PaperReview.services.PDFService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.*;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan("com.example.PaperReview")

public class PaperReviewApplication {
	public static User userReviewer = new User("user1");
	public static User userAuthor = new User("user2");

	public static void main(String[] args) {
		Organization organization = new Organization("Texas Tech");
		OrganizationRepository.save(organization);
		userAuthor.setOrganization(organization);
		userReviewer.setOrganization(organization);
		UserRepository.putUser(userReviewer);
		UserRepository.putUser(userAuthor);

		//create 2 papers with the same aurthor and same authorized reviewer

		PaperRepository.putPaper(createTestPaper(userAuthor, userReviewer));
		PaperRepository.putPaper(createTestPaper2(userAuthor, userReviewer));
		//user2 will have 2 papers. user1 will have 2 papers to review



		SpringApplication.run(PaperReviewApplication.class, args);
	}

	private static Paper createTestPaper(User author, User authorizedReviewer) {
		Paper paper = new Paper();
		//pdf content
		paper.setContent(PDFService.toByteArray("src/main/java/com/example/PaperReview/Projects - Tagged.pdf")); // Replace with actual content

		//basic information
		paper.setTitle("Test Paper");
		paper.setDescription("This is a test paper");
		paper.setCreationDate(new Date());

		//sentiment
		paper.setSentimentLabel("Default Label");
		paper.setSentimentScore(0f);

		//idfk
		paper.setPublished(true);

		// test reviews
		Review review1 = new Review();
		review1.setAuthor(new User("Becky Smith (test)"));
		review1.setContent("Great paper!");

		Review review2 = new Review();
		review2.setAuthor(new User("Michael Jacksun (test)"));
		review2.setContent("""
    The submitted content admirably explores the intricate realms of parallelism and concurrency, showcasing a thoughtful amalgamation of insights. The author skillfully navigates the landscape, emphasizing the pivotal role of concurrency in optimizing computational processes. The adept use of technical language and precise articulation underscores the author's command over the subject matter.

				The manuscript delves into the nuanced intricacies of parallel execution, shedding light on its significance in enhancing overall system performance. The seamless integration of theoretical frameworks with practical applications serves as a commendable aspect, contributing to the scholarly merit of the work.

						Furthermore, the author's adeptness in elucidating complex concepts is evident throughout the text. The discussion on parallel algorithms and their efficacy in real-world scenarios is particularly enlightening. The meticulous exploration of concurrency models and their impact on system efficiency adds depth to the narrative.

		In conclusion, the submitted manuscript presents a well-crafted examination of parallelism and concurrency, showcasing the author's expertise in the field. The positive tone, coupled with the comprehensive coverage of the subject matter, makes this contribution a valuable addition to the scientific discourse on the topic.""");

		Set<Review> reviews = new HashSet<>();
		reviews.add(review2);
		reviews.add(review1);
		paper.setReviews(reviews);

		//authors
		Set<User> authors = new HashSet<>();
		authors.add(author);
		paper.setAuthors(authors);

		//authorized reviewers
		paper.getAuthorizedReviewers().add(authorizedReviewer);
		//categories
		List<String> categories = GoogleNLPService.classifyText(PDFService.extractText(paper.getContent()));
		paper.setCategories(categories);

		return paper;
	}

	private static Paper createTestPaper2(User author, User authorizedReviewer) {
		Paper paper = new Paper();

		//pdf content
		paper.setContent(PDFService.toByteArray("src/main/java/com/example/PaperReview/haskellPaper.pdf"));

		//basic information
		paper.setTitle("The Second Test Paper");
		paper.setDescription("This is a test paper. The second one, to be exact. ");
		paper.setCreationDate(new Date());

		//sentiment
		paper.setSentimentLabel("Default Label");
		paper.setSentimentScore(0f);
		paper.setSentimentMagnitude(0f);

		//idfk
		paper.setPublished(true);

		// test reviews
		Review review1 = new Review();
		review1.setAuthor(new User("Teddy Smith (test)"));
		review1.setContent("Mediocre! I am enthusiastically reviewing this paper.");
		review1.setPaperId(2);

		Review review2 = new Review();
		review2.setPaperId(2);
		review2.setAuthor(new User("The Prime Time (test)"));
		review2.setContent("""
    The submitted content admirably explores the intricate realms of parallelism and concurrency, showcasing a thoughtful amalgamation of insights. The author skillfully navigates the landscape, emphasizing the pivotal role of concurrency in optimizing computational processes. The adept use of technical language and precise articulation underscores the author's command over the subject matter.

				The manuscript delves into the nuanced intricacies of parallel execution, shedding light on its significance in enhancing overall system performance. The seamless integration of theoretical frameworks with practical applications serves as a commendable aspect, contributing to the scholarly merit of the work.

						Furthermore, the author's adeptness in elucidating complex concepts is evident throughout the text. The discussion on parallel algorithms and their efficacy in real-world scenarios is particularly enlightening. The meticulous exploration of concurrency models and their impact on system efficiency adds depth to the narrative.

		In conclusion, the submitted manuscript presents a well-crafted examination of parallelism and concurrency, showcasing the author's expertise in the field. The positive tone, coupled with the comprehensive coverage of the subject matter, makes this contribution a valuable addition to the scientific discourse on the topic.""");

		Set<Review> reviews = new HashSet<>();
		reviews.add(review2);
		reviews.add(review1);
		paper.setReviews(reviews);

		//authors
		Set<User> authors = new HashSet<>();
		authors.add(author);
		paper.setAuthors(authors);

		//authorized reviewers
		paper.getAuthorizedReviewers().add(authorizedReviewer);
		List<String> categories = GoogleNLPService.classifyText(PDFService.extractText(paper.getContent()));
		paper.setCategories(categories);
		return paper;
	}
}
