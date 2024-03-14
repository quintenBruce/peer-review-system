package com.example.PaperReview.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

    public static String firstPageToImage(byte[] pdf) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            PDDocument document = PDDocument.load(pdf);
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            if (document.getNumberOfPages() > 0) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);

                // Write image to ByteArrayOutputStream using ImageIO
                ImageIO.write(bim, "png", baos);
            }

            document.close();
            return Base64.encodeBase64String(baos.toByteArray());

        } catch (Throwable e) {
            e.printStackTrace(); // Log the exception for debugging
            return "";
        }
    }

}

