package tests;

import base.BaseTest;
import io.restassured.RestAssured;
import org.testng.annotations.*;
import static io.restassured.RestAssured.*;
import api.AuthApi;

public class BookingTest extends BaseTest {
    private int bookingId;

    @BeforeMethod
    public void createBookingForUpdate() {
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
    public void updateBooking() {
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
                .log().all()
                .patch("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void deleteBookingAndDeletionConfirmation() {
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
                .get("/booking/" + bookingId)
                .then()
                .statusCode(404)
                .log().all();
    }
}
