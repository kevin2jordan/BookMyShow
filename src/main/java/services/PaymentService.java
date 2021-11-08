package services;

import exceptions.BadRequestException;
import lombok.NonNull;
import model.Booking;
import providers.SeatLockProvider;

import java.util.HashMap;
import java.util.Map;

public class PaymentService {

    private Map<Booking, Integer> bookingFailures;
    private final Integer allowedRetries;
    private final SeatLockProvider seatLockProvider;

    public PaymentService(@NonNull final Integer allowedRetries, final SeatLockProvider seatLockProvider) {
        this.allowedRetries = allowedRetries;
        this.seatLockProvider = seatLockProvider;
        bookingFailures = new HashMap<>();
    }

    public void processFailedPayment(@NonNull final Booking booking, @NonNull final String userId) {
        if(!booking.getUserId().equals(userId)) {
            throw new BadRequestException("Bad request");
        }
        if(!bookingFailures.containsKey(booking)){
            bookingFailures.put(booking, 0);
        }
        final Integer currentFailedCount = bookingFailures.get(booking);
        final Integer newFailedCount = currentFailedCount + 1;
        bookingFailures.put(booking, newFailedCount);
        if(newFailedCount > allowedRetries) {
            seatLockProvider.unLockSeats(booking.getShow(), booking.getBookedSeats(),userId);
        }
    }
}
