package test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class DebitCardPayTest {

    @BeforeEach
    public void setup() {
        Selenide.open("http://localhost:8080");
    }

    // Debit Card

    @Test
    public void shouldPurchaseWithApprovedCard() {                      // ввод валидных данных Approved карты
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(Condition.visible);
        $("[placeholder='0000 0000 0000 0000']").setValue(DataHelper.getFirstCardInfo());
        $("[placeholder='08']").setValue(DataHelper.getGenerateMonth(1));
        $("[placeholder='22']").setValue(DataHelper.getgenerateYear(1));
        $$("[class='input__control']").get(3).setValue(DataHelper.generateOwner("EN"));
        $("[placeholder='999']").setValue(DataHelper.generateCvcCode(3));
        $(Selectors.byText("Продолжить")).click();
        $(Selectors.withText("Операция одобрена Банком.")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldPurchaseWithDeclinedCard() {                     // ввод валидных данных Declined карты
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(Condition.visible);
        $("[placeholder='0000 0000 0000 0000']").setValue(DataHelper.getSecondCardInfo());
        $("[placeholder='08']").setValue(DataHelper.getGenerateMonth(1));
        $("[placeholder='22']").setValue(DataHelper.getgenerateYear(1));
        $$("[class='input__control']").get(3).setValue(DataHelper.generateOwner("EN"));
        $("[placeholder='999']").setValue(DataHelper.generateCvcCode(3));
        $(Selectors.byText("Продолжить")).click();
        $(Selectors.withText("Ошибка! Банк отказал в проведении операции.")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldReturnErrorWithEmptyDebitCard() {               // отправка пустой формы
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(Condition.visible);
        $(Selectors.byText("Продолжить")).click();
        $(Selectors.withText("Неверный формат")).shouldBe(Condition.visible);
        $(Selectors.withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldReturnErrorWithEmptyMonthDebitCard() {          // пустое поле "Месяц"
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(Condition.visible);
        $("[placeholder='0000 0000 0000 0000']").setValue(DataHelper.getSecondCardInfo());
        //$("[placeholder='08']").setValue(DataHelper.getGenerateMonth(1));
        $("[placeholder='22']").setValue(DataHelper.getgenerateYear(1));
        $$("[class='input__control']").get(3).setValue(DataHelper.generateOwner("EN"));
        $("[placeholder='999']").setValue(DataHelper.generateCvcCode(3));
        $(Selectors.byText("Продолжить")).click();
        $(Selectors.withText("Неверный формат")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldReturnErrorWithEmptyYearDebitCard() {                // пустое поле "Год"
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(Condition.visible);
        $("[placeholder='0000 0000 0000 0000']").setValue(DataHelper.getFirstCardInfo());
        $("[placeholder='08']").setValue(DataHelper.getGenerateMonth(1));
        //$("[placeholder='22']").setValue(DataHelper.getgenerateYear(1));
        $$("[class='input__control']").get(3).setValue(DataHelper.generateOwner("EN"));
        $("[placeholder='999']").setValue(DataHelper.generateCvcCode(3));
        $(Selectors.byText("Продолжить")).click();
        $(Selectors.withText("Неверный формат")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldReturnErrorWithEmptyOwnerDebitCard() {                // пустое поле "Владелец"
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(Condition.visible);
        $("[placeholder='0000 0000 0000 0000']").setValue(DataHelper.getFirstCardInfo());
        $("[placeholder='08']").setValue(DataHelper.getGenerateMonth(1));
        $("[placeholder='22']").setValue(DataHelper.getgenerateYear(1));
        //$$("[class='input__control']").get(3).setValue(DataHelper.generateOwner("EN"));
        $("[placeholder='999']").setValue(DataHelper.generateCvcCode(3));
        $(Selectors.byText("Продолжить")).click();
        $(Selectors.withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldReturnErrorWithEmptyCvcDebitCard() {                // пустое поле "CVC/CVV"
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(Condition.visible);
        $("[placeholder='0000 0000 0000 0000']").setValue(DataHelper.getFirstCardInfo());
        $("[placeholder='08']").setValue(DataHelper.getGenerateMonth(1));
        $("[placeholder='22']").setValue(DataHelper.getgenerateYear(1));
        $$("[class='input__control']").get(3).setValue(DataHelper.generateOwner("EN"));
        //$("[placeholder='999']").setValue(DataHelper.generateCvcCode(3));
        $(Selectors.byText("Продолжить")).click();
        $(Selectors.withText("Неверный формат")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldReturnErrorWithRusOwnerDebitCard() {                      // ввод символов кириллицы в поле "Владелец"
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(Condition.visible);
        $("[placeholder='0000 0000 0000 0000']").setValue(DataHelper.getFirstCardInfo());
        $("[placeholder='08']").setValue(DataHelper.getGenerateMonth(1));
        $("[placeholder='22']").setValue(DataHelper.getgenerateYear(1));
        $$("[class='input__control']").get(3).setValue(DataHelper.generateOwner("RU"));
        $("[placeholder='999']").setValue(DataHelper.generateCvcCode(3));
        $(Selectors.byText("Продолжить")).click();
        $(Selectors.withText("Неверный формат")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldReturnErrorWithInvalidNumberDebitCard() {                      // ввод символов кириллицы в поле "Владелец"
        $(byText("Купить")).click();
        $(byText("Оплата по карте")).shouldBe(Condition.visible);
        $("[placeholder='0000 0000 0000 0000']").setValue("номер");
        $(Selectors.withText("Неверный формат")).shouldBe(Condition.visible);

    }

}
