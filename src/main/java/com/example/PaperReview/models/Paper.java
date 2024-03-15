package com.example.PaperReview.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paper {
    private int id;
    private byte[] content;
    private String title;
    private String description;
    private Date creationDate;
    private String sentimentLabel;
    private float sentimentScore;
    private float sentimentMagnitude;
    private ReviewType reviewType;
    private Set<Review> reviews = new HashSet<>();
    private Set<User> Authors = new HashSet<>();
    private Set<Category> categories = new HashSet<>();
    private Set<User> authorizedReviewers = new HashSet<>();
    private boolean isPublished;

    // Constructors, getters, setters, and other methods
    public Paper(int id, byte[] content, Set<User> Authors, Set<Review> reviews, Set<Category> categories) {
        this.id = id;
        this.content = content;
        this.Authors = Authors;
        this.reviews = reviews;
        this.categories = categories;
        // Initialize other fields as needed...
    }

    public Paper(byte[] content, Set<User> authors, String title, Date date, String description) {
        this.content = content;
        this.Authors = authors;
        this.title = title;
        this.creationDate = date;
        this.description = description;
    }

    public Paper(int id, byte[] content) {
        this.id = id;
        this.content = content;
        // Initialize other fields as needed...
    }
    public boolean isReviewer(User user) {
        for (User reviewer : authorizedReviewers) {
            if (reviewer.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public String getReviewsText() {
        String reviewText = "";
        for (Review review: this.getReviews()) {
            reviewText = reviewText.concat(review.getContent() + "\n");
        }

        return reviewText;
    }

    public void setSentiments(double score, double magnitude, String label) {
        this.sentimentScore = (float) score;
        this.sentimentMagnitude = (float) magnitude;
        this.sentimentLabel = label;
    }
}
