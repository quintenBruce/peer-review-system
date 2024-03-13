package com.example.PaperReview.services;

import com.example.PaperReview.models.SentimentAnalysis;
import okhttp3.*;
import org.json.JSONObject;

public class GoogleNLPService {
    public static SentimentAnalysis sentimentAnalysis(String text) {
        OkHttpClient client = new OkHttpClient();

        // Specify the URL you want to send the request to
        String url = "";

        if (url.isEmpty() || url.isBlank() || url.equals("")) {
            return new SentimentAnalysis(0d, 0d, "test label");
        }

        String requestBody = "{" +
                "\"document\": {" +
                "\"type\": \"PLAIN_TEXT\"," +
                "\"content\": \"" + text + "\"" +
                "}," +
                "\"encodingType\": \"UTF16\"" +
                "}";

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), requestBody))
                .build();


        try {
            // Execute the request and get the response
            Response response = client.newCall(request).execute();

            // Read the response body
            String responseBody = response.body().string();


            JSONObject jsonObject = new JSONObject(responseBody);

            // Extract documentSentiment information
            JSONObject documentSentiment = jsonObject.getJSONObject("documentSentiment");
            double magnitude = documentSentiment.getDouble("magnitude");
            double score = documentSentiment.getDouble("score");

            SentimentAnalysis sentimentAnalysis = new SentimentAnalysis(magnitude, score, null);
            sentimentAnalysis.generateSentimentLabel();

            // Close the response body
            response.close();
            return sentimentAnalysis;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
