package com.example.PaperReview.controllers;

import com.example.PaperReview.models.Organization;
import com.example.PaperReview.models.User;
import com.example.PaperReview.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.regex.Pattern;

@Controller
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService _userService) {
        this.userService = _userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Return the name of the login view (HTML file name without extension)
    }

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "signup"; // Return the name of the sign-up view (HTML file name without extension)
    }

    // Add a method to handle sign-up form submission
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) {
        if (!signUpRequest.isValid()) {
            return ResponseEntity.badRequest().body("Invalid SignUpRequest");
        }

        User user = new User(signUpRequest.getUsername());
        user.setOrganization(new Organization(signUpRequest.getOrganization()));
        user.setEmail(signUpRequest.getEmail());
        boolean status = userService.putUser(user, signUpRequest.getPassword());

        if (status) {
            return ResponseEntity.ok("User registered successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class SignUpRequest {
        private String username;
        private String password;
        private String email;
        private String organization;

        // Regular expression for email validation
        private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        public boolean isValid() {
            // Check if username is not null and not empty
            if (this.username == null || this.username.isEmpty()) {
                return false;
            }

            // Check if password is not null and has at least 8 characters
            if (this.password == null) {
                return false;
            }

            // Check if email is valid using regular expression
            if (this.email == null || !Pattern.matches(EMAIL_REGEX, this.email)) {
                return false;
            }

            // Check if organization is not null and not empty
            if (this.organization == null || this.organization.isEmpty()) {
                return false;
            }

            // All fields are valid
            return true;
        }
    }
}
