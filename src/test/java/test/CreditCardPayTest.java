package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.CreditCardPage;
import page.HomePage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class CreditCardPayTest {

    private HomePage homePage;
    private CreditCardPage creditCardPage;

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
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitSuccessfulNotification();
        var expected = DataHelper.getFirstCardStatus();
        var actual = SQLHelper.getCreditPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldPurchaseWithDeclinedCard() {                           // 2. ввод валидных данных Declined карты
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getSecondCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitErrorNotification();
        var expected = DataHelper.getSecondCardStatus();
        var actual = SQLHelper.getCreditPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnErrorWithEmptyCreditCard() {                      // 3. отправка пустой формы
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getEmptyCardInfo();
        var month = DataHelper.getEmptyMonth();
        var year = DataHelper.getEmptyYear();
        var owner = DataHelper.getEmptyOwner();
        var cvc = DataHelper.getEmptyCvcCode();
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitEmptyField();
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithEmptyNumberCreditCard() {                 // 4. пустое поле "Номер карты"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getEmptyCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithEmptyMonthCreditCard() {                  // 5. пустое поле "Месяц"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getEmptyMonth();
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithEmptyYearCreditCard() {                    // 6. пустое поле "Год"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getEmptyYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithEmptyOwnerCreditCard() {                   // 7. пустое поле "Владелец"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getEmptyOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitEmptyField();
    }

    @Test
    public void shouldReturnErrorWithEmptyCvcCreditCard() {                     // 8. пустое поле "CVC/CVV"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getEmptyCvcCode();
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithCyrillicSymbolNumberCreditCard() {           // 9. ввод символов кириллицы в поле "Номер карты"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getGenerateInvalidCardInfo("RU");
        creditCardPage.onlyCardField(cardNumber);
        creditCardPage.emptyCardField();
    }

    @Test
    public void shouldReturnErrorWithIncorrectSymbolNumberCreditCard() {         // 10. ввод спец.символов в поле "Номер карты"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getSpecialSimbolsCardInfo();
        creditCardPage.onlyCardField(cardNumber);
        creditCardPage.emptyCardField();
    }

    @Test
    public void shouldReturnErrorWithLatinSymbolMonthCreditCard() {               // 11. ввод символов латиницы в поле "Месяц"
        creditCardPage = homePage.openCreditForm();
        var month = DataHelper.getGenerateInvalidMonthInfo("EN");
        creditCardPage.onlyMonthField(month);
        creditCardPage.emptyMonthField();
    }

    @Test
    public void shouldReturnErrorWithIncorrectSymbolMonthCreditCard() {           // 12. ввод спец.символов в поле "Месяц"
        creditCardPage = homePage.openCreditForm();
        var month = DataHelper.getSpecialsSymbolMonth();
        creditCardPage.onlyMonthField(month);
        creditCardPage.emptyMonthField();
    }

    @Test
    public void shouldReturnErrorWithLatinSymbolYearCreditCard() {                // 13. ввод символов латиницы в поле "Год"
        creditCardPage = homePage.openCreditForm();
        var year = DataHelper.getGenerateInvalidYearInfo("EN");
        creditCardPage.onlyYearField(year);
        creditCardPage.emptyYearField();
    }

    @Test
    public void shouldReturnErrorWithIncorrectSymbolYearCreditCard() {            // 14. ввод спец.символов в поле "Год"
        creditCardPage = homePage.openCreditForm();
        var year = DataHelper.getSpecialsSymbolsYearInfo();
        creditCardPage.onlyYearField(year);
        creditCardPage.emptyYearField();
    }

    @Test
    public void shouldReturnErrorWithCyrillicSymbolOwnerCreditCard() {             // 15. ввод символов кириллицы в поле "Владелец"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("RU");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithIncorrectSymbolOwnerCreditCard() {            // 16. ввод спецсимволов в поле "Владелец"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getSpecialsSymbolsOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithDigitOwnerCreditCard() {                       // 17. ввод цифрового значения в поле "Владелец"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateNumberOwner(10);
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorHieroglyphSymbolOwnerCreditCard() {                // 18. ввод иероглифов в поле "Владелец"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("ja");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithArabicSymbolOwnerCreditCard() {                // 19. ввод арабских символов в поле "Владелец"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("ar");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithLatinSymbolCvcCreditCard() {                   // 20. ввод символов на латинице в поле "CVC/CVV"
        creditCardPage = homePage.openCreditForm();
        var cvc = DataHelper.getGenerateInvalidCvcCode("EN");
        creditCardPage.onlyCVCField(cvc);
        creditCardPage.emptyCVCField();
    }

    @Test
    public void shouldReturnErrorWithIncorrectSymbolCvcCreditCard() {                // 21. ввод спецсимволов в поле "CVC/CVV"
        creditCardPage = homePage.openCreditForm();
        var cvc = DataHelper.getSpecialSymbolsCvcCode();
        creditCardPage.onlyCVCField(cvc);
        creditCardPage.emptyCVCField();
    }

    @Test
    public void shouldReturnValidValueWith17SymbolNumberCreditCard() {               // 22. ввод 17 цифр в поле "Номер карты"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getCardAfterLimitCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldReturnValidValueWith3SymbolMonthCreditCard() {                 // 23. ввод 3 цифр в поле "Месяц"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getCardAfterLimitMonth();
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldReturnValidValueWith3SymbolYearCreditCard() {                    // 24. ввод 3 цифр в поле "Год"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getCardAfterLimitYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldReturnValidValueWith4SymbolCvcCodeCreditCard() {                // 25. ввод 4 цифр в поле "CVC/CVV"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getCvcAfterLimitCvcCode();
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldErrorWithZeroCardNumberCreditCard() {                          // 26. ввод нулей в поле "Номер карты"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getCardWithZeroCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitErrorNotification();
    }

    @Test
    public void shouldErrorWithZeroMonthCreditCard() {                               // 27. ввод нулей в поле "Месяц"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getCardWithZeroMonth();
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldErrorWithZeroYearCreditCard() {                               // 28. ввод нулей в поле "Год"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getCardWithZeroYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitCardExpired();
    }

    @Test
    public void shouldErrorWithZeroCvcCodeCreditCard() {                            // 29. ввод нулей в поле "CVC/CVV"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getCardWithZeroCvcCode();
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldPurchaseWith999CvcCodeCreditCard() {                         // 30. ввод в поле "CVC/CVV" значения "999"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getEnterCvcCode("999");
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldErrorWithUnderLimitNumberCreditCard() {                       // 31. ввод 15 цифр в поле "Номер карты"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getCardUnderLimitCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldErrorWith13MonthCreditCard() {                                // 32. ввод числа "13" в поле "Месяц"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getEnterMonth("13");
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongCardDate();
    }

    @Test
    public void shouldErrorWithUnderLimitMonthCreditCard() {                       // 33. ввод 1 цифры в поле "Месяц"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getEnterMonth("1");
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldErrorWithUnderLimitYearCreditCard() {                       // 34. ввод 1 цифры в поле "Год"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getCardUnderLimitYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldErrorWithUnderLimitCvcCreditCard() {                       // 35. ввод 1 цифры в поле "CVC/CVV"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(1);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongFormat();
    }

    @Test
    public void shouldErrorWithInvalidDateMonthCreditCard() {                   // 36. ввод предыдущего месяца в поле "Месяц"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateInvalidMonthDate(2);
        var year = DataHelper.getGenerateYear(0);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitWrongCardDate();
    }

    @Test
    public void shouldErrorWithInvalidDateYearCreditCard() {                    // 37. ввод предыдущего года в поле "Год"
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateInvalidYearDate(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        creditCardPage.waitCardExpired();
    }

    @Test
    public void shouldAddPaymentIDInOrderEntry() {                             // 38. статус APPROVED, должно быть поле PaymentId при оплате одобренной дебетовой картой
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        var expected = SQLHelper.getCreditRequestReEntryId();
        var actual = SQLHelper.getCreditOrderEntryId();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldDontAddPaymentIDInOrderEntryStatusDeclined() {             // 39. статус DECLINED, должно отсутствовать поле PaymentId при оплате отклоненной дебетовой картой
        creditCardPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getSecondCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        creditCardPage.filledForm(cardNumber, month, year, owner, cvc);
        var expected = SQLHelper.getCreditRequestReEntryId();
        var actual = SQLHelper.getCreditOrderEntryId();
        assertNotEquals(expected, actual);
    }
}