package com.example.PaperReview.repositories;

import com.example.PaperReview.models.Paper;
import com.example.PaperReview.models.Review;
import com.example.PaperReview.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaperRepository {
    private static final List<Paper> papers = new ArrayList<>();
    private static int nextId = 1;

    public static byte[] getContentById(int paperId) {
        Paper paper = getPaperById(paperId);
        return paper != null ? paper.getContent() : null;
    }
    public static void removeAuthorizedReviewer(int paperId, User reviewer) {
        Paper paper = getPaperById(paperId);
        if (paper != null) {
            paper.getAuthorizedReviewers().remove(reviewer);
            papers.set(papers.indexOf(paper), paper);
        }
    }

    public static Paper putPaper(Paper paper) {
        if (paper.getId() == 0) {
            // If the paper doesn't have an ID, assign the next available ID
            paper.setId(nextId++);
        }
        papers.add(paper);
        return paper;
    }

    public static Paper getPaperById(int id) {
        for (Paper paper : papers) {
            if (paper.getId() == id) {
                return paper;
            }
        }
        return null;
    }

    public static void deletePaper(int id) {
        papers.removeIf(paper -> paper.getId() == id);
    }

    public static void addReviewToPaper(int paperId, Review review) {
        Paper paper = getPaperById(paperId);
        if (paper != null) {
            paper.getReviews().add(review);
        }
    }

    public static List<Paper> getPapersByUser(User user) {
        List<Paper> userPapers = new ArrayList<>();
        for (Paper paper : papers) {
            if (paper.getAuthors().contains(user)) {
                userPapers.add(paper);
            }
        }
        return userPapers;
    }
    public static List<Paper> getPapersToReview(User reviewer) {
        List<Paper> papersToReview = new ArrayList<>();

        for (Paper paper : papers) {
            if (paper.getAuthorizedReviewers().contains(reviewer)) {
                papersToReview.add(paper);
            }
        }

        return papersToReview;
    }
    public static List<Review> getReviewsByAuthor(User author) {
        List<Review> reviewsContent = new ArrayList<>();

        for (Paper paper : papers) {
            for (Review review : paper.getReviews()) {
                if (review.getAuthor().equals(author)) {
                    reviewsContent.add(review);
                }
            }
        }

        return reviewsContent;
    }

}
