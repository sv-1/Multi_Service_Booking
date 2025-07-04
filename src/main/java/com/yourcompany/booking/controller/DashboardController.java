package com.yourcompany.booking.controller;

import com.yourcompany.booking.model.enums.Role;
import com.yourcompany.booking.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }



    // Simple user dashboard (optional if needed directly)
    @GetMapping("/user/home")
    public String userDashboard() {
        return "user-dashboard"; // Create user-dashboard.html
    }
}
