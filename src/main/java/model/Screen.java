package model;

import exceptions.SeatAlreadyExistException;
import lombok.Getter;
import lombok.NonNull;

import java.util.LinkedList;
import java.util.List;

@Getter
public class Screen {

    private final String screenId;
    private final String screenName;
    private final String theatreId;

    List<Seat> seats;

    public Screen(@NonNull final String screenId, @NonNull final String screenName, @NonNull final String theatreId){
        this.screenId = screenId;
        this.screenName = screenName;
        this.theatreId = theatreId;
        this.seats = new LinkedList<>();
    }
    public void addSeat(@NonNull final Seat seat){
        if(seats.contains(seat)) {
            throw new SeatAlreadyExistException("Seat already exist exception");
        }
        seats.add(seat);
    }
}
