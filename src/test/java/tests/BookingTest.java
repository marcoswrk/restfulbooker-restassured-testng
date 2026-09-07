package tests;

import base.BaseTest;
import io.restassured.RestAssured;
import org.testng.annotations.*;
import static io.restassured.RestAssured.*;
import api.AuthApi;

public class BookingTest extends BaseTest {
    private int bookingId;

    @BeforeMethod
    public void postBookingForUpdate() {
        bookingId = given()
                .contentType("application/json")
                .body("{\"firstname\": \"Teste\", \"lastname\": \"Teste\", \"totalprice\": 123, \"depositpaid\": true, \"bookingdates\": {\"checkin\": \"2026-09-01\", \"checkout\": \"2026-09-12\"}, \"additionalneeds\": \"Breakfast\"}")
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .log().all()
                .extract().path("bookingid");

    }

    @Test
    public void postBooking() {
             given()
            .contentType("application/json")
            .body("{\"firstname\": \"Teste\", \"lastname\": \"Teste\", \"totalprice\": 123, \"depositpaid\": true, \"bookingdates\": {\"checkin\": \"2026-09-01\", \"checkout\": \"2026-09-12\"}, \"additionalneeds\": \"Breakfast\"}")
            .when()
                .post("/booking")
            .then()
                .statusCode(200)
                .log().all()
        ;
    }

    @Test
    public void getCreatedBooking() {
        given()
                .contentType("application/json")
                .get("/booking/" + bookingId)
                .then()
                    .statusCode(200)
                    .log().all()
        ;
    }

    @Test
    public void putBooking() {
        String token =  AuthApi.generateToken();
        given()
                .contentType("application/json")
                .header("Cookie", "token=" + token)
                .body("{\"firstname\": \"Teste\", \"lastname\": \"Update\", \"totalprice\": 123, \"depositpaid\": true, \"bookingdates\": {\"checkin\": \"2026-09-01\", \"checkout\": \"2026-09-12\"}, \"additionalneeds\": \"Breakfast\"}")
                .when()
                .log().all()
                .put("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .log().all()
        ;
    }

    @Test
    public void patchBooking() {
        String token = AuthApi.generateToken();
        given()
                .contentType("application/json")
                .header("Cookie", "token=" + token)
                .body("{\"firstname\": \"Teste\", \"lastname\": \"Patch\", \"totalprice\": 123, \"depositpaid\": true, \"bookingdates\": {\"checkin\": \"2026-09-01\", \"checkout\": \"2026-09-12\"}, \"additionalneeds\": \"Breakfast\"}")
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
                .contentType("application/json")
                .header("Cookie", "token=" + token)
                .when()
                .delete("/booking/" + bookingId)
                .then()
                .statusCode(201)
                .log().all();

        given()
                .contentType("application/json")
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
                .contentType("application/json")
                .when()
                .get("/booking")
                .then()
                .statusCode(200);
    }
    @Test
    public void getBookingByNameAndLastName() {
        given()
                .contentType("application/json")
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
                .contentType("application/json")
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
                .contentType("application/json")
                .queryParam("checkin", "1923009-01")
                .queryParam("checkout", "2026-09-12")
                .when()
                .get("/booking")
                .then()
                .statusCode(500)
                .log().all();
    }
}
