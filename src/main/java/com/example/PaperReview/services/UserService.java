package com.example.PaperReview.services;

import com.example.PaperReview.models.Organization;
import com.example.PaperReview.models.User;
import com.example.PaperReview.repositories.OrganizationRepository;
import com.example.PaperReview.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }
    public boolean putUser(User user, String password) {
        //if username alread exists, return false
        if (UserRepository.isUserExists(user.getUsername())) {
            return false;
        }
        //if organization name does not exists, return false
        if (OrganizationRepository.exists(user.getOrganization().getName())) {
            Organization org = OrganizationRepository.findByName(user.getOrganization().getName());
            user.setOrganization(org);

            try {
                String encryptedPassword = passwordEncoder.encode(password);

                // Save the user to the database
                jdbcTemplate.update("INSERT INTO users (username, password, enabled) VALUES (?, ?, ?)",
                        user.getUsername(), encryptedPassword, true);
                // save role to db
                jdbcTemplate.update("INSERT INTO authorities (username, authority) VALUES (?, ?)", user.getUsername(), "USER");

                // save the user to in mem repository
                UserRepository.putUser(user);

                return true;
            } catch (Exception e) { //if any error, return false
                return false;
            }
        }
        return false;
    }
}
