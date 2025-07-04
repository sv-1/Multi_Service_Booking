package com.yourcompany.booking.controller;

import com.yourcompany.booking.model.*;
import com.yourcompany.booking.model.enums.ServiceType;
import com.yourcompany.booking.repository.*;
import com.yourcompany.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class BookingController {

    @Autowired private BookingService bookingService;
    @Autowired private TrainRepository trainRepository;
    @Autowired private MovieRepository movieRepository;
    @Autowired private HotelRepository hotelRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/train")
    public String showTrainBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("trainList", trainRepository.findAll());
        return "train-booking";
    }

    @PostMapping("/train")
    public String bookTrain(@Valid @ModelAttribute("booking") Booking booking,
                            BindingResult result,
                            Authentication authentication,
                            Model model) {
        booking.setServiceType(ServiceType.TRAIN);
        model.addAttribute("trainList", trainRepository.findAll());

        if (result.hasErrors()) {
            model.addAttribute("booking", booking);
            return "train-booking";
        }

        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalStateException("User not found with email: " + email));

            Long selectedTrainId = booking.getSelectedTrainId();
            if (selectedTrainId == null) throw new IllegalArgumentException("Train ID must not be null");

            Train train = trainRepository.findById(selectedTrainId)
                    .orElseThrow(() -> new IllegalArgumentException("Train not found with ID: " + selectedTrainId));

            booking.setTrain(train);
            booking.setSelectedTrainId(selectedTrainId);

            bookingService.createBooking(
                    user.getId(),
                    ServiceType.TRAIN,
                    selectedTrainId,
                    booking.getEventDate(),
                    null,
                    booking.getSeats()
            );

            model.addAttribute("successMessage", "Train booked successfully!");
            model.addAttribute("booking", new Booking());

        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Booking failed: " + ex.getMessage());
            model.addAttribute("booking", booking);
        }

        return "train-booking";
    }
    @GetMapping("/movie")
    public String showMovieBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("movieList", movieRepository.findAll());
        return "movie-booking";
    }

    @PostMapping("/movie")
    public String bookMovie(@Valid @ModelAttribute("booking") Booking booking,
                            BindingResult result,
                            Authentication authentication,
                            Model model) {
        booking.setServiceType(ServiceType.MOVIE);
        model.addAttribute("movieList", movieRepository.findAll());

        if (result.hasErrors()) {
            model.addAttribute("booking", booking);
            return "movie-booking";
        }

        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalStateException("User not found with email: " + email));

            Long selectedMovieId = booking.getSelectedMovieId();
            if (selectedMovieId == null) throw new IllegalArgumentException("Movie ID must not be null");

            Movie movie = movieRepository.findById(selectedMovieId)
                    .orElseThrow(() -> new IllegalArgumentException("Movie not found with ID: " + selectedMovieId));

            booking.setMovie(movie);
            booking.setSelectedMovieId(selectedMovieId);

            bookingService.createBooking(
                    user.getId(),
                    ServiceType.MOVIE,
                    selectedMovieId,
                    booking.getEventDate(),
                    booking.getShowtime(),
                    booking.getSeats()
            );

            model.addAttribute("successMessage", "Movie booked successfully!");
            model.addAttribute("booking", new Booking());

        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Booking failed: " + ex.getMessage());
            model.addAttribute("booking", booking);
        }

        return "movie-booking";
    }
    @GetMapping("/hotel")
    public String showHotelBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("hotelList", hotelRepository.findAll());
        return "hotel-booking";
    }

    @PostMapping("/hotel")
    public String bookHotel(@Valid @ModelAttribute("booking") Booking booking,
                            BindingResult result,
                            Authentication authentication,
                            Model model) {
        booking.setServiceType(ServiceType.HOTEL);
        model.addAttribute("hotelList", hotelRepository.findAll());

        if (result.hasErrors()) {
            model.addAttribute("booking", booking);
            return "hotel-booking";
        }

        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalStateException("User not found with email: " + email));

            Long selectedHotelId = booking.getSelectedHotelId();
            if (selectedHotelId == null) throw new IllegalArgumentException("Hotel ID must not be null");

            Hotel hotel = hotelRepository.findById(selectedHotelId)
                    .orElseThrow(() -> new IllegalArgumentException("Hotel not found with ID: " + selectedHotelId));

            booking.setHotel(hotel);
            booking.setSelectedHotelId(selectedHotelId);

            bookingService.createBooking(
                    user.getId(),
                    ServiceType.HOTEL,
                    selectedHotelId,
                    booking.getEventDate(),
                    null,
                    booking.getSeats()
            );

            model.addAttribute("successMessage", "Hotel booked successfully!");
            model.addAttribute("booking", new Booking());

        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Booking failed: " + ex.getMessage());
            model.addAttribute("booking", booking);
        }

        return "hotel-booking";
    }
    @GetMapping("/bookings")
    public String viewUserBookings(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found with email: " + email));

        model.addAttribute("bookings", bookingService.getBookingsByUser(user));
        return "user-bookings";
    }
    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, Authentication authentication, Model model) {
        System.out.println(" Cancel request received for booking ID: " + id);
        try {
            bookingService.cancelBookingForUser(id, authentication.getName());
            System.out.println("Booking cancelled.");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(" Cancel failed: " + ex.getMessage());
            model.addAttribute("errorMessage", "Failed to cancel booking: " + ex.getMessage());
        }
        return "redirect:/user/bookings";
    }


}
