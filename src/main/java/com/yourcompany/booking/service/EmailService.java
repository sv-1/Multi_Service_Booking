package com.yourcompany.booking.service;

import com.yourcompany.booking.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmailService {

    // 🚫 Mail sender removed
    // @Autowired
    // private JavaMailSender mailSender;

    @Autowired
    private BookingService bookingService;

    public void sendBookingConfirmation(Booking booking) {
        // Stub only
        System.out.println("Simulating booking confirmation email to: " + booking.getUser().getEmail());
    }

    public void sendCancellationNotification(Booking booking) {
        // Stub only
        System.out.println("Simulating cancellation email to: " + booking.getUser().getEmail());
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void sendReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Booking> bookings = bookingService.getBookingsByEventDate(tomorrow);

        for (Booking booking : bookings) {
            System.out.println("Simulating reminder for booking: " + booking.getServiceName() +
                    " for " + booking.getUser().getFullName());
        }
    }

    public void sendReminderEmail(Booking booking) {
        System.out.println("Simulating single reminder email for: " + booking.getUser().getEmail());
    }

    private void sendEmail(String to, String subject, String text) {
        // 🚫 No actual email logic now
        System.out.println("Mock email: To=" + to + ", Subject=" + subject);
    }
}