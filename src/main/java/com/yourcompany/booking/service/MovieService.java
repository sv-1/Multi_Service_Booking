package com.yourcompany.booking.service;

import com.yourcompany.booking.exception.ResourceNotFoundException;
import com.yourcompany.booking.model.Movie;
import com.yourcompany.booking.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;


    public List<Movie> getAvailableMovies() {
        return movieRepository.findByAvailableSeatsGreaterThan(0);
    }


    public List<Movie> searchByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }


    public List<Movie> getByLanguage(String language) {
        return movieRepository.findByLanguageIgnoreCase(language);
    }


    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + id));
    }


    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }


    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie not found with ID: " + id);
        }
        movieRepository.deleteById(id);
    }
}
