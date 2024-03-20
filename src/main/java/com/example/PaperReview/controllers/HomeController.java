package com.example.PaperReview.controllers;

import com.example.PaperReview.models.User;
import com.example.PaperReview.repositories.UserRepository;
import com.example.PaperReview.services.PaperService;
import com.example.PaperReview.viewModels.HomeViewModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
    @GetMapping("/dashboard")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usern = auth.getName();
        User user = UserRepository.getUser(auth.getName());

        HomeViewModel viewModel = PaperService.generateHomeViewModel(user);

        modelAndView.addObject("homeViewModel", viewModel);
        modelAndView.addObject("username", user.getUsername());
        modelAndView.setViewName("dashboard"); // This corresponds to the file name without the extension
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView redirectToHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index"); // This corresponds to the file name without the extension
        return modelAndView;
    }
}
