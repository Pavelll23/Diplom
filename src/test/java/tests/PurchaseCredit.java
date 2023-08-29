package tests;

import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;


public class PurchaseCredit {
    @BeforeEach
    static void setUp(){
        open("http://localhost:8080");
    }
}
