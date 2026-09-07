package utils;
import net.datafaker.Faker;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import model.BookingModel;

public class TestData {
    private static final Faker faker = new Faker();
    private static final String PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);


    public static String getFirstName() {
        return faker.name().firstName();
    }

    public static String getLastName() {
        return faker.name().lastName();
    }

    public static int getTotalPrice() {
        return faker.number().numberBetween(100, 1000);
    }

    public static boolean isDepositPaid() {
        return faker.bool().bool();
    }

    public static String bookingCheckinDate() {
        return faker.timeAndDate().future(30, TimeUnit.DAYS, PATTERN);
    }

    public static String bookingCheckoutDate(String checkinDateStr) {
        LocalDate checkin = LocalDate.parse(checkinDateStr, FORMATTER);
        LocalDate checkout = checkin.plusDays(faker.number().numberBetween(1, 14));
        return checkout.format(FORMATTER);
    }

    public static String additionalNeeds() {
    return faker.food().dish();
    }

    public static BookingModel randomBooking() {
        String checkin = bookingCheckinDate();
        String checkout = bookingCheckoutDate(checkin);

        return new BookingModel(
                getFirstName(),
                getLastName(),
                getTotalPrice(),
                isDepositPaid(),
                new BookingModel.BookingDatesModel(checkin, checkout),
                additionalNeeds()
        );
    }
}
