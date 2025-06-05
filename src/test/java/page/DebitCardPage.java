package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;



import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DebitCardPage {
    private final SelenideElement cardSelection = $(byText("Купить"));
    private final SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000]");
    private final SelenideElement monthField = $("[placeholder='08]");
    private final SelenideElement yearField = $("[placeholder='22']");
    private final SelenideElement ownerField = $$("[class='input__control']").get(3);
    private final SelenideElement codeField = $("[placeholder='999']");
    private final SelenideElement buttonForm = $(Selectors.byText("Продолжить"));
    private final SelenideElement successNotification = $(Selectors.withText("Операция одобрена Банком."));
    private final SelenideElement errorNotification = $(Selectors.withText("Ошибка! Банк отказал в проведении операции."));
    private final SelenideElement wrongFormat = $(byText("Неверный формат"));
    private final SelenideElement emptyField = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement wrongCardDate = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement cardExpired = $(byText("Истёк срок действия карты"));

    public void ClickCardSelection() {
        cardSelection.click();
    }

    public void ClickSendButton() {
        buttonForm.click();
    }

}
