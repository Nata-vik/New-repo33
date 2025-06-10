package test;


import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DebitCardPage;
import page.HomePage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class DebitCardPayTest {

    private HomePage homePage;
    private DebitCardPage debitCardPage;


    @BeforeEach
    public void setup() {
        homePage = open("http://localhost:8080/", HomePage.class);
    }


    // Debit Card

    @Test
    public void shouldPurchaseWithApprovedCard() {                         // 1. ввод валидных данных Approved карты
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitErrorNotification();
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
            debitCardPage.waitEmptyField();
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
        debitCardPage.waitEmptyField();
    }

    @Test
    public void shouldReturnErrorWithEmptyYearDebitCard() {                 // 6. пустое поле "Год"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getEmptyYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitEmptyField();
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
        debitCardPage.waitEmptyField();
    }

    @Test
    public void shouldReturnErrorWithCyrillicSymbolNumberDebitCard() {           // 9. ввод символов кириллицы в поле "Номер карты"
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
    public void shouldReturnErrorWithIncorrectSymbolOwnerDebitCard() {             // 16. ввод спецсимволов в поле "Владелец"
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
    public void shouldReturnErrorWithArabicSymbolOwnerDebitCard() {                // 18. ввод арабских символов в поле "Владелец"
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
    public void shouldReturnErrorWithLatinSymbolCvcDebitCard() {                   // 19. ввод символов на латинице в поле "CVC/CVV"
        debitCardPage = homePage.openDebitForm();
        var cvc = DataHelper.getGenerateInvalidCvcCode("EN");
        debitCardPage.onlyCVCField(cvc);
        debitCardPage.emptyCVCField();
    }

    @Test
    public void shouldReturnErrorWithIncorrectSymbolCvcDebitCard() {                // 20. ввод спецсимволов в поле "CVC/CVV"
        debitCardPage = homePage.openDebitForm();
        var cvc = DataHelper.getSpecialSymbolsCvcCode();
        debitCardPage.onlyCVCField(cvc);
        debitCardPage.emptyCVCField();
    }

    @Test
    public void shouldReturnErrorWith17SymbolNumberDebitCard() {                   // 21. ввод 17 символов в поле "Номер карты"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getCardAfterLimitCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitErrorNotification();
        var expected = DataHelper.getFirstCardStatus();
        var actual = SQLHelper.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnErrorWith3SymbolMonthDebitCard() {                    // 22. ввод 3 символов в поле "Месяц"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getCardAfterLimitMonth();
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitErrorNotification();
        var expected = DataHelper.getFirstCardStatus();
        var actual = SQLHelper.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnErrorWith3SymbolYearDebitCard() {                    // 23. ввод 3 символов в поле "Год"
        debitCardPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getCardAfterLimitYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        debitCardPage.filledForm(cardNumber, month, year, owner, cvc);
        debitCardPage.waitErrorNotification();
        var expected = DataHelper.getFirstCardStatus();
        var actual = SQLHelper.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }




}
