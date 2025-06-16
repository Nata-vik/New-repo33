package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.DebitCardPage;
import page.HomePage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class DebitCardPayTest {

    private HomePage homePage;
    private DebitCardPage debitCardPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @BeforeEach
    public void setup() {
        homePage = open("http://localhost:8080/", HomePage.class);
    }

    @AfterEach
    void clean() {
        SQLHelper.clear();
    }

    @Test
    public void shouldPurchaseWithApprovedCard() {                         // 1. ввод валидных данных Approved карты
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitSuccessfulNotification();
        var expected = DataHelper.getFirstCardStatus();
        var actual = SQLHelper.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldPurchaseWithDeclinedCard() {                           // 2. ввод валидных данных Declined карты
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getSecondCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitErrorNotification();
        var expected = DataHelper.getSecondCardStatus();
        var actual = SQLHelper.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnErrorWithEmptyDebitCard() {                      // 3. отправка пустой формы
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getEmptyCardInfo();
        var month = DataHelper.getEmptyMonth();
        var year = DataHelper.getEmptyYear();
        var owner = DataHelper.getEmptyOwner();
        var cvc = DataHelper.getEmptyCvcCode();
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitEmptyField();
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithEmptyNumberDebitCard() {                // 4. пустое поле "Номер карты"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getEmptyCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithEmptyMonthDebitCard() {                 // 5. пустое поле "Месяц"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getEmptyMonth();
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithEmptyYearDebitCard() {                  // 6. пустое поле "Год"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getEmptyYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithEmptyOwnerDebitCard() {                 // 7. пустое поле "Владелец"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getEmptyOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitEmptyField();
    }

    @Test
    public void shouldReturnErrorWithEmptyCvcDebitCard() {                   // 8. пустое поле "CVC/CVV"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getEmptyCvcCode();
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithCyrillicSymbolNumberDebitCard() {       // 9. ввод символов кириллицы в поле "Номер карты"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getGenerateInvalidCardInfo("RU");
        debitCardPage.onlyCardField(cardNumber);
        debitCardPage.emptyCardField();
    }

    @Test
    public void shouldReturnErrorWithIncorrectSymbolNumberDebitCard() {         // 10. ввод спец.символов в поле "Номер карты"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getSpecialSimbolsCardInfo();
        debitCardPage.onlyCardField(cardNumber);
        debitCardPage.emptyCardField();
    }

    @Test
    public void shouldReturnErrorWithLatinSymbolMonthDebitCard() {              // 11. ввод символов латиницы в поле "Месяц"
        debitCardPage = homePage.openDebitForm();
        var month = DataHelper.getGenerateInvalidMonthInfo("EN");
        debitCardPage.onlyMonthField(month);
        debitCardPage.emptyMonthField();
    }

    @Test
    public void shouldReturnErrorWithIncorrectSymbolMonthDebitCard() {            // 12. ввод спец.символов в поле "Месяц"
        debitCardPage = homePage.openDebitForm();
        var month = DataHelper.getSpecialsSymbolMonth();
        debitCardPage.onlyMonthField(month);
        debitCardPage.emptyMonthField();
    }

    @Test
    public void shouldReturnErrorWithLatinSymbolYearDebitCard() {                // 13. ввод символов латиницы в поле "Год"
        debitCardPage = homePage.openDebitForm();
        var year = DataHelper.getGenerateInvalidYearInfo("EN");
        debitCardPage.onlyYearField(year);
        debitCardPage.emptyYearField();
    }

    @Test
    public void shouldReturnErrorWithIncorrectSymbolYearDebitCard() {            // 14. ввод спец.символов в поле "Год"
        debitCardPage = homePage.openDebitForm();
        var year = DataHelper.getSpecialsSymbolsYearInfo();
        debitCardPage.onlyYearField(year);
        debitCardPage.emptyYearField();
    }

    @Test
    public void shouldReturnErrorWithCyrillicSymbolOwnerDebitCard() {             // 15. ввод символов кириллицы в поле "Владелец"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("RU");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithIncorrectSymbolOwnerDebitCard() {            // 16. ввод спецсимволов в поле "Владелец"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getSpecialsSymbolsOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithDigitOwnerDebitCard() {                       // 17. ввод цифрового значения в поле "Владелец"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateNumberOwner(10);
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorHieroglyphSymbolOwnerDebitCard() {                // 18. ввод иероглифов в поле "Владелец"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("ja");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithArabicSymbolOwnerDebitCard() {                // 19. ввод арабских символов в поле "Владелец"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("ar");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithLatinSymbolCvcDebitCard() {                   // 20. ввод символов на латинице в поле "CVC/CVV"
        debitCardPage = homePage.openDebitForm();
        var cvc = DataHelper.getGenerateInvalidCvcCode("EN");
        debitCardPage.onlyCVCField(cvc);
        debitCardPage.emptyCVCField();
    }

    @Test
    public void shouldReturnErrorWithIncorrectSymbolCvcDebitCard() {                // 21. ввод спецсимволов в поле "CVC/CVV"
        debitCardPage = homePage.openDebitForm();
        var cvc = DataHelper.getSpecialSymbolsCvcCode();
        debitCardPage.onlyCVCField(cvc);
        debitCardPage.emptyCVCField();
    }

    @Test
    public void shouldReturnValidValueWith17SymbolNumberDebitCard() {               // 22. ввод 17 цифр в поле "Номер карты"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getCardAfterLimitCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldReturnValidValueWith3SymbolMonthDebitCard() {                 // 23. ввод 3 цифр в поле "Месяц"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getCardAfterLimitMonth();
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldReturnValidValueWith3SymbolYearDebitCard() {                    // 24. ввод 3 цифр в поле "Год"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getCardAfterLimitYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldReturnValidValueWith4SymbolCvcCodeDebitCard() {                // 25. ввод 4 цифр в поле "CVC/CVV"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getCvcAfterLimitCvcCode();
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldErrorWithZeroCardNumberDebitCard() {                          // 26. ввод нулей в поле "Номер карты"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getCardWithZeroCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitErrorNotification();
    }

    @Test
    public void shouldErrorWithZeroMonthDebitCard() {                               // 27. ввод нулей в поле "Месяц"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getCardWithZeroMonth();
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldErrorWithZeroYearDebitCard() {                               // 28. ввод нулей в поле "Год"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getCardWithZeroYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitCardExpired();
    }

    @Test
    public void shouldErrorWithZeroCvcCodeDebitCard() {                            // 29. ввод нулей в поле "CVC/CVV"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getCardWithZeroCvcCode();
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldPurchaseWith999CvcCodeDebitCard() {                                     // 30. ввод в поле "CVC/CVV" значения "999"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getEnterCvcCode("999");
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldErrorWithUnderLimitNumberDebitCard() {                       // 31. ввод 15 цифр в поле "Номер карты"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getCardUnderLimitCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldErrorWith13MonthDebitCard() {                                // 32. ввод числа "13" в поле "Месяц"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getEnterMonth("13");
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongCardDate();
    }

    @Test
    public void shouldErrorWithUnderLimitMonthDebitCard() {                       // 33. ввод 1 цифры в поле "Месяц"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getEnterMonth("1");
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldErrorWithUnderLimitYearDebitCard() {                       // 34. ввод 1 цифры в поле "Год"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getCardUnderLimitYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldErrorWithUnderLimitCvcDebitCard() {                       // 35. ввод 1 цифры в поле "CVC/CVV"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(1);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongFormat();
    }

    @Test
    public void shouldErrorWithInvalidDateMonthDebitCard() {                   // 36. ввод предыдущего месяца в поле "Месяц"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateInvalidMonthDate(2);
        var year = DataHelper.getGenerateYear(0);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitWrongCardDate();
    }

    @Test
    public void shouldErrorWithInvalidDateYearDebitCard() {                    // 37. ввод предыдущего года в поле "Год"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateInvalidYearDate(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitCardExpired();
    }

    @Test
    public void shouldAddPaymentIDInOrderEntry() {                             // 38. статус APPROVED, должно быть поле PaymentId при оплате одобренной дебетовой картой
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        var expected = SQLHelper.getDebitPaymentID();
        var actual = SQLHelper.getDebitOrderEntryId();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldDontAddPaymentIDInOrderEntryStatusDeclined() {             // 39. статус DECLINED, должно отсутствовать поле PaymentId при оплате отклоненной дебетовой картой
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getSecondCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        var expected = SQLHelper.getDebitPaymentID();
        var actual = SQLHelper.getDebitOrderEntryId();
        assertNotEquals(expected, actual);
    }
}