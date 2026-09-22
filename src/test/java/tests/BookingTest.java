package tests;

import base.BaseTest;
import model.BookingModel;
import org.testng.annotations.*;

import static java.lang.Math.log;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import api.BookingApi;
import specs.Specs;
import utils.TestData;
import api.AuthApi;

import java.time.LocalDate;

public class BookingTest extends BaseTest {
    private int bookingId;
    private BookingModel createdBooking;

    @BeforeMethod
    public void postBookingForUpdate() {
        createdBooking = TestData.randomBooking();
        bookingId = createWithRetry(createdBooking);
    }

    private int createWithRetry(BookingModel booking) {
        int tentativas = 0;
        while (true) {
            var response = BookingApi.createBooking(booking);
            if (response.statusCode() == 200) {
                return response.path("bookingid");
            }
            tentativas++;
            if (tentativas >= 3) {
                response.then().statusCode(200); // força a falha real, com a mensagem do RestAssured
            }
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    @Test
    public void getCreatedBooking() {
        BookingApi.getBooking(bookingId)
        .then()
            .log().ifValidationFails()
            .statusCode(200)
            .body("firstname", equalTo(createdBooking.getFirstname()));
    }

    @Test
    public void putBooking() {
        String token =  AuthApi.generateToken();
        BookingModel updated = TestData.randomBooking();

        BookingApi.updateBooking(bookingId, updated, token)
        .then()
            .log().ifValidationFails()
            .statusCode(200)
                .body("firstname", equalTo(updated.getFirstname()))
                .body("lastname", equalTo(updated.getLastname()));
    }

    @Test
    public void patchBooking() {
        String token = AuthApi.generateToken();
        BookingModel patch = TestData.randomFirstNamePatch(createdBooking.getFirstname());

        BookingApi.patchBooking(bookingId, patch, token)
                .then()
                .statusCode(200)
                .body("firstname", equalTo(patch.getFirstname()))
                .body("lastname", equalTo(createdBooking.getLastname()));

        BookingApi.getBooking(bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo(patch.getFirstname()))
                .body("lastname", equalTo(createdBooking.getLastname()));
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
            .log().ifValidationFails()
            .statusCode(404);
    }
    //booking filters
    @Test
    public void getAllBookings() {
        BookingApi.getAllBookings()
                .then()
                .statusCode(200)
                .contentType(io.restassured.http.ContentType.JSON)
                .log().ifValidationFails();
    }

    @Test
    public void getBookingByNameAndLastName() {
       BookingApi.getBookingByFilter(createdBooking.getFirstname(), createdBooking.getLastname())
        .then()
             .log().ifValidationFails()
             .statusCode(200)
             .body("bookingid", hasItem(bookingId));

    }

    @Test
    public void getBookingByCheckInAndCheckOut() {
        LocalDate checkin = LocalDate.parse(createdBooking.getBookingdates().getCheckin());
        LocalDate checkout = LocalDate.parse(createdBooking.getBookingdates().getCheckout());
                BookingApi.getBookingByCheckinCheckout(
                        checkin.minusDays(1).toString(),
                        checkout.plusDays(1).toString())
                .then()
                .statusCode(200)
                .body("bookingid", hasItem(bookingId));
    }

    @Test
    public void getInvalidDateBooking() {
        BookingApi.getBookingByCheckinCheckout("1923009-01", "2026-09-12")
            .then()
            .log().ifValidationFails()
            .statusCode(500);
    }
}
