package model;

import exceptions.ScreenAlreadyExitException;
import lombok.Getter;
import lombok.NonNull;

import java.util.LinkedList;
import java.util.List;

@Getter
public class Theatre {

    private String theaterId;
    private String theatreName;
    private List<Screen> screens;

    public Theatre(String theaterId, String theatreName) {
        this.theaterId = theaterId;
        this.theatreName = theatreName;
        screens = new LinkedList<>();
    }

    public void addScreen(@NonNull final Screen screen) {
        if(screens.contains(screen)) {
            throw new ScreenAlreadyExitException("Screen already exist");
        }
        screens.add(screen);
    }
}


