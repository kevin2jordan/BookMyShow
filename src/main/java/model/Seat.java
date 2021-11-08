package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Seat {

    private String seatId;
    private int rowNumber;
    private int seatNumber;
}
