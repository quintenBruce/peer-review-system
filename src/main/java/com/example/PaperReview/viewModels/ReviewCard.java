package com.example.PaperReview.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCard {
    private String reviewText;
    private String label;
    private int paperId;
}
