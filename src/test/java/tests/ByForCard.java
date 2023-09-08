package tests;

import data.DataHelper;
import data.SqlHelper;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByForCard {
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
    void shouldSuccessfulPurchaseByCard() {         // Покупка тура картой со статусом "APPROVED"
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getApprovedCard());
        payment.expectationOperationApproved();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());
    }

    @Test
    void shouldPurchaseByCardDeclined() {            // Покупка тура картой со статусом "DECLINED"
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getDeclinedCard());
        payment.expectationError();
        assertEquals("DECLINED", SqlHelper.getPaymentStatus());
    }

    @Test
    void shouldByPaymentEmptyForm() {              // Пустая форма
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getEmptyForm());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentLastMonth() {           // Прошедший месяц
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getDateLastMonth());
        payment.expectationCardExpired();
        assertEquals("Неверно указан срок действия карты", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentNonExistentMonth() {          // Не существующий месяц
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getDateNonExistentMonth());
        payment.expectationInvalidDataCard();
        assertEquals("Неверно указан срок действия карты", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentOneSymbolInMonth() {          // Одна цифра в месяце
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getDateOneSymbolIntMonth());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentPastYear() {          // Прошлый год
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getDatePastYear());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentNextYear() {          // Год не входящий в диапазон валидных (+6)
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getDateNextYear());
        payment.expectationInvalidDataCard();
        assertEquals("Неверно указан срок действия карты", payment.getInvalidText());
    }

    @Test
    void shouldByPaymentOneSymbolInYear() {          // Одна цифра при указании года
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getDateOneSymbolIntYear());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterNameCyrillic() {        //Ввод имени на кирилице
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getNameCyrillic());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterSpecialSymbol() {        //Ввод в поле спец.символов
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getNameSpecialSymbol());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterTsyfry() {        //Ввод в поле "Имя" цыфры
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getNameTsyfry());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterManySymbol() {        //Ввод в поле "Имя" >64 символов
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getNameManySymbol());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterCard15Tsyfry() {        //Ввод в поле "Номер карты" 15 цыфр
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getCard15Tsyfry());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterCardAllZero() {        //Ввод в поле "Номер карты" все 0
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getCardAllZero());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterCvcAllZero() {        //Ввод в поле "CVC" все 0
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getCvcAllZero());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }

    @Test
    void shouldByEnterCvcOneTsyfry() {        //Ввод в поле "CVC" одну цыфру
        val dashboardtPage = new DashboardPage();
        dashboardtPage.openBuyPage();
        val payment = new PaymentPage();
        payment.completedForm(DataHelper.getCvcOneTsyfry());
        payment.expectationInvalidFormat();
        assertEquals("Неверный формат", payment.getInvalidText());
    }


}
