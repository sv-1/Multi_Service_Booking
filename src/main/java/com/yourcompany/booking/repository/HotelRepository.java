package com.yourcompany.booking.repository;

import com.yourcompany.booking.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    // Find hotels by location (optional filter)
    List<Hotel> findByLocationIgnoreCase(String location);

    // Find hotels with available rooms
    List<Hotel> findByAvailableRoomsGreaterThan(int count);

    // Search hotels by name (partial match)
    List<Hotel> findByNameContainingIgnoreCase(String name);
    Optional<Hotel> findByName(String name);

}
