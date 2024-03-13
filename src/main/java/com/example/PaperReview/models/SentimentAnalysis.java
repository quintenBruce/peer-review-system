package com.example.PaperReview.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentimentAnalysis {
    private Double magnitude;
    private Double score;
    private String label;

    public void generateSentimentLabel() {
        Double sentimentScore = this.score;
        Double sentimentMagnitude = this.magnitude;
        if (sentimentMagnitude < 0.5) {
            this.label = "Neutral";
        }

        if (sentimentScore >= 0.7 && sentimentMagnitude >= 0.5) {
            this.label =  "Strongly Positive";
        } else if (sentimentScore >= 0.4 && sentimentScore < 0.7 && sentimentMagnitude >= 0.5) {
            this.label = "Positive";
        } else if (sentimentScore >= 0.1 && sentimentScore < 0.4 && sentimentMagnitude >= 0.5) {
            this.label = "Slightly Positive";
        } else if (sentimentScore >= -0.1 && sentimentScore < 0.1 && sentimentMagnitude >= 0.5) {
            this.label = "Neutral";
        } else if (sentimentScore >= -0.4 && sentimentScore < -0.1 && sentimentMagnitude >= 0.5) {
            this.label = "Slightly Negative";
        } else if (sentimentScore >= -0.7 && sentimentScore < -0.4 && sentimentMagnitude >= 0.5) {
            this.label = "Negative";
        } else if (sentimentScore < -0.7 && sentimentMagnitude >= 0.5) {
            this.label = "Strongly Negative";
        } else {
            this.label = "Undetermined";
        }
    }
}
