package tests;

import api.AuthApi;
import api.BookingApi;
import base.BaseTest;
import io.restassured.response.Response;
import model.BookingModel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utils.TestData;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class BookingCreateTest  extends BaseTest {
    private Integer createdId;

    @Test(retryAnalyzer = utils.RateLimitRetryAnalyzer.class)
    public void postBooking() {
        BookingModel booking = TestData.randomBooking();
        Response response = BookingApi.createBooking(booking);
        response.then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("bookingid", notNullValue())
                .body("booking.firstname", equalTo(booking.getFirstname()))
                .body("booking.lastname", equalTo(booking.getLastname()))
                .body("booking.totalprice", equalTo(booking.getTotalprice()))
                .body("booking.bookingdates.checkin", equalTo(booking.getBookingdates().getCheckin()))
                .body("booking.bookingdates.checkout", equalTo(booking.getBookingdates().getCheckout()));

        createdId = response.path("bookingid");

        BookingApi.getBooking(createdId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo(booking.getFirstname()));
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        if (createdId != null) {
            BookingApi.deleteBooking(createdId, AuthApi.generateToken());
            createdId = null;
        }
    }
}
