package tests;

import base.BaseTest;
import model.BookingModel;
import org.testng.annotations.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import api.AuthApi;

public class BookingTest extends BaseTest {
    private int bookingId;

    @BeforeMethod
    public void postBookingForUpdate() {
        BookingModel booking = new BookingModel("Teste", "Teste", 123, true, new BookingModel.BookingDatesModel("2026-09-01", "2026-09-12"), "Breakfast");
        bookingId = given()
                .body(booking)
        .when()
            .post("/booking")
        .then()
            .statusCode(200)
            .log().all()
            .extract().path("bookingid");

    }

    @Test
    public void postBooking() {
        BookingModel booking = new BookingModel("Teste", "Teste", 123, true, new BookingModel.BookingDatesModel("2026-09-01", "2026-09-12"), "Breakfast");
        given()
            .body(booking)
        .when()
            .post("/booking")
        .then()
            .statusCode(200)
            .log().all();
    }

    @Test
    public void getCreatedBooking() {
        given()
                .pathParam("bookingId", bookingId)
        .when()
            .get("/booking/{bookingId}")
        .then()
            .statusCode(200)
                .body("firstname", equalTo("Teste"))

            .log().all();
    }

    @Test
    public void putBooking() {
        BookingModel booking = new BookingModel("Teste", "Teste", 123, true, new BookingModel.BookingDatesModel("2026-09-01", "2026-09-12"), "Breakfast");
        String token =  AuthApi.generateToken();
        given()
            .header("Cookie", "token=" + token)
            .body(booking)
        .when()
            .log().all()
            .put("/booking/" + bookingId)
        .then()
            .statusCode(200)
            .log().all();
    }

    @Test
    public void patchBooking() {
        String token = AuthApi.generateToken();
        BookingModel booking = new BookingModel("Teste", "Patch", 123, true, new BookingModel.BookingDatesModel("2026-09-01", "2026-09-12"), "Breakfast");
        given()
            .header("Cookie", "token=" + token)
            .body(booking)
        .when()
            .patch("/booking/" + bookingId)
        .then()
            .statusCode(200)
            .log().all();
    }

    @Test
    public void deleteBookingAndGetDeletionConfirmation() {
        String token = AuthApi.generateToken();
        given()
            .header("Cookie", "token=" + token)
        .when()
            .delete("/booking/" + bookingId)
        .then()
             .statusCode(201)
             .log().all();

        given()
        .when()
            .get("/booking/" + bookingId)
        .then()
            .statusCode(404)
            .log().all();
    }
    //booking filters
    @Test
    public void getAllBookings() {
        given()
        .when()
            .get("/booking")
        .then()
            .statusCode(200);
    }
    @Test
    public void getBookingByNameAndLastName() {
        given()
             .queryParam("firstname", "Teste")
             .queryParam("lastname", "Teste")
        .when()
              .get("/booking")
        .then()
             .statusCode(200)
             .log().all();
    }
    @Test
    public void getBookingByCheckInAndCheckOut() {
        given()
            .queryParam("checkin", "2026-09-01")
            .queryParam("checkout", "2026-09-12")
        .when()
           .get("/booking")
        .then()
           .statusCode(200)
           .log().all();
    }

    @Test
    public void getInvalidDateBooking() {
        given()
             .queryParam("checkin", "1923009-01")
             .queryParam("checkout", "2026-09-12")
        .when()
            .get("/booking")
        .then()
            .statusCode(500)
            .log().all();
    }
}
