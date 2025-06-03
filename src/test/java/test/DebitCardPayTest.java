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
import static data.DataHelper.generateCvcCode;



public class DebitCardPayTest {

   @BeforeEach
   public void setup() {
      Selenide.open("http://localhost:8080");
   }

   @Test
   public void shouldPurchaseWithApprovedCard() {
      $(byText("Купить")).click();
      $(byText("Оплата по карте")).shouldBe(Condition.visible);
      $("[placeholder='0000 0000 0000 0000']").setValue(DataHelper.getFirstCardInfo());
      $("[placeholder='08']").setValue(DataHelper.getGenerateMonth(01));
      $("[placeholder='22']").setValue(DataHelper.getgenerateYear(26));
      $$("[class='input__control']").get(3).setValue(DataHelper.generateOwner("EN"));
      $("[placeholder='999']").setValue(DataHelper.generateCvcCode(123));
      $(Selectors.byText("Продолжить")).click();
      $(Selectors.withText("Операция одобрена Банком.")).shouldBe(Condition.visible, Duration.ofSeconds(15));
   }

   @Test
   public void shouldPurchaseWithDeclinedCard() {
       $(byText("Купить")).click();
       $(byText("Оплата по карте")).shouldBe(Condition.visible);
       $("[placeholder='0000 0000 0000 0000']").setValue(DataHelper.getSecondCardInfo());
       $("[placeholder='08']").setValue("01");
       $("[placeholder='22']").setValue("26");
       $$("[class='input__control']").get(3).setValue("IVANOV");
       $("[placeholder='999']").setValue("123");
       $(Selectors.byText("Продолжить")).click();
       $(Selectors.withText("Ошибка! Банк отказал в проведении операции.")).shouldBe(Condition.visible, Duration.ofSeconds(15));
   }

}
