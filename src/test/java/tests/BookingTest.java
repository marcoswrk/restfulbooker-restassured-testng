package tests;

import base.BaseTest;
import model.BookingModel;
import org.testng.annotations.*;
import static org.hamcrest.Matchers.equalTo;
import api.BookingApi;
import utils.TestData;
import api.AuthApi;

public class BookingTest extends BaseTest {
    private int bookingId;
    private BookingModel createdBooking;

    @BeforeMethod
    public void postBookingForUpdate() {
        createdBooking = TestData.randomBooking();

        bookingId = BookingApi.createBooking(createdBooking)
        .then()
            .statusCode(200)
            .log().all()
            .extract().path("bookingid");

    }

    @Test
    public void postBooking() {
        BookingModel booking = TestData.randomBooking();

        BookingApi.createBooking(booking)
            .then()
            .statusCode(200)
            .log().all();
    }

    @Test
    public void getCreatedBooking() {
        BookingApi.getBooking(bookingId)
        .then()
            .statusCode(200)
            .body("firstname", equalTo(createdBooking.getFirstname()))
            .log().all();
    }

    @Test
    public void putBooking() {
        String token =  AuthApi.generateToken();
        BookingModel updated = TestData.randomBooking();

        BookingApi.updateBooking(bookingId, updated, token)
        .then()
            .statusCode(200)
            .log().all();
    }

    @Test
    public void patchBooking() {
        String token = AuthApi.generateToken();
        BookingModel patch = TestData.randomBooking();

        BookingApi.patchBooking(bookingId, patch, token)
        .then()
            .statusCode(200)
            .log().all();
    }

    @Test
    public void deleteBookingAndGetDeletionConfirmation() {
        String token = AuthApi.generateToken();
        BookingApi.deleteBooking(bookingId, token)
        .then()
             .statusCode(201);

        //Confirmation by getting the deleted booking id
        BookingApi.getBooking(bookingId)
        .then()
            .statusCode(404)
            .log().all();
    }
    //booking filters
    @Test
    public void getAllBookings() {
        BookingApi.getAllBookings()
            .then()
            .statusCode(200);
    }
    @Test
    public void getBookingByNameAndLastName() {
       BookingApi.getBookingByFilter(createdBooking.getFirstname(), createdBooking.getLastname())
        .then()
             .statusCode(200)
             .log().all();
    }
    @Test
    public void getBookingByCheckInAndCheckOut() {
       BookingApi.getBookingByFilter(
               createdBooking.getBookingdates().getCheckin(),
               createdBooking.getBookingdates().getCheckout())
        .then()
           .statusCode(200)
           .log().all();
    }

    @Test
    public void getInvalidDateBooking() {
        BookingApi.getBookingByCheckinCheckout("1923009-01", "2026-09-12")
            .then()
            .statusCode(500)
            .log().all();
    }
}
