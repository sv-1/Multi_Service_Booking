package com.yourcompany.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Hotel name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @Min(value = 1, message = "Total rooms must be at least 1")
    private int totalRooms;

    private int availableRooms;


    public Hotel() {}

    public Hotel(String name, String location, int totalRooms) {
        this.name = name;
        this.location = location;
        this.totalRooms = totalRooms;
        this.availableRooms = totalRooms;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }



    public boolean hasAvailableRooms(int requestedRooms) {
        return availableRooms >= requestedRooms;
    }

    public void bookRooms(int rooms) {
        if (availableRooms >= rooms) {
            availableRooms -= rooms;
        } else {
            throw new IllegalStateException("Not enough available rooms.");
        }
    }

    public void cancelRooms(int rooms) {
        if (availableRooms + rooms <= totalRooms) {
            availableRooms += rooms;
        } else {
            availableRooms = totalRooms; // prevent exceeding total
        }
    }
}
