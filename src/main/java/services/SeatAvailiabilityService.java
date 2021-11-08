package services;

import lombok.NonNull;
import model.Seat;
import model.Show;
import providers.SeatLockProvider;

import java.util.List;

public class SeatAvailiabilityService {

    private final BookingService bookingService;
    public final SeatLockProvider seatLockProvider;

    public SeatAvailiabilityService(@NonNull BookingService bookingService, @NonNull SeatLockProvider seatLockProvider){
        this.bookingService = bookingService;
        this.seatLockProvider = seatLockProvider;
    }

    public List<Seat> getAvailiableSeats(@NonNull final Show show) {
        final List<Seat> allSeats = show.getScreen().getSeats();
        final List<Seat> unavailiableSeats = getUnavailiableSeats(show);

        allSeats.removeAll(unavailiableSeats);
        return allSeats;
    }

    private List<Seat> getUnavailiableSeats(@NonNull final Show show) {
        final List<Seat> unavailiableSeats = bookingService.getAllBookedSeats(show);
        unavailiableSeats.addAll(seatLockProvider.getLockedSeats(show));
        return unavailiableSeats;
    }
}
