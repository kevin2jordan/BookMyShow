package providers;

import model.Seat;
import model.Show;

import java.util.List;

public interface SeatLockProvider {

    void lockSeats(Show show, List<Seat> seats, String userId);
    void unLockSeats(Show show, List<Seat> seats, String userId);
    boolean validateLock(Show show, Seat seat, String userId);

    List<Seat> getLockedSeats(Show show);

}
