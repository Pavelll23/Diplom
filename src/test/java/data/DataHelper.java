package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DataHelper {
    private DataHelper() {
    }

    private static String getApprovedNumberCard() {
        return ("4444 4444 4444 4441");
    }

    private static String getDeclinedNumberCard() {
        return ("4444 4444 4444 4442");
    }

//    public static String getMonth() {
//        String month = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
//        return month;
//    }

//    public static String getPastMonth(int shift) {
//        String month = LocalDate.now().minusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
//        return month;
//    }

    public static String getMonth(int shift) {
        String month = LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
        return month;
    }

//    public static String getYear() {
//        String year = LocalDate.now().format(DateTimeFormatter.ofPattern("YY"));
//        return year;
//    }
//
//    public static String getPastYear(int shift) {
//        String year = LocalDate.now().minusYears(shift).format(DateTimeFormatter.ofPattern("YY"));
//        return year;
//    }

    public static String getYear(int shift) {
        String year = LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern("YY"));
        return year;
    }

    public static String getName() {
        Faker faker = new Faker();
        String name = faker.name().firstName() + " " + faker.name().lastName();
        return name;
    }

    public static String getCvc() {
        Faker faker = new Faker();
        String code = faker.number().digits(3);
        return code;
    }

    public static CardInfo getApprovedCard() {
        return new CardInfo(getApprovedNumberCard(), getMonth(0), getYear(0), getName(), getCvc());
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo(getDeclinedNumberCard(), getMonth(0), getYear(0), getName(), getCvc());
    }

    public static CardInfo getEmptyForm() {
        return new CardInfo("", "", "", "", "");
    }

    public static CardInfo getDateLastMonth() {
        return new CardInfo(getApprovedNumberCard(), getMonth(-1), getYear(0), getName(), getCvc());
    }

    public static CardInfo getDateNonExistentMonth() {
        return new CardInfo(getApprovedNumberCard(), "13", getYear(0), getName(), getCvc());
    }

    public static CardInfo getDateOneSymbolIntMonth() {
        return new CardInfo(getApprovedNumberCard(), "1", getYear(0), getName(), getCvc());
    }

    public static CardInfo getDatePastYear() {
        return new CardInfo(getApprovedNumberCard(), getMonth(0), getYear(-1), getName(), getCvc());
    }

    public static CardInfo getDateNextYear() {
        return new CardInfo(getApprovedNumberCard(), getMonth(0), getYear(6), getName(), getCvc());
    }

    public static CardInfo getDateOneSymbolIntYear() {
        return new CardInfo(getApprovedNumberCard(), getMonth(0), "1", getName(), getCvc());
    }

    public static CardInfo getNameCyrillic() {
        return new CardInfo(getApprovedNumberCard(), getMonth(0), getYear(0), "Сергей", getCvc());
    }

    public static CardInfo getNameSpecialSymbol() {
        return new CardInfo(getApprovedNumberCard(), getMonth(0), getYear(0), "!@#$%^", getCvc());
    }

    public static CardInfo getNameTsyfry() {
        return new CardInfo(getApprovedNumberCard(), getMonth(0), getYear(0), "12345", getCvc());
    }

    public static CardInfo getNameManySymbol() {
        return new CardInfo(getApprovedNumberCard(), getMonth(0), getYear(0), "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", getCvc());
    }

    public static CardInfo getCard15Tsyfry() {
        return new CardInfo(("444444444444444"), getMonth(0), getYear(0), getName(), getCvc());
    }

    public static CardInfo getCardAllZero() {
        return new CardInfo(("0000000000000000"), getMonth(0), getYear(0), getName(), getCvc());
    }

    public static CardInfo getCvcAllZero() {
        return new CardInfo(getApprovedNumberCard(), getMonth(0), getYear(0), getName(), ("000"));
    }

    public static CardInfo getCvcOneTsyfry() {
        return new CardInfo(getApprovedNumberCard(), getMonth(0), getYear(0), getName(), ("1"));
    }
}