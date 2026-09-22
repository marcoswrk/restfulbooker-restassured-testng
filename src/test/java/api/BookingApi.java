package api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.BookingModel;
import specs.Specs;

import static io.restassured.RestAssured.given;

public class BookingApi {

    private static RequestSpecification request() {
        return given(Specs.requestSpec());
    }

    public static Response createBooking(BookingModel booking) {
        return request()
                .body(booking)
                .when()
                .post("/booking");
    }

    public static Response getBooking(int bookingId) {
        return request()
                .pathParam("bookingId", bookingId)
                .when()
                .get("/booking/{bookingId}");
    }

    public static Response deleteBooking(int bookingId, String token) {
        return request()
                .cookie("token", token)
                .pathParam("bookingId", bookingId)
                .when()
                .delete("/booking/{bookingId}");
    }

    public static Response getBookingByFilter(String firstname, String lastname) {
        return request()
                .queryParam("firstname", firstname)
                .queryParam("lastname", lastname)
                .when()
                .get("/booking");
    }

    public static Response updateBooking(int bookingId, BookingModel booking, String token) {
        return request()
                .cookie("token", token)
                .pathParam("bookingId", bookingId)
                .body(booking)
                .when()
                .put("/booking/{bookingId}");
    }

    public static Response patchBooking(int bookingId, BookingModel booking, String token) {
        return request()
                .cookie("token", token)
                .pathParam("bookingId", bookingId)
                .body(booking)
                .when()
                .patch("/booking/{bookingId}");
    }

    public static Response getAllBookings() {
        return request()
                .when()
                .get("/booking");
    }

    public static Response getBookingByCheckinCheckout(String checkin, String checkout) {
        return request()
                .queryParam("checkin", checkin)
                .queryParam("checkout", checkout)
                .when()
                .get("/booking");
    }
}