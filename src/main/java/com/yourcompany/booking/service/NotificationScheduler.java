package com.yourcompany.booking.service;

import com.yourcompany.booking.model.Booking;
import com.yourcompany.booking.service.BookingService;
import com.yourcompany.booking.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class NotificationScheduler {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EmailService emailService;


    @Scheduled(cron = "0 0 8 * * *")  // 8:00 AM daily
    public void sendBookingReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Booking> bookings = bookingService.getBookingsByEventDate(tomorrow);

        for (Booking booking : bookings) {
            if (!booking.isCancelled()) {
                emailService.sendReminderEmail(booking);
            }
        }

        System.out.println("Reminder emails sent for event date: " + tomorrow);
    }
}
