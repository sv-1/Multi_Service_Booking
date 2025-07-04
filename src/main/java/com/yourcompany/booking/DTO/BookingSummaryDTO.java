package com.yourcompany.booking.DTO;

public class BookingSummaryDTO {
    private long trainBookings;
    private long movieBookings;
    private long hotelBookings;
    private long totalBookings;

    public BookingSummaryDTO(long trainBookings, long movieBookings, long hotelBookings) {
        this.trainBookings = trainBookings;
        this.movieBookings = movieBookings;
        this.hotelBookings = hotelBookings;
        this.totalBookings = trainBookings + movieBookings + hotelBookings;
    }


    public long getTrainBookings() {
        return trainBookings;
    }

    public long getMovieBookings() {
        return movieBookings;
    }

    public long getHotelBookings() {
        return hotelBookings;
    }

    public long getTotalBookings() {
        return totalBookings;
    }
}
