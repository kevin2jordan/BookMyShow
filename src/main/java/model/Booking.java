package model;

import exceptions.InvalidStateException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import model.enums.BookingStatus;

import java.util.Date;
import java.util.List;

@Getter
public class Booking {

    private final String bookingId;
    private final Show show;
    private final List<Seat> bookedSeats;
    private final Date bookingDate;
    private final String userId;
    private BookingStatus bookingStatus;

    public Booking(@NonNull final String bookingId, @NonNull final Show show, @NonNull List<Seat> seats, @NonNull final String userId) {
        this.bookingId = bookingId;
        this.show = show;
        this.bookedSeats = seats;
        this.bookingDate = new Date();
        this.userId = userId;
        this.bookingStatus = BookingStatus.CREATED;
    }

    public boolean isBooingConfirmed() {
        return this.bookingStatus == BookingStatus.CONFIRMED;
    }

    public void confirmBooking() {
        if(this.bookingStatus != BookingStatus.CREATED) {
            throw new InvalidStateException("Invalid state");
        }
        this.bookingStatus = BookingStatus.CONFIRMED;
    }

    public void expireBooking() {
        if(this.bookingStatus != BookingStatus.CREATED) {
            throw new InvalidStateException("Invalid state");
        }
        this.bookingStatus = BookingStatus.EXPIRED;
    }
}


