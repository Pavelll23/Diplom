package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SqlHelper;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import page.DashboardPage;
import page.PaymentPageForCredit;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ByForCredit {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
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

    @Test
    void shouldSuccessfulPurchaseByCredit() {         // Покупка тура в кредит со статусом "APPROVED"
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getApprovedCard());
        payment.expectationOperationApproved();
        assertEquals("APPROVED", SqlHelper.getCreditPaymentStatus());
    }

    @Test
    void shouldPurchaseByCardDeclined() {            // Покупка тура в кредит со статусом "DECLINED"
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getDeclinedCard());
        payment.expectationError();
        assertEquals("DECLINED", SqlHelper.getCreditPaymentStatus());
    }

    @Test
    void shouldByPaymentEmptyForm() {              // Пустая форма
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getEmptyForm());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentLastMonth() {           // Прошедший месяц
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getDateLastMonth());
        payment.expectationInvalidDataCard();
        assertEquals("Неверно указан срок действия карты", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentNonExistentMonth() {          // Не существующий месяц
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getDateNonExistentMonth());
        payment.expectationInvalidDataCard();
        assertEquals("Неверно указан срок действия карты", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentOneSymbolInMonth() {          // Одна цифра в месяце
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getDateOneSymbolIntMonth());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentPastYear() {          // Прошлый год
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getDatePastYear());
        payment.expectationCardExpired();
        assertEquals("Истёк срок действия карты", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentNextYear() {          // Год не входящий в диапазон валидных (+6)
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getDateNextYear());
        payment.expectationInvalidDataCard();
        assertEquals("Неверно указан срок действия карты", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentOneSymbolInYear() {          // Одна цифра при указании года
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getDateOneSymbolIntYear());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterNameCyrillic() {        //Ввод имени на кириллице
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getNameCyrillic());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterSpecialSymbol() {        //Ввод в поле спец.символов
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getNameSpecialSymbol());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterTsyfry() {        //Ввод в поле "Имя" цифры
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getNameTsyfry());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterManySymbol() {        //Ввод в поле "Имя" >64 символов
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getNameManySymbol());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterCard15Tsyfry() {        //Ввод в поле "Номер карты" 15 цифр
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getCard15Tsyfry());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterCardAllZero() {        //Ввод в поле "Номер карты" все 0
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getCardAllZero());
        payment.expectationError();
        // assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterCvcAllZero() {        //Ввод в поле "CVC" все 0
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getCvcAllZero());
        payment.expectationError();
        // assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterCvcOneTsyfry() {        //Ввод в поле "CVC" одну цифру
        var dashboardtPage = new DashboardPage();
        dashboardtPage.openCreditPage();
        var payment = new PaymentPageForCredit();
        payment.completedForm(DataHelper.getCvcOneTsyfry());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

}
