package com.example.PaperReview.Services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PDFService {
    public static String extractText(String path) {
        File file = new File(path);
        try {
            PDDocument doc = PDDocument.load(file);
            return new PDFTextStripper().getText(doc);
        } catch (IOException e) {
            return "";
        }
    }
//    public static String extractText(byte[] blob) {
//
//    }
}

