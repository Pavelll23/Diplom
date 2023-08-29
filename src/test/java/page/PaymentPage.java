package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.CardInfo;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class PaymentPage {
    private SelenideElement heading = $$("h3").find(exactText("Оплата по карте"));
    private SelenideElement payButton = $x("//span[text()='Купить']//ancestor::button");
    private SelenideElement creditButton = $x("//span[text()='Купить в кредит']//ancestor::button");
    private SelenideElement cardNumberField = $(byText("Номер карты")).parent().$("[class=\"input__control\"]");
    private SelenideElement monthField = $(byText("Месяц")).parent().$("[class=\"input__control\"]");
    private SelenideElement yearField = $(byText("Год")).parent().$("[class=\"input__control\"]");
    private SelenideElement cardNameField = $(byText("Владелец")).parent().$("[class=\"input__control\"]");
    private SelenideElement cvcField = $(byText("CVC/CVV")).parent().$("[class=\"input__control\"]");

    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));

    public PaymentPage(){
        heading.shouldBe(visible);
    }
    public PaymentPage pushPay() {
        payButton.click();
        return new PaymentPage();
    }

    public PaymentPage pushCredit() {
        creditButton.click();
        return new PaymentPage();
    }
    public void completedForm(CardInfo card){
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        cardNameField.setValue(card.getName());
        cvcField.setValue(card.getCvc());
        continueButton.click();


    }
}
