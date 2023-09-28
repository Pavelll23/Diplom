package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SqlHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.DashboardPage;
import page.PaymentPage;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ByForCard {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        String url = System.getProperty("sut.url");
        open(url);
    }

    @AfterEach
    public void deleteDB() {
        SqlHelper.deleteDataBase();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldSuccessfulPurchaseByCard() {         // Покупка тура картой со статусом "APPROVED"
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getApprovedCard());
        payment.expectationOperationApproved();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());
    }

    @Test
    void shouldPurchaseByCardDeclined() {            // Покупка тура картой со статусом "DECLINED"
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getDeclinedCard());
        payment.expectationError();
        assertEquals("DECLINED", SqlHelper.getPaymentStatus());
    }

    @Test
    void shouldByPaymentEmptyForm() {              // Пустая форма
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getEmptyForm());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentLastMonth() {           // Прошедший месяц
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getDateLastMonth());
        payment.expectationInvalidDataCard();
        assertEquals("Неверно указан срок действия карты", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentNonExistentMonth() {          // Не существующий месяц
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getDateNonExistentMonth());
        payment.expectationInvalidDataCard();
        assertEquals("Неверно указан срок действия карты", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentOneSymbolInMonth() {          // Одна цифра в месяце
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getDateOneSymbolIntMonth());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentPastYear() {          // Прошлый год
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getDatePastYear());
        payment.expectationCardExpired();
        assertEquals("Истёк срок действия карты", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentNextYear() {          // Год не входящий в диапазон валидных (+6)
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getDateNextYear());
        payment.expectationInvalidDataCard();
        assertEquals("Неверно указан срок действия карты", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentOneSymbolInYear() {          // Одна цифра при указании года
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getDateOneSymbolIntYear());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterNameCyrillic() {        //Ввод имени на кириллице
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getNameCyrillic());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterSpecialSymbol() {        //Ввод в поле "Имя" спец.символов
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getNameSpecialSymbol());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterTsyfry() {        //Ввод в поле "Имя" цифры
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getNameTsyfry());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterManySymbol() {        //Ввод в поле "Имя" >64 символов
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getNameManySymbol());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterCard15Tsyfry() {        //Ввод в поле "Номер карты" 15 цифр
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getCard15Tsyfry());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterCardAllZero() {        //Ввод в поле "Номер карты" все 0
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getCardAllZero());
        payment.expectationError();
    }

    @Test
    void shouldByEnterCvcAllZero() {        //Ввод в поле "CVC" все 0
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getCvcAllZero());
        payment.expectationError();
    }

    @Test
    void shouldByEnterCvcOneTsyfry() {        //Ввод в поле "CVC" одну цыфру
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        var payment = new PaymentPage();
        payment.completedForm(DataHelper.getCvcOneTsyfry());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }


}
