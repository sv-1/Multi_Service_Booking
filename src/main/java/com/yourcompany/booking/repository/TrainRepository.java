package com.yourcompany.booking.repository;

import com.yourcompany.booking.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train, Long> {


    List<Train> findBySourceIgnoreCaseAndDestinationIgnoreCase(String source, String destination);


    List<Train> findByAvailableSeatsGreaterThan(int count);


    List<Train> findByNameContainingIgnoreCase(String name);
    List<Train> findBySourceIgnoreCaseOrDestinationIgnoreCase(String source, String destination);
}
