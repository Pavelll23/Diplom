package data;

import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    static class CardNumber {
        private String number;
    }

    public static CardNumber getApprovedNumberCard() {
        return new CardNumber("4444 4444 4444 4441");
    }

    public static CardNumber getDeclinedNumberCard(){
        return new CardNumber("4444 4444 4444 4442");
    }

    public static String generateData(){
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return date;
    }
    public static String generatePastDate(int shift){
        String date =LocalDate.now().minusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
        return date;
    }
}