package com.example.PaperReview.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Paper {
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
    private boolean isPublished;

    // Constructors, getters, setters, and other methods
}
