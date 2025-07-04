package com.yourcompany.booking.service;

import com.yourcompany.booking.model.Booking;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private BookingService bookingService;

    public void sendBookingConfirmation(Booking booking) {
        String subject = "Booking Confirmation - " + booking.getServiceType();
        String message = "Hi " + booking.getUser().getFullName() + ",\n\n" +
                "Your booking for " + booking.getServiceName() + " on " + booking.getEventDate() + " has been confirmed.\n" +
                "Thank you for using our service!\n\nRegards,\nMulti-Service Booking Team";

        sendEmail(booking.getUser().getEmail(), subject, message);
    }

    public void sendCancellationNotification(Booking booking) {
        String subject = "Booking Cancelled - " + booking.getServiceType();
        String message = "Hi " + booking.getUser().getFullName() + ",\n\n" +
                "Your booking for " + booking.getServiceName() + " on " + booking.getEventDate() + " has been cancelled.\n" +
                "If this was a mistake, please book again.\n\nRegards,\nMulti-Service Booking Team";

        sendEmail(booking.getUser().getEmail(), subject, message);
    }

    // Scheduled reminder (every day at 8 AM)
    @Scheduled(cron = "0 0 8 * * *")
    public void sendReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Booking> bookings = bookingService.getBookingsByEventDate(tomorrow);

        for (Booking booking : bookings) {
            String subject = "Reminder: Upcoming Booking - " + booking.getServiceName();
            String message = "Hi " + booking.getUser().getFullName() + ",\n\n" +
                    "This is a reminder for your upcoming booking on " + booking.getEventDate() + " for:\n" +
                    booking.getServiceName() + " (" + booking.getServiceType() + ").\n\n" +
                    "Please be on time.\n\nRegards,\nMulti-Service Booking Team";

            sendEmail(booking.getUser().getEmail(), subject, message);
        }
    }

    private void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            mailSender.send(message);
        } catch (MessagingException e) {
            // log error (optional)
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
    public void sendReminderEmail(Booking booking) {
        String subject = "Reminder: Your " + booking.getServiceType() + " Booking Tomorrow";
        String message = "Hi " + booking.getUser().getFullName() + ",\n\n"
                + "This is a reminder for your upcoming booking for " + booking.getServiceName()
                + " on " + booking.getEventDate() + ".\n\n"
                + "Please be on time!\n\nRegards,\nMulti-Service Booking Team";

        sendEmail(booking.getUser().getEmail(), subject, message);
    }

}
