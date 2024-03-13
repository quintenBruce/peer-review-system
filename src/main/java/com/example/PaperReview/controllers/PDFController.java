package com.example.PaperReview.controllers;

import com.example.PaperReview.repositories.PaperRepository;
import com.example.PaperReview.services.PDFService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/pdf")
public class PDFController {
    private final PDFService pdfService;

    @Autowired
    public PDFController(PDFService pdfService) {
        this.pdfService = pdfService;
    }
    @GetMapping("/view")
    public void viewPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=yourFileName.pdf");

        try (InputStream is = new ByteArrayInputStream(PDFService.toByteArray("src/main/java/com/example/PaperReview/Projects - Tagged.pdf"))) {
            IOUtils.copy(is, response.getOutputStream());
        }
    }
    @GetMapping("/view/{id}")
    public void viewPdf(@PathVariable int id, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=yourFileName.pdf");

        byte[] content = PaperRepository.getContentById(id);

        if (content != null) {
            try (InputStream is = new ByteArrayInputStream(content)) {
                IOUtils.copy(is, response.getOutputStream());
            }
        } else {
            // Handle the case where content is not found for the given ID
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}


