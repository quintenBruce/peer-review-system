package com.example.PaperReview.services;

import com.example.PaperReview.models.Category;
import com.example.PaperReview.models.SentimentAnalysis;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<String> classifyText(String text) {
        OkHttpClient client = new OkHttpClient();

        // Specify the URL you want to send the request to
        String url = "";

        if (url.isEmpty() || url.isBlank() || url.equals("")) {
            List<String> defaultList = new ArrayList<>();
            defaultList.add("Test Category 1");
            defaultList.add("Test Category 2");
            return defaultList;
        }

//        String requestBody = "{" +
//                "\"document\": {" +
//                "\"type\": \"PLAIN_TEXT\"," +
//                "\"content\": \"" + text + "\"" +
//                "}" +
//                "}";

        org.json.JSONObject document = new org.json.JSONObject();
        document.put("type", "PLAIN_TEXT");
        document.put("content", text);

        org.json.JSONObject requestBody = new org.json.JSONObject();
        requestBody.put("document", document);

//        String requestBody = constructRequestBody(text);

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), String.valueOf(requestBody)))
                .build();


        try {
            // Execute the request and get the response
            Response response = client.newCall(request).execute();

            // Read the response body
            String responseBody = response.body().string();


            ObjectMapper objectMapper = new ObjectMapper();
            List<String> categoriesWithConfidenceOverPointTwo = new ArrayList<>();


            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode categoriesNode = rootNode.get("categories");

            for (JsonNode categoryNode : categoriesNode) {
                double confidence = categoryNode.get("confidence").asDouble();
                if (confidence > 0.15) {
                    String categoryName = categoryNode.get("name").asText();
                    categoriesWithConfidenceOverPointTwo.add(categoryName.substring(1));
                }
            }
            response.close();
            return categoriesWithConfidenceOverPointTwo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
