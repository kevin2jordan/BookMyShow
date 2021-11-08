package services;

import exceptions.InvalidBookingException;
import exceptions.SeatPermanentlyUnavailiableException;
import lombok.NonNull;
import model.Booking;
import model.Seat;
import model.Show;
import providers.SeatLockProvider;

import java.util.*;
import java.util.stream.Collectors;

public class BookingService {

    private final Map<String, Booking> showBookings;
    private final SeatLockProvider seatLockProvider;

    public BookingService(SeatLockProvider seatLockProvider) {
        this.seatLockProvider = seatLockProvider;
        showBookings = new HashMap<>();
    }

    public Booking getBookingById(@NonNull final String bookingId){
        if(!showBookings.containsKey(bookingId)){
            throw new InvalidBookingException("Invalid booking id");
        }
        return showBookings.get(bookingId);
    }

    public List<Booking> getAllBookingsOfAShow(@NonNull final Show show) {
        List<Booking> bookings = new LinkedList<>();
        for(Booking booking : showBookings.values()){
            if(booking.getShow().equals(show)){
                bookings.add(booking);
            }
        }
        return bookings;
    }

    public Booking createBooking(@NonNull final String userId, @NonNull final Show show, @NonNull List<Seat> seats) {
        if(isAnySeatAlreadyBooked(show, seats)){
            throw new SeatPermanentlyUnavailiableException("Seats permanently unavailiable");
        }
        seatLockProvider.lockSeats(show,seats,userId);
        final Booking booking =  new Booking(UUID.randomUUID().toString(), show, seats, userId);
        showBookings.put(booking.getBookingId(), booking);

        return booking;
    }

    private boolean isAnySeatAlreadyBooked(Show show, List<Seat> seats) {
        final List<Seat> bookedSeats = getAllBookedSeats(show);
        for(Seat seat : seats){
            if(bookedSeats.contains(seat)){
                return true;
            }
        }
        return false;
    }

    public List<Seat> getAllBookedSeats(Show show) {

        return getAllBookingsOfAShow(show).stream()
               .filter(Booking::isBooingConfirmed)
               .map(Booking::getBookedSeats)
               .flatMap(Collection::stream)
               .collect(Collectors.toList());

    }
}
