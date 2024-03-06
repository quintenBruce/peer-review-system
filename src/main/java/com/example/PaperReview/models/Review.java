package com.example.PaperReview.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private int id;
    private int paperId;
    private int authorId;
    private String content;
    private Date creationDate;
    private String sentimentLabel;
    private float sentimentScore;
}

