package com.yourcompany.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {


    @GetMapping("/dashboard")
    public String showUserDashboard(Model model) {
        model.addAttribute("message", "Welcome to the User Dashboard!");
        return "user-dashboard"; // templates/user-dashboard.html
    }
}
