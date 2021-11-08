package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Show {

    private String showId;
    private Movie movie;
    private Date startTime;
    private int durationInMinutes;
    private Screen screen;
}
