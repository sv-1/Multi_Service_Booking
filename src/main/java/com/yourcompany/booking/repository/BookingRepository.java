package com.yourcompany.booking.repository;

import com.yourcompany.booking.model.Booking;
import com.yourcompany.booking.model.User;
import com.yourcompany.booking.model.enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);


    List<Booking> findByUserAndServiceType(User user, ServiceType serviceType);


    List<Booking> findByServiceType(ServiceType serviceType);


    List<Booking> findByCancelled(boolean cancelled);


    @Query("SELECT b FROM Booking b WHERE b.eventDate = :eventDate AND b.cancelled = false")
    List<Booking> findUpcomingBookingsForReminder(LocalDate eventDate);


    long countByServiceType(ServiceType serviceType);
    long countByCancelledTrue();
    long countByCancelledFalse();


    long countByUserAndCancelledFalse(User user);

    @Query("SELECT b FROM Booking b WHERE b.user = :user AND b.serviceType = :serviceType AND b.eventDate = :eventDate AND b.cancelled = false")
    List<Booking> findConflictingBooking(User user, ServiceType serviceType, LocalDate eventDate);
}
