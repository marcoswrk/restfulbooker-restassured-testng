package tests;

import base.BaseTest;
import model.BookingModel;
import org.testng.annotations.*;
import static io.restassured.RestAssured.*;
import api.AuthApi;

public class BookingTest extends BaseTest {
    private int bookingId;

    @BeforeMethod
    public void postBookingForUpdate() {
        BookingModel.BookingDatesModel dates = new BookingModel.BookingDatesModel("2026-09-01", "2026-09-12");
        BookingModel booking = new BookingModel("Teste", "Teste", 123, true, "Breakfast", dates);
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
        BookingModel.BookingDatesModel dates = new BookingModel.BookingDatesModel("2026-09-01", "2026-09-12");
        BookingModel booking = new BookingModel("Teste", "Teste", 123, true, "Breakfast", dates);
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
        .when()
            .get("/booking/" + bookingId)
        .then()
            .statusCode(200)
            .log().all();
    }

    @Test
    public void putBooking() {
        BookingModel.BookingDatesModel dates = new BookingModel.BookingDatesModel("2026-09-01", "2026-09-12");
        BookingModel booking = new BookingModel("Teste", "Teste", 123, true, "Breakfast", dates);
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
        BookingModel.BookingDatesModel dates = new BookingModel.BookingDatesModel("2026-09-01", "2026-09-12");
        BookingModel booking = new BookingModel("Teste", "Patch", 123, true, "Breakfast", dates);
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
    public void getBookingById() {
        given()
                .pathParam("bookingId", 122)
        .when()
            .get("/booking/{bookingId}")
        .then()
            .statusCode(200)
            .log().all();
    }

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
