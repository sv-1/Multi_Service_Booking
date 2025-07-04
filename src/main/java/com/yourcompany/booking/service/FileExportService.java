package com.yourcompany.booking.service;

import com.yourcompany.booking.model.Booking;
import com.yourcompany.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class FileExportService {

    @Autowired
    private BookingRepository bookingRepository;

    public String exportBookingsToCSV(String filePath) {
        List<Booking> bookings = bookingRepository.findAll();

        try (FileWriter writer = new FileWriter(filePath)) {
            // Write CSV header
            writer.append("Booking ID,User Name,Email,Service Type,Service Name,Event Date,Cancelled\n");

            // Write booking data
            for (Booking booking : bookings) {
                writer.append(String.valueOf(booking.getId())).append(",")
                        .append(booking.getUser().getFullName()).append(",")
                        .append(booking.getUser().getEmail()).append(",")
                        .append(booking.getServiceType().toString()).append(",")
                        .append(booking.getServiceName()).append(",")
                        .append(booking.getEventDate().toString()).append(",")
                        .append(String.valueOf(booking.isCancelled()))
                        .append("\n");
            }

            return "Export successful: " + filePath;

        } catch (IOException e) {
            e.printStackTrace();
            return "Error exporting file: " + e.getMessage();
        }
    }
}
