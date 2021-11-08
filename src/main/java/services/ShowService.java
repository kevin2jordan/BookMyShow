package services;

import exceptions.NotFoundException;
import lombok.NonNull;
import model.Movie;
import model.Screen;
import model.Show;

import java.util.*;

public class ShowService {


    private final Map<String, Show> shows;

    public ShowService() {
        shows = new HashMap<>();
    }

    public Show getShowById(@NonNull final String showId) {
        if(!shows.containsKey(showId)) {
            throw new NotFoundException("Show not found");
        }
        return shows.get(showId);
    }

    public Show createShow(@NonNull final Movie movie, @NonNull final Screen screen, @NonNull final Date startTime,
                           @NonNull final int durationInMins){

        if(!checkIfShowCreationAllowed(screen, startTime, durationInMins)){
            throw  new NotFoundException("Screen not availaible ");
        }
        final Show show = new Show(UUID.randomUUID().toString(), movie, startTime, durationInMins, screen);
        shows.put(show.getShowId(), show);
        return show;
    }

    private boolean checkIfShowCreationAllowed(Screen screen, Date startTime, int durationInMins) {
        return true;
    }

    public List<Show> getShowsForaScreen(@NonNull final Screen screen) {
        List<Show> response = new LinkedList<>();

        for(Show show : shows.values()){
            if(show.getScreen().equals(screen)){
                response.add(show);
            }
        }
        return response;
    }
}
