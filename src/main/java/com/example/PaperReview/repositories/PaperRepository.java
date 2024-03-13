package com.example.PaperReview.repositories;

import com.example.PaperReview.models.Paper;
import com.example.PaperReview.models.Review;
import com.example.PaperReview.models.User;

import java.util.*;

public class PaperRepository {
    private static final Map<Integer, Paper> paperMap = new HashMap<>();
    private static int nextId = 1;

    private PaperRepository() {
        // private constructor to prevent instantiation
    }

    public static byte[] getContentById(int paperId) {
        Paper paper = paperMap.get(paperId);
        return paper != null ? paper.getContent() : null;
    }

    public static Paper putPaper(Paper paper) {
        if (paper.getId() == 0) {
            // If the paper doesn't have an ID, assign the next available ID
            paper.setId(nextId++);
        }
        paperMap.put(paper.getId(), paper);
        return paper;
    }

    public static Paper getPaper(int id) {
        return paperMap.get(id);
    }

    public static Collection<Paper> getAllPapers() {
        return paperMap.values();
    }

    // Other methods as needed...

    public static void deletePaper(int id) {
        paperMap.remove(id);
    }

    public static void addReviewToPaper(int paperId, Review review) {
        Paper paper = paperMap.get(paperId);
        if (paper != null) {
            paper.getReviews().add(review);
            // Update the paper in the repository
            paperMap.put(paperId, paper);
        }
    }

    // Method to add an author to a paper
    public static void addAuthorToPaper(int paperId, User author) {
        Paper paper = paperMap.get(paperId);
        if (paper != null) {
            paper.getAuthors().add(author);
            // Update the paper in the repository
            paperMap.put(paperId, paper);
        }
    }

    // Method to add sentiment details to a paper
    public static void addSentimentDetailsToPaper(int paperId, String sentimentLabel, float sentimentScore) {
        Paper paper = paperMap.get(paperId);
        if (paper != null) {
            paper.setSentimentLabel(sentimentLabel);
            paper.setSentimentScore(sentimentScore);
            // Update the paper in the repository
            paperMap.put(paperId, paper);
        }
    }

    public static List<Integer> getPaperIdsByAuthor(User author) {
        List<Integer> paperIds = new ArrayList<>();

        for (Map.Entry<Integer, Paper> entry : paperMap.entrySet()) {
            Paper paper = entry.getValue();
            if (paper.getAuthors().contains(author)) {
                paperIds.add(entry.getKey());
            }
        }

        return paperIds;
    }
    public static void addSentimentDetailsToPaper(int paperId, String sentimentLabel, float sentimentScore, float sentimentMagnitude) {
        Paper paper = paperMap.get(paperId);
        if (paper != null) {
            paper.setSentimentLabel(sentimentLabel);
            paper.setSentimentScore(sentimentScore);
            paper.setSentimentMagnitude(sentimentMagnitude);
            // Update the paper in the repository
            paperMap.put(paperId, paper);
        }
    }

    // Additional method to update sentiment details without modifying existing ones
    public static void updateSentimentDetails(int paperId, float sentimentScore, float sentimentMagnitude) {
        Paper paper = paperMap.get(paperId);
        if (paper != null) {
            paper.setSentimentScore(sentimentScore);
            paper.setSentimentMagnitude(sentimentMagnitude);
            // Update the paper in the repository
            paperMap.put(paperId, paper);
        }
    }

    public static Paper updatePaper(Paper updatedPaper) {
        int paperId = updatedPaper.getId();

        if (paperMap.containsKey(paperId)) {
            // Replace the existing paper with the updated one
            paperMap.put(paperId, updatedPaper);
            return updatedPaper;
        } else {
            // Paper with the given ID not found
            return null;
        }
    }
}
