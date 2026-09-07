package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookingModel {

    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private BookingDatesModel bookingdates;
    private String additionalneeds;
    public BookingModel() {}



    @Getter
    @Setter
    @AllArgsConstructor
    public static class BookingDatesModel {
        private String checkin;
        private String checkout;
    }
}
