package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@AllArgsConstructor
public class SeatLock {

    private Seat seat;
    private String lockedBy;
    private Show show;
    private Integer timeoutInSecs;
    private Date lockTime;

    public boolean isLockExpired() {
        final Instant lockInstant = lockTime.toInstant().plusSeconds(timeoutInSecs);
        final Instant currentInstant = new Date().toInstant();

        return lockInstant.isAfter(currentInstant);
    }

}
