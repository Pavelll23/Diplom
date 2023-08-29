package tests;

import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

public class PurchaseCard {
    @BeforeEach
    static void setUp() {
        open("http://localhost:8080");
    }


}
