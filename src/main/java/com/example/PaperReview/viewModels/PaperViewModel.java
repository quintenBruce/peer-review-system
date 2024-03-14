package com.example.PaperReview.viewModels;


import com.example.PaperReview.models.Category;
import com.example.PaperReview.models.Review;
import com.example.PaperReview.models.ReviewType;
import com.example.PaperReview.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperViewModel {
    private int id;
    private byte[] content;
    private String title;
    private String description;
    private Date creationDate;
    private String sentimentLabel;
    private float sentimentScore;
    private ReviewType reviewType;
    private Set<Review> reviews = new HashSet<>();
    private Set<User> Authors = new HashSet<>();
    private Set<Category> categories = new HashSet<>();
    private boolean isReviewer;
    private boolean isPublished;

    public PaperViewModel(int id, byte[] content, Set<User> Authors, Set<Review> reviews, Set<Category> categories) {
        this.id = id;
        this.content = content;
        this.Authors = Authors;
        this.reviews = reviews;
        this.categories = categories;
        // Initialize other fields as needed...
    }

}
