package com.example.PaperReview.services;

import com.example.PaperReview.models.Category;
import com.example.PaperReview.models.SentimentAnalysis;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleNLPService {
    public static SentimentAnalysis sentimentAnalysis(String text) {
        OkHttpClient client = new OkHttpClient();

        // Specify the URL you want to send the request to
        String url = "https://language.googleapis.com/v2/documents:analyzeSentiment?key=AIzaSyA9C6lQ1iSAs8Vc-y-AxnLsOr86zgR94QY";

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
        String url = "https://language.googleapis.com/v2/documents:classifyText?key=AIzaSyA9C6lQ1iSAs8Vc-y-AxnLsOr86zgR94QY";

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

    public static List<String> moderateText(String text) {
        OkHttpClient client = new OkHttpClient();

        // Specify the URL you want to send the request to
        String url = "https://language.googleapis.com/v2/documents:moderateText?key=AIzaSyA9C6lQ1iSAs8Vc-y-AxnLsOr86zgR94QY";

        if (url.isEmpty() || url.isBlank() || url.equals("")) {
            List<String> defaultList = new ArrayList<>();
            return defaultList;
        }

        org.json.JSONObject document = new org.json.JSONObject();
        document.put("type", "PLAIN_TEXT");
        document.put("content", text);

        org.json.JSONObject requestBody = new org.json.JSONObject();
        requestBody.put("document", document);


        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), String.valueOf(requestBody)))
                .build();

        // Execute the request and get the response
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String responseBody = response.body().string();

            return extractModerationCategories(responseBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static List<String> extractModerationCategories(String responseBody) {
        List<String> categories = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray moderationCategories = jsonObject.getJSONArray("moderationCategories");

        for (int i = 0; i < moderationCategories.length(); i++) {
            JSONObject categoryObject = moderationCategories.getJSONObject(i);
            double confidence = categoryObject.getDouble("confidence");
            if (confidence > 0.25) {
                String name = categoryObject.getString("name");
                if (!name.equals("Legal") && !name.equals("Politics") && !name.equals("Finance") &&
                        !name.equals("War & Conflict") && !name.equals("Illicit Drugs") &&
                        !name.equals("Religion & Belief") && !name.equals("Health") &&
                        !name.equals("Public Safety") && !name.equals("Firearms & Weapons")) {
                    categories.add(name);
                }
            }
        }

        return categories;
    }
}
