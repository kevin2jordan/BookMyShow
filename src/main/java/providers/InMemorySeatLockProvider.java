package providers;

import exceptions.SeatTemporaryUnavailiableException;
import lombok.NonNull;
import model.Seat;
import model.SeatLock;
import model.Show;

import java.util.*;

public class InMemorySeatLockProvider implements SeatLockProvider {

    private final Integer lockTimeOut;
    private final Map<Show, Map<Seat, SeatLock>> locks;

    public InMemorySeatLockProvider(@NonNull final Integer lockTimeOut) {
        this.lockTimeOut = lockTimeOut;
        locks = new HashMap<>();
    }

    @Override
    public void lockSeats(Show show, List<Seat> seats, String userId) {

        for(Seat seat : seats) {
            if(isSeatLocked(show, seat)) {
                throw new SeatTemporaryUnavailiableException("Seat temporarily unavailiable exception");
            }
        }

        for(Seat seat : seats) {
            lockSeat(show, seat, userId, lockTimeOut);
        }

    }

    @Override
    public void unLockSeats(Show show, List<Seat> seats, String userId) {
        for(Seat seat : seats){
            if(validateLock(show, seat, userId)){
                unlockSeat(show, seat);
            }
        }
    }

    private void unlockSeat(Show show, Seat seat) {
        if(!locks.containsKey(show)){
            return;
        }
        locks.get(show).remove(seat);
    }

    @Override
    public boolean validateLock(Show show, Seat seat, String userId) {
        return isSeatLocked(show, seat) && locks.get(show).get(seat).getLockedBy().equals(userId);
    }

    @Override
    public List<Seat> getLockedSeats(Show show) {
        if(!locks.containsKey(show)){
            return new LinkedList<>();
        }
        final List<Seat> lockedSeats = new LinkedList<>();
        for(Seat seat : locks.get(show).keySet()){
            if(isSeatLocked(show, seat)){
                lockedSeats.add(seat);
            }
        }
        return lockedSeats;
    }

    private void lockSeat(Show show, Seat seat, String userId, Integer lockTimeOut) {
        final SeatLock seatLock = new SeatLock(seat,userId,show,lockTimeOut, new Date());
        if(!locks.containsKey(show)){
            locks.put(show,new HashMap<>());
        }
        locks.get(show).put(seat, seatLock);
    }

    private boolean isSeatLocked(Show show, Seat seat) {
        return locks.containsKey(show) && locks.get(show).containsKey(seat) && !locks.get(show).get(seat).isLockExpired();
    }

}
