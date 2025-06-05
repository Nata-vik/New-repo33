package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private DataHelper() {
    }

    private static final Faker faker = new Faker();

    public static String getFirstCardInfo() {
        return new String ("4444444444444441");
    }

    public static String getFirstCardStatus() {
        return new String("APPROVED");
    }

    public static String getSecondCardInfo() {
        return new String("4444444444444442");
    }

    public static String getSecondCardStatus() {
        return new String("DECLINED");
    }

    public static String getGenerateMonth(int shift) {
        var month = LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
        return new String (month);
    }

    public static String getgenerateYear(int shift) {
        var year = LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern("yy"));
        return new String (year);
    }

    public static String generateOwner(String locale) {
        Faker faker = new Faker(new Locale(locale));
        var owner = faker.name().lastName() + " " + faker.name().firstName();
        return new String (owner);
    }

    public static String generateCvcCode(int digits) {
        Faker faker = new Faker();
        var cvc = faker.number().digits(digits);
        return new String (cvc);
    }


//    @Value
//    public static class CardInfo {
//        String cardNumber;
//    }
//
//    @Value
//    public static class MonthInfo {
//        String month;
//    }
//
//    @Value
//    public static class YearInfo {
//        String year;
//    }
//
//    @Value
//    public static class OwnerInfo {
//        String owner;
//    }
//
//    @Value
//    public static class CvcInfo {
//        String cvc;
//    }
}
