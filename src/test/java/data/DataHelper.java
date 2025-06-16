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

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("4444444444444441");
    }


    public static String getFirstCardStatus() {
        return new String("APPROVED");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("4444444444444442");
    }

    public static String getSecondCardStatus() {
        return new String("DECLINED");
    }

    public static CardInfo getEmptyCardInfo() {
        return new CardInfo("");
    }

    public static CardInfo getGenerateInvalidCardInfo(String locale) {
        Faker faker = new Faker(new Locale(locale));
        var card = faker.name().firstName();
        return new CardInfo(card);
    }

    public static CardInfo getCardAfterLimitCardInfo() {
        return new CardInfo("44444444444444411");
    }

    public static CardInfo getCardUnderLimitCardInfo() {
        return new CardInfo("444444444444444");
    }

    public static CardInfo getCardWithZeroCardInfo() {
        return new CardInfo("0000000000000000");
    }

    public static CardInfo getSpecialSimbolsCardInfo() {
        return new CardInfo("!@%^*($#^&@#(&%$");
    }

    public static MonthInfo getGenerateMonth(int shift) {
        var month = LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
        return new MonthInfo(month);
    }

    public static MonthInfo getGenerateInvalidMonthInfo(String locale) {
        Faker faker = new Faker(new Locale(locale));
        var month = faker.name().firstName();
        return new MonthInfo(month);
    }

    public static MonthInfo getGenerateInvalidMonthDate(int shift) {
        var month = LocalDate.now().minusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
        return new MonthInfo(month);
    }

    public static MonthInfo getEmptyMonth() {
        return new MonthInfo("");
    }

    public static MonthInfo getEnterMonth(String enter) {
        return new MonthInfo(enter);
    }

    public static MonthInfo getCardAfterLimitMonth() {
        return new MonthInfo("123");
    }

    public static MonthInfo getSpecialsSymbolMonth() {
        return new MonthInfo("&#");
    }

    public static MonthInfo getCardWithZeroMonth() {
        return new MonthInfo("00");
    }

    public static YearInfo getGenerateYear(int shift) {
        var year = LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern("yy"));
        return new YearInfo(year);
    }

    public static YearInfo getEmptyYear() {
        return new YearInfo("");
    }

    public static YearInfo getGenerateInvalidYearInfo(String locale) {
        Faker faker = new Faker(new Locale(locale));
        var year = faker.name().firstName();
        return new YearInfo(year);
    }

    public static YearInfo getSpecialsSymbolsYearInfo() {
        return new YearInfo("%#");
    }

    public static YearInfo getCardAfterLimitYear() {
        return new YearInfo("278");
    }

    public static YearInfo getCardUnderLimitYear() {
        return new YearInfo("2");
    }

    public static YearInfo getCardWithZeroYear() {
        return new YearInfo("00");
    }

    public static YearInfo getGenerateInvalidYearDate(int shift) {
        var year = LocalDate.now().minusYears(shift).format(DateTimeFormatter.ofPattern("yy"));
        return new YearInfo(year);
    }

    public static OwnerInfo getGenerateOwner(String locale) {
        Faker faker = new Faker(new Locale(locale));
        var fakerOwner = faker.name().lastName() + " " + faker.name().firstName();
        return new OwnerInfo(fakerOwner);
    }

    public static OwnerInfo getEmptyOwner() {
        return new OwnerInfo("");
    }

    public static OwnerInfo getGenerateNumberOwner(int digits) {
        Faker faker = new Faker();
        var owner = faker.number().digits(digits);
        return new OwnerInfo(owner);
    }

    public static OwnerInfo getSpecialsSymbolsOwner() {
        return new OwnerInfo("#$%^*#$^&$");
    }

    public static CvcInfo getGenerateCvcCode(int digits) {
        Faker faker = new Faker();
        var cvc = faker.number().digits(digits);
        return new CvcInfo(cvc);
    }

    public static CvcInfo getEmptyCvcCode() {
        return new CvcInfo("");
    }

    public static CvcInfo getGenerateInvalidCvcCode(String locale) {
        Faker faker = new Faker(new Locale(locale));
        var cvc = faker.name().firstName();
        return new CvcInfo(cvc);
    }

    public static CvcInfo getSpecialSymbolsCvcCode() {
        return new CvcInfo("&^%");
    }

    public static CvcInfo getCvcAfterLimitCvcCode() {
        return new CvcInfo("1234");
    }

    public static CvcInfo getCardWithZeroCvcCode() {
        return new CvcInfo("000");
    }

    public static CvcInfo getEnterCvcCode(String enter) {
        return new CvcInfo(enter);
    }

    @Value
    public static class CardInfo {
        String cardNumber;
    }

    @Value
    public static class MonthInfo {
        String month;
    }

    @Value
    public static class YearInfo {
        String year;
    }

    @Value
    public static class OwnerInfo {
        String owner;
    }

    @Value
    public static class CvcInfo {
        String cvc;
    }
}
