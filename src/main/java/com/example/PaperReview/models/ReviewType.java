package com.example.PaperReview.models;

public enum ReviewType {
    ORGANIZATION("Waiting to be processed"),
    ADMIN_ASSIGNED,
    OPEN,
    FAILURE;

    private final String note;

    ReviewType() {
        this.note = null;
    }

    ReviewType(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
