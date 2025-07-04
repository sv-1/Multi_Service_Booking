package com.yourcompany.booking.service;

import com.yourcompany.booking.exception.ResourceNotFoundException;
import com.yourcompany.booking.model.Hotel;
import com.yourcompany.booking.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;


    public List<Hotel> getAvailableHotels() {
        return hotelRepository.findByAvailableRoomsGreaterThan(0);
    }

        public List<Hotel> getHotelsByLocation(String location) {
        return hotelRepository.findByLocationIgnoreCase(location);
    }


    public List<Hotel> searchHotelsByName(String keyword) {
        return hotelRepository.findByNameContainingIgnoreCase(keyword);
    }


    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
    }


    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }


    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hotel not found");
        }
        hotelRepository.deleteById(id);
    }
}
