package api;

import io.restassured.response.Response;
import model.BookingModel;
import specs.Specs;
import static io.restassured.RestAssured.given;

public class BookingApi {

    public static Response createBooking(BookingModel booking) {
        return given(Specs.withJsonBody())
                .body(booking)
                .when()
                .post("/booking");

    }

    public static Response getBooking(int bookingId) {
        return given(Specs.base())
                .pathParam("bookingId", bookingId)
                .when()
                .get("/booking/{bookingId}");
    }

    public static Response deleteBooking(int bookingId, String token) {
        return given(Specs.authenticated(token))
                .pathParam("bookingId", bookingId)
                .when()
                .delete("/booking/{bookingId}");
    }

    public static Response getBookingByFilter(String firstname, String lastname) {
        return given(Specs.base())
                .queryParam("firstname", firstname)
                .queryParam("lastname", lastname)
                .when()
                .get("/booking");
    }

    public static Response updateBooking(int bookingId, BookingModel booking, String token) {
        return given(Specs.authenticatedWithJsonBody(token))
                .pathParam("bookingId", bookingId)
                .body(booking)
                .when()
                .put("/booking/{bookingId}");
    }
    public static Response patchBooking(int bookingId, BookingModel booking, String token) {
        return given(Specs.authenticatedWithJsonBody(token))
                .pathParam("bookingId", bookingId)
                .body(booking)
                .when()
                .patch("/booking/{bookingId}");
    }
    public static Response getAllBookings() {
        return given(Specs.base())
                .when()
                .get("/booking");
    }

    public static Response getBookingByCheckinCheckout(String checkin, String checkout) {
        return given(Specs.base())
                .queryParam("checkin", checkin)
                .queryParam("checkout", checkout)
                .when()
                .get("/booking");
    }
}