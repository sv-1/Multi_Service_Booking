package com.yourcompany.booking.repository;

import com.yourcompany.booking.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {


    List<Movie> findByLanguageIgnoreCase(String language);


    List<Movie> findByAvailableSeatsGreaterThan(int count);


    List<Movie> findByTitleContainingIgnoreCase(String keyword);


    List<Movie> findByShowtime(LocalDateTime showtime);


    Optional<Movie> findByTitle(String title);
}
