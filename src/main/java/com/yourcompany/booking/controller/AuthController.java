package com.yourcompany.booking.controller;

import com.yourcompany.booking.model.User;
import com.yourcompany.booking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Show the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user, Model model) {
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email already exists.");
            return "register";
        }

        // Encode password here to avoid circular dependency in UserService

        userService.registerUser(user); // Calls the save logic

        return "redirect:/login";
    }

    // Show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}
