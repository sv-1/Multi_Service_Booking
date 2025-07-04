package com.yourcompany.booking.service;

import com.yourcompany.booking.DTO.BookingSummaryDTO;
import com.yourcompany.booking.exception.ResourceNotFoundException;
import com.yourcompany.booking.model.*;
import com.yourcompany.booking.model.enums.ServiceType;
import com.yourcompany.booking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired private BookingRepository bookingRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private MovieRepository movieRepository;
    @Autowired private HotelRepository hotelRepository;
    @Autowired private TrainRepository trainRepository;

    @Transactional
    public Booking createBooking(Long userId, ServiceType serviceType, Long serviceId, LocalDate eventDate, LocalDateTime showtime, int seats) {
        if (userId == null || serviceId == null)
            throw new IllegalArgumentException("User ID and Service ID must not be null.");
        if (seats <= 0)
            throw new IllegalArgumentException("At least one seat must be booked.");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setServiceType(serviceType);
        booking.setEventDate(eventDate);
        booking.setSeats(seats);
        booking.setCancelled(false);
        booking.setCreatedAt(LocalDate.now());
        booking.setBookingTime(LocalDateTime.now());

        switch (serviceType) {
            case MOVIE -> {
                Movie movie = movieRepository.findById(serviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + serviceId));
                if (!movie.hasAvailableSeats(seats))
                    throw new IllegalStateException("Only " + movie.getAvailableSeats() + " seat(s) available.");
                movie.bookSeats(seats);
                movieRepository.save(movie);
                booking.setMovie(movie);
                booking.setMovieName(movie.getTitle());
                booking.setServiceName(movie.getTitle());
                booking.setShowtime(showtime);
                booking.setSelectedMovieId(serviceId);
            }

            case HOTEL -> {
                Hotel hotel = hotelRepository.findById(serviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + serviceId));
                if (!hotel.hasAvailableRooms(seats))
                    throw new IllegalStateException("Only " + hotel.getAvailableRooms() + " room(s) available.");
                hotel.bookRooms(seats);
                hotelRepository.save(hotel);
                booking.setHotel(hotel);
                booking.setHotelName(hotel.getName());
                booking.setServiceName(hotel.getName());
                booking.setSelectedHotelId(serviceId);
            }

            case TRAIN -> {
                Train train = trainRepository.findById(serviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("Train not found with ID: " + serviceId));
                if (train.getAvailableSeats() < seats)
                    throw new IllegalStateException("Only " + train.getAvailableSeats() + " seat(s) available.");
                for (int i = 0; i < seats; i++) train.bookSeat();
                trainRepository.save(train);
                booking.setTrain(train);
                booking.setTrainName(train.getName());
                booking.setServiceName(train.getName());
                booking.setSelectedTrainId(serviceId);
            }
        }

        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        if (booking.isCancelled())
            throw new IllegalStateException("This booking is already cancelled.");

        booking.setCancelled(true);
        int seatsToRestore = Math.max(booking.getSeats(), 1);

        switch (booking.getServiceType()) {
            case MOVIE -> {
                Long movieId = booking.getSelectedMovieId();
                Movie movie;
                if (movieId != null) {
                    movie = movieRepository.findById(movieId)
                            .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + movieId));
                } else if (booking.getMovie() != null) {
                    movie = movieRepository.findById(booking.getMovie().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Movie not found from booking reference"));
                } else {
                    throw new IllegalArgumentException("Selected movie ID missing in booking.");
                }
                movie.cancelSeats(seatsToRestore);
                movieRepository.save(movie);
            }

            case HOTEL -> {
                Long hotelId = booking.getSelectedHotelId();
                Hotel hotel;
                if (hotelId != null) {
                    hotel = hotelRepository.findById(hotelId)
                            .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));
                } else if (booking.getHotel() != null) {
                    hotel = hotelRepository.findById(booking.getHotel().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Hotel not found from booking reference"));
                } else {
                    throw new IllegalArgumentException("Selected hotel ID missing in booking.");
                }
                hotel.cancelRooms(seatsToRestore);
                hotelRepository.save(hotel);
            }

            case TRAIN -> {
                Long trainId = booking.getSelectedTrainId();
                Train train;
                if (trainId != null) {
                    train = trainRepository.findById(trainId)
                            .orElseThrow(() -> new ResourceNotFoundException("Train not found with ID: " + trainId));
                } else if (booking.getTrain() != null) {
                    train = trainRepository.findById(booking.getTrain().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Train not found from booking reference"));
                } else {
                    throw new IllegalArgumentException("Selected train ID missing in booking.");
                }
                train.setAvailableSeats(Math.min(train.getAvailableSeats() + seatsToRestore, train.getTotalSeats()));
                trainRepository.save(train);
            }
        }

        return bookingRepository.save(booking);
    }

    @Transactional
    public void cancelBookingForUser(Long bookingId, String email) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "ID", bookingId));

        if (!booking.getUser().getEmail().equalsIgnoreCase(email))
            throw new SecurityException("Unauthorized to cancel this booking.");

        if (booking.isCancelled())
            throw new IllegalStateException("Booking is already cancelled.");

        cancelBooking(bookingId);
    }

    @Transactional
    public void cancelBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "ID", id));

        if (booking.isCancelled())
            throw new IllegalStateException("Booking is already cancelled.");

        cancelBooking(id);
    }

    public List<Booking> getBookingsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        return bookingRepository.findByUser(user);
    }

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByEventDate(LocalDate date) {
        return bookingRepository.findUpcomingBookingsForReminder(date);
    }

    public long countByType(ServiceType type) {
        return bookingRepository.countByServiceType(type);
    }

    public long countActive() {
        return bookingRepository.countByCancelledFalse();
    }

    public long countCancelled() {
        return bookingRepository.countByCancelledTrue();
    }

    public long countBookings() {
        return bookingRepository.count();
    }

    public BookingSummaryDTO getBookingSummary() {
        return new BookingSummaryDTO(
                countByType(ServiceType.TRAIN),
                countByType(ServiceType.MOVIE),
                countByType(ServiceType.HOTEL)
        );
    }
}
