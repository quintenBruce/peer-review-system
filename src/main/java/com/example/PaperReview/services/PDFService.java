package com.example.PaperReview.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


@Service
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
    public static byte[] toByteArray(String path) {
        try (PDDocument document = PDDocument.load(new File(path))) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

