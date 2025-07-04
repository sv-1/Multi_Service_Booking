package com.yourcompany.booking.model;

import com.yourcompany.booking.model.enums.ServiceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceType serviceType;

    @NotNull(message = "Event date is required")
    @Future(message = "Event date must be in the future")
    private LocalDate eventDate;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Column(nullable = false)
    private boolean cancelled = false;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime showtime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_id")
    private Train train;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Column(nullable = false)
    private String serviceName;

    @Min(value = 1, message = "At least 1 seat must be booked")
    @Max(value = 10, message = "You can book up to 10 seats only")
    private int seats;

    @Column(name = "train_name")
    private String trainName;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "hotel_name")
    private String hotelName;

    @Transient
    private Long selectedTrainId;

    @Transient
    private Long selectedMovieId;

    @Transient
    private Long selectedHotelId;


    @AssertTrue(message = "Please select a train")
    public boolean isTrainValid() {
        return isCancelled() || serviceType != ServiceType.TRAIN || selectedTrainId != null;
    }

    @AssertTrue(message = "Please select a movie")
    public boolean isMovieValid() {
        return isCancelled() || serviceType != ServiceType.MOVIE || selectedMovieId != null;
    }

    @AssertTrue(message = "Please select a hotel")
    public boolean isHotelValid() {
        return isCancelled() || serviceType != ServiceType.HOTEL || selectedHotelId != null;
    }

    @AssertTrue(message = "Showtime is required for movies")
    public boolean isShowtimeValid() {
        return isCancelled() || serviceType != ServiceType.MOVIE || showtime != null;
    }


    public Booking() {
        this.createdAt = LocalDate.now();
        this.bookingTime = LocalDateTime.now();
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public ServiceType getServiceType() { return serviceType; }
    public void setServiceType(ServiceType serviceType) { this.serviceType = serviceType; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }

    public boolean isCancelled() { return cancelled; }
    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }

    public LocalDateTime getShowtime() { return showtime; }
    public void setShowtime(LocalDateTime showtime) { this.showtime = showtime; }

    public Train getTrain() { return train; }
    public void setTrain(Train train) { this.train = train; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public Hotel getHotel() { return hotel; }
    public void setHotel(Hotel hotel) { this.hotel = hotel; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public Long getSelectedTrainId() { return selectedTrainId; }
    public void setSelectedTrainId(Long selectedTrainId) { this.selectedTrainId = selectedTrainId; }

    public Long getSelectedMovieId() { return selectedMovieId; }
    public void setSelectedMovieId(Long selectedMovieId) { this.selectedMovieId = selectedMovieId; }

    public Long getSelectedHotelId() { return selectedHotelId; }
    public void setSelectedHotelId(Long selectedHotelId) { this.selectedHotelId = selectedHotelId; }
}
