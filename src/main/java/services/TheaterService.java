package services;

import exceptions.BadRequestException;
import exceptions.NotFoundException;
import lombok.NonNull;
import model.Screen;
import model.Seat;
import model.Theatre;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TheaterService {

    private final Map<String, Theatre> theatres;
    private final Map<String, Screen> screens;
    private final Map<String, Seat> seats;

    public TheaterService() {
        theatres = new HashMap<>();
        screens = new HashMap<>();
        seats = new HashMap<>();
    }

    public Theatre getTheaterById(@NonNull final String theatreId) {
        if(!theatres.containsKey(theatreId)){
            throw new NotFoundException("Theatre not found");
        }
        return theatres.get(theatreId);
    }

    public Screen getScreenById(@NonNull final String screenId) {
        if(!screens.containsKey(screenId)){
            throw new NotFoundException("Screen not found");
        }
        return screens.get(screenId);
    }

    public Seat getSeatById(@NonNull final String seatId) {
        if(!seats.containsKey(seatId)){
            throw new NotFoundException("Screen not found");
        }
        return seats.get(seatId);
    }

    public Theatre createTheatre(@NonNull final String theatreName) {
        final Theatre theatre = new Theatre(UUID.randomUUID().toString(), theatreName);
        theatres.put(theatre.getTheaterId(), theatre);
        return theatre;
    }

    public Screen addScreenToTheatre(@NonNull final String screenName, @NonNull final Theatre theatre) {
        if(!theatres.containsKey(theatre.getTheaterId())){
            throw new NotFoundException("Theater not found exception");
        }
        final Theatre theatre1 = theatres.get(theatre.getTheaterId());
        Optional<Screen> screenOptional = theatre1.getScreens().stream()
        .filter(screen -> screen.getScreenName().equalsIgnoreCase(screenName)).findFirst();
        if(screenOptional.isPresent()){
            throw new BadRequestException("Bad request");
        }

        final Screen screen = new Screen(UUID.randomUUID().toString(), screenName, theatre.getTheaterId());
        theatre1.addScreen(screen);
        return screen;
    }

    public Seat addSeatInScreen(@NonNull final int rowNo, @NonNull final int seatNo, @NonNull final Screen screen) {
        final Seat seat = new Seat(UUID.randomUUID().toString(), rowNo, seatNo);
        screen.addSeat(seat);
        seats.put(seat.getSeatId(), seat);

        return seat;
    }
}
