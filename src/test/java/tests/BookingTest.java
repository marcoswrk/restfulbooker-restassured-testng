package tests;

import base.BaseTest;
import model.BookingModel;
import org.testng.annotations.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import api.BookingApi;

import api.AuthApi;

public class BookingTest extends BaseTest {
    private int bookingId;

    @BeforeMethod
    public void postBookingForUpdate() {
        BookingModel booking = new BookingModel("Teste", "Teste", 123, true, new BookingModel.BookingDatesModel("2026-09-01", "2026-09-12"), "Breakfast");

        bookingId = BookingApi.createBooking(booking)
        .then()
            .statusCode(200)
            .log().all()
            .extract().path("bookingid");

    }

    @Test
    public void postBooking() {
        BookingModel booking = new BookingModel("Teste", "Teste", 123, true, new BookingModel.BookingDatesModel("2026-09-01", "2026-09-12"), "Breakfast");

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
            .body("firstname", equalTo("Teste"))
            .log().all();
    }

    @Test
    public void putBooking() {
        BookingModel booking = new BookingModel("Teste", "Teste", 123, true, new BookingModel.BookingDatesModel("2026-09-01", "2026-09-12"), "Breakfast");

        String token =  AuthApi.generateToken();
        BookingApi.updateBooking(bookingId, booking, token)
        .then()
            .statusCode(200)
            .log().all();
    }

    @Test
    public void patchBooking() {
        BookingModel booking = new BookingModel("Teste", "Patch", 123, true, new BookingModel.BookingDatesModel("2026-09-01", "2026-09-12"), "Breakfast");

        String token = AuthApi.generateToken();
        BookingApi.patchBooking(bookingId, booking, token)
        .then()
            .statusCode(200)
            .log().all();
    }

    @Test
    public void deleteBookingAndGetDeletionConfirmation() {
        String token = AuthApi.generateToken();
        BookingApi.deleteBooking(bookingId, token)
        .then()
             .statusCode(201)
             .log().all();
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
       BookingApi.getBookingByFilter("Teste", "Teste")
        .then()
             .statusCode(200)
             .log().all();
    }
    @Test
    public void getBookingByCheckInAndCheckOut() {
       BookingApi.getBookingByCheckinCheckout("2026-09-01", "2026-09-12")
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
