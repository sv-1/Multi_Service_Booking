package com.yourcompany.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Movie name is required")
    private String title;

    @NotBlank(message = "Language is required")
    private String language;

    @NotNull(message = "Showtime is required")
    private LocalDateTime showtime;

    @Min(value = 1, message = "Total seats must be at least 1")
    private int totalSeats;

    private int availableSeats;


    public Movie() {}

    public Movie(String title, String language, LocalDateTime showtime, int totalSeats) {
        this.title = title;
        this.language = language;
        this.showtime = showtime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDateTime getShowtime() {
        return showtime;
    }

    public void setShowtime(LocalDateTime showtime) {
        this.showtime = showtime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }


    public boolean hasAvailableSeats(int requestedSeats) {
        return availableSeats >= requestedSeats;
    }

    public void bookSeats(int count) {
        if (count <= 0) return;
        if (availableSeats >= count) {
            availableSeats -= count;
        } else {
            throw new IllegalStateException("Not enough available seats.");
        }
    }

    public void cancelSeats(int count) {
        if (count <= 0) return;
        if (availableSeats + count <= totalSeats) {
            availableSeats += count;
        } else {
            availableSeats = totalSeats;
        }
    }
}
