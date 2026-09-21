package utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import model.BookingModel;
@JsonInclude(JsonInclude.Include.NON_NULL)

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

    public static BookingModel.BookingDatesModel  randomBookingDates() {
        LocalDate checkin = LocalDate.now().plusDays(faker.number().numberBetween(1, 30));
        LocalDate checkout = checkin.plusDays(faker.number().numberBetween(1, 14));
        return new BookingModel.BookingDatesModel(
                checkin.format(FORMATTER), checkout.format(FORMATTER));
    }
    public static BookingModel randomBooking() {
        return new BookingModel(
                getFirstName(),
                getLastName(),
                getTotalPrice(),
                isDepositPaid(),
                randomBookingDates(),
                additionalNeeds());
    }
    public static BookingModel randomFirstNamePatch(String currentFirstName) {
        String newName = getFirstName();
        while (newName.equals(currentFirstName)) {
            newName = getFirstName();
        }
        BookingModel patch = new BookingModel();
        patch.setFirstname(newName);
        return patch;
    }
}
