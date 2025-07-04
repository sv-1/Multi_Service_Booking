package com.yourcompany.booking.controller;

import com.yourcompany.booking.model.Booking;
import com.yourcompany.booking.model.Hotel;
import com.yourcompany.booking.model.Movie;
import com.yourcompany.booking.model.User;
import com.yourcompany.booking.repository.HotelRepository;
import com.yourcompany.booking.repository.MovieRepository;
import com.yourcompany.booking.service.BookingService;
import com.yourcompany.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final BookingService bookingService;
    private final HotelRepository hotelRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public AdminController(UserService userService,
                           BookingService bookingService,
                           HotelRepository hotelRepository,
                           MovieRepository movieRepository) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.hotelRepository = hotelRepository;
        this.movieRepository = movieRepository;
    }
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalBookings", bookingService.countBookings());
        model.addAttribute("summary", bookingService.getBookingSummary()); // BookingSummaryDTO
        model.addAttribute("activeBookings", bookingService.countActive());
        model.addAttribute("cancelledBookings", bookingService.countCancelled());
        model.addAttribute("totalUsers", userService.countUsers());
        return "admin-dashboard";
    }
    @GetMapping("/users")
    public String viewAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/bookings")
    public String showAllBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "all-bookings";
    }


    @PostMapping("/bookings/cancel/{id}")
    public String cancelBooking(@PathVariable Long id) {
        bookingService.cancelBookingById(id);
        return "redirect:/admin/bookings";
    }
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @GetMapping("/add-movie")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "add-movie";
    }

    @PostMapping("/add-movie")
    public String addMovie(@ModelAttribute Movie movie, RedirectAttributes redirectAttributes) {
        movieRepository.save(movie);
        redirectAttributes.addFlashAttribute("successMessage", "Movie added successfully!");
        return "redirect:/admin/dashboard";
    }
    @GetMapping("/add-hotel")
    public String showAddHotelForm(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "add-hotel";
    }

    @PostMapping("/add-hotel")
    public String addHotel(@ModelAttribute Hotel hotel, RedirectAttributes redirectAttributes) {
        hotel.setAvailableRooms(hotel.getTotalRooms()); // initial availability
        hotelRepository.save(hotel);
        redirectAttributes.addFlashAttribute("successMessage", "Hotel added successfully!");
        return "redirect:/admin/dashboard";
    }
}
