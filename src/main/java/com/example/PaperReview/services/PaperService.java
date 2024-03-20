package com.example.PaperReview.services;

import com.example.PaperReview.models.Organization;
import com.example.PaperReview.models.Paper;
import com.example.PaperReview.models.Review;
import com.example.PaperReview.models.User;
import com.example.PaperReview.repositories.PaperRepository;
import com.example.PaperReview.repositories.UserRepository;
import com.example.PaperReview.viewModels.HomeViewModel;
import com.example.PaperReview.viewModels.PaperCard;
import com.example.PaperReview.viewModels.ReviewCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaperService {
    public static HomeViewModel generateHomeViewModel(User user) {
        //papers the user authored
        List<Paper> userPapers = PaperRepository.getPapersByUser(user);
        List<PaperCard> userPaperCards = new ArrayList<>();
        for (Paper paper: userPapers) {
            String imageData = PDFService.firstPageToImage(paper.getContent());
            userPaperCards.add(new PaperCard(imageData, paper.getTitle(), paper.getId()));
        }
        //papers the author is assigned to review
        List<Paper> userAssignedReviews = PaperRepository.getPapersToReview(user);
        List<PaperCard> userReviewPaperCards = new ArrayList<>();
        for (Paper paper: userAssignedReviews) {
            String imageData = PDFService.firstPageToImage(paper.getContent());
            userReviewPaperCards.add(new PaperCard(imageData, paper.getTitle(), paper.getId()));
        }
        //reviews the user has posted
        List<Review> userReviews = PaperRepository.getReviewsByAuthor(user);
        List<ReviewCard> userReviewCards = new ArrayList<>();
        for (Review review: userReviews) {
            Paper paper = PaperRepository.getPaperById(review.getPaperId());
            userReviewCards.add(new ReviewCard(review.getContent(), paper.getTitle(), paper.getId()));
        }
        HomeViewModel viewModel = new HomeViewModel(userPaperCards, userReviewCards, userReviewPaperCards);
        return viewModel;


    }

    public static boolean PutPaper(Paper paper) {
        //run categorization service
        List<String> categories = GoogleNLPService.classifyText(PDFService.extractText(paper.getContent()));
        paper.setCategories(categories);
        PaperRepository.putPaper(paper);
        return true;
    }

    public static List<String> AssignReviewers(User author, Paper paper) {
        List<User> usersInOrg = UserRepository.getUsersByOrganization(author.getOrganization());
        usersInOrg.removeIf(user -> user.equals(author));

        Collections.shuffle(usersInOrg);

        int numReviewers = Math.min(2, usersInOrg.size());

        List<User> selectedUsers = usersInOrg.subList(0, numReviewers);
        List<String> res = new ArrayList<>();

        for (User user : selectedUsers) {
            PaperRepository.addAuthorizedReviewer(paper.getId(), user);
            res.add(user.getUsername());
        }
        return res;
    }

}
