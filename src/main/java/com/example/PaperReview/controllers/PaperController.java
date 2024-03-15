package com.example.PaperReview.controllers;


import com.example.PaperReview.models.Paper;
import com.example.PaperReview.models.Review;
import com.example.PaperReview.models.SentimentAnalysis;
import com.example.PaperReview.models.User;
import com.example.PaperReview.repositories.PaperRepository;
import com.example.PaperReview.repositories.UserRepository;
import com.example.PaperReview.services.GoogleNLPService;
import com.example.PaperReview.services.PDFService;
import com.example.PaperReview.services.PaperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.*;

@Controller
public class PaperController {
    @GetMapping("/papers/{id}")
    public ModelAndView paperById(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        //byte[] pdfBytes = PDFService.toByteArray("src/main/java/com/example/PaperReview/Projects - Tagged.pdf");
        Paper paper = PaperRepository.getPaperById(id);
        modelAndView.addObject("paper", paper);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        modelAndView.addObject("username", auth.getName());

        User user = UserRepository.getUser(auth.getName());



        boolean isReviewer = paper.isReviewer(user);
        modelAndView.addObject("isReviewer", isReviewer);
        modelAndView.setViewName("paper"); // This corresponds to the file name without the extension
        return modelAndView;
    }

    @PostMapping("/papers/{id}/reviews")
    public ResponseEntity<String> paperById(@PathVariable int id, @RequestParam String content) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = UserRepository.getUser(auth.getName());
            Paper paper = PaperRepository.getPaperById(id);

            if (user == null || paper == null) {
                // User or paper not found, return failure status
                return new ResponseEntity<>("User or Paper not found", HttpStatus.NOT_FOUND);
            }

            Random random = new Random();
            Review review = new Review(random.nextInt(Integer.MAX_VALUE), id, user, content, new Date(), null, 0);
            PaperRepository.addReviewToPaper(paper.getId(), review);

            SentimentAnalysis sentimentAnalysis = GoogleNLPService.sentimentAnalysis(paper.getReviewsText());
            sentimentAnalysis.generateSentimentLabel();

            paper.setSentiments(sentimentAnalysis.getScore(), sentimentAnalysis.getMagnitude(), sentimentAnalysis.getLabel());

            PaperRepository.removeAuthorizedReviewer(paper.getId(), user);


            // Review insertion successful, return success status
            return new ResponseEntity<>("Review inserted successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            // An error occurred, return failure status
            return new ResponseEntity<>("Error inserting review", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/submit-paper")
    public ModelAndView submitPaper() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        modelAndView.addObject("username", auth.getName());

        modelAndView.setViewName("submit"); // This corresponds to the file name without the extension
        return modelAndView;
    }

    @PostMapping("/submit-paper")
    public ResponseEntity<String> postPaper(
            @RequestParam("title") String title,
            @RequestParam("authors") List<String> authors,
            @RequestParam("pdfFile") MultipartFile pdfFile,
            @RequestParam(value = "abstract", required = false) String paperAbstract
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        byte[] content = new byte[0];
        try {
            content = pdfFile.getBytes();
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to read file content", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<User> _authors = UserRepository.getUsers(authors);
        Paper paper = new Paper(content, new HashSet<>(_authors), title, new Date(), paperAbstract);
        boolean status = PaperService.PutPaper(paper);

        if (status) {
            return new ResponseEntity<>("Paper submitted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to submit paper", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
