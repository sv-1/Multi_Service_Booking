package com.yourcompany.booking.service;

import com.yourcompany.booking.exception.ResourceNotFoundException;
import com.yourcompany.booking.model.Train;
import com.yourcompany.booking.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;


    public List<Train> getAvailableTrains() {
        return trainRepository.findByAvailableSeatsGreaterThan(0);
    }


    public List<Train> searchByName(String name) {
        return trainRepository.findByNameContainingIgnoreCase(name);
    }


    public List<Train> findByRoute(String sourceOrDestination) {
        return trainRepository.findBySourceIgnoreCaseOrDestinationIgnoreCase(sourceOrDestination, sourceOrDestination);
    }


    public Train getTrainById(Long id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with ID: " + id));
    }


    public Train saveTrain(Train train) {
        return trainRepository.save(train);
    }


    public void deleteTrain(Long id) {
        if (!trainRepository.existsById(id)) {
            throw new ResourceNotFoundException("Train not found with ID: " + id);
        }
        trainRepository.deleteById(id);
    }
}
