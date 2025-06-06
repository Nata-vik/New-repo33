package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class HomePage {
    private SelenideElement debitForm  = $(byText("Купить"));
    private SelenideElement creditForm = $(byText("Купить в кредит"));

    public DebitCardPage openDebitForm() {
        debitForm.click();
        return new DebitCardPage();
    }

    public CreditCardPage openCreditForm() {
        creditForm.click();
        return new CreditCardPage();
    }
}
