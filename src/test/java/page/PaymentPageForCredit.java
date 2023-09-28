package page;

import com.codeborne.selenide.SelenideElement;
import data.CardInfo;
import java.time.Duration;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPageForCredit {
    private SelenideElement heading = $$("h3").find(exactText("Кредит по данным карты"));
    private SelenideElement cardNumberField = $(byText("Номер карты")).parent().$("[class=\"input__control\"]");
    private SelenideElement monthField = $(byText("Месяц")).parent().$("[class=\"input__control\"]");
    private SelenideElement yearField = $(byText("Год")).parent().$("[class=\"input__control\"]");
    private SelenideElement cardNameField = $(byText("Владелец")).parent().$("[class=\"input__control\"]");
    private SelenideElement cvcField = $(byText("CVC/CVV")).parent().$("[class=\"input__control\"]");
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private SelenideElement operationApproved = $(byText("Операция одобрена Банком.")).parent().$("[class=\"notification__content\"]");
    private SelenideElement error = $(byText("Ошибка! Банк отказал в проведении операции.")).parent().$("[class=\"notification__content\"]");
    private SelenideElement invalidFormat = $(byText("Неверный формат"));
   private SelenideElement invalidDataCard = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpired = $(byText("Истёк срок действия карты"));
    private SelenideElement invalidMassege = $(".input__sub");

    public PaymentPageForCredit() {
        heading.shouldBe(visible);
    }

    public void completedForm(CardInfo card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        cardNameField.setValue(card.getName());
        cvcField.setValue(card.getCvc());
        continueButton.click();
    }

    public void expectationOperationApproved() {
        operationApproved.shouldBe(visible, Duration.ofMillis(15000));
    }

    public void expectationError() {
        error.shouldBe(visible, Duration.ofMillis(15000));
    }

    public void expectationInvalidFormat() {
        invalidFormat.shouldBe(visible);
    }

    public void expectationInvalidDataCard() {
        invalidDataCard.shouldBe(visible);
    }

    public void expectationCardExpired() {
        cardExpired.shouldBe(visible);
    }

    public String getInvalidText() {
        return invalidMassege.getText();
    }
}
