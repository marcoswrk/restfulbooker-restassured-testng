package api;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured.*;
import io.restassured.response.Response;
import model.BookingModel;

public class BookingApi {

    public static Response createBooking(BookingModel booking) {
        return given()
                .body(booking)
                .when()
                .post("/booking");

    }

    public static Response getBooking(int bookingId) {
        return given()
                .pathParam("bookingId", bookingId)
                .when()
                .get("/booking/{bookingId}");
    }

    public static Response deleteBooking(int bookingId, String token) {
        return given()
                .header("Cookie", "token=" + token)
                .pathParam("bookingId", bookingId)
                .when()
                .delete("/booking/{bookingId}");
    }

    public static Response getBookingByFilter(String firstname, String lastname) {
        return given()
                .queryParam("firstname", firstname)
                .queryParam("lastname", lastname)
                .when()
                .get("/booking");
    }

    public static Response updateBooking(int bookingId, BookingModel booking, String token) {
        return given()
                .header("Cookie", "token=" + token)
                .pathParam("bookingId", bookingId)
                .body(booking)
                .when()
                .put("/booking/{bookingId}");
    }
    public static Response patchBooking(int bookingId, BookingModel booking, String token) {
        return given()
                .header("Cookie", "token=" + token)
                .pathParam("bookingId", bookingId)
                .body(booking)
                .when()
                .patch("/booking/{bookingId}");
    }
    public static Response getAllBookings() {
        return given()
                .when()
                .get("/booking");
    }

    public static Response getBookingByCheckinCheckout(String checkin, String checkout) {
        return given()
                .queryParam("checkin", checkin)
                .queryParam("checkout", checkout)
                .when()
                .get("/booking");
    }
}