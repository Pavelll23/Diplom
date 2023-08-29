package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    private static String getApprovedNumberCard() {
        return ("4444 4444 4444 4441");
    }
    private static String getDeclinedNumberCard() {
        return ("4444 4444 4444 4442");
    }
    public static String getMonth() {
        String month = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return month;
    }
    public static String getPastMonth(int shift) {
        String month = LocalDate.now().minusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
        return month;
    }
    public static String getNexttMonth(int shift) {
        String month = LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
        return month;
    }
    public static String getYear() {
        String year = LocalDate.now().format(DateTimeFormatter.ofPattern("YY"));
        return year;
    }
    public static String getPastYear(int shift) {
        String year = LocalDate.now().minusYears(shift).format(DateTimeFormatter.ofPattern("YY"));
        return year;
    }
    public static String getNextYear(int shift) {
        String year = LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern("YY"));
        return year;
    }
    public static String getName(){
        Faker faker = new Faker();
        String name = faker.name().firstName() + " " + faker.name().lastName();
        return name;
    }

    public static String getCvc(){
        Faker faker = new Faker();
        String code = faker.number().digits(3);
        return code;
    }
    public static CardInfo getApprovedCard() {
        return new CardInfo(getApprovedNumberCard(), getMonth(), getYear(), getName(), getCvc());
    }
    public static CardInfo getDeclinedCard() {
        return new CardInfo(getDeclinedNumberCard(), getMonth(), getYear(), getName(), getCvc());
    }
}