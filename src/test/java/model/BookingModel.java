package model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingModel {

    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private BookingDatesModel bookingdates;
    private String additionalneeds;

    public BookingModel() {}

    public BookingModel(String firstname, String lastname, int totalprice, boolean depositpaid, BookingDatesModel bookingdates, String additionalneeds) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
        this.additionalneeds = additionalneeds;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public Boolean isDepositpaid() {
        return depositpaid;
    }

    public BookingDatesModel getBookingdates() {
        return bookingdates;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public void setBookingdates(BookingDatesModel bookingdates) {
        this.bookingdates = bookingdates;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }

    public static class BookingDatesModel {
        private String checkin;
        private String checkout;

        public BookingDatesModel() {}

        public BookingDatesModel(String checkin, String checkout) {
            this.checkin = checkin;
            this.checkout = checkout;
        }

        public String getCheckin() {
            return checkin;
        }

        public String getCheckout() {
            return checkout;
        }

        public void setCheckin(String checkin) {
            this.checkin = checkin;
        }

        public void setCheckout(String checkout) {
            this.checkout = checkout;
        }
    }

}
