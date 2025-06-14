
# План автоматизации тестирования сервиса покупки тура
## Валидные данные
| Поле         | Данные                                                                                                   |
|--------------|----------------------------------------------------------------------------------------------------------|
| Номер карты  | 4444 4444 4444 4441 - статус APPROVED<br/> 4444 4444 4444 4442 - статус DECLINED                         |
| Месяц        | Целое число от 01 до 12. Не может быть меньше текущего месяца,<br/> если в поле `Год` указан текущий год |
| Год          | Последние две цифры года в диапазоне от текущего до +5 лет                                               |
| Владелец     | Имя и фамилия на латинице (не менее 2х символов), разделенные пробелом. Могут содержать дефис            |
| CVC/CVV      | Трехзначное число от 001 до 999, кроме 000                                                               |

## Вкладка "Купить"
## Предусловия:
Открыть главную страницу сервиса, нажать на кнопку `Купить`, открыв вкладку `Оплата по карте`.

### Позитивные сценарии:
1 Заполнить форму валидными данными с APPROVED-картой, нажать на кнопку `Продолжить`. Успешная отправка формы, появление сообщения об одобренной операции.

### Негативные сценарии:
2. Заполнить форму валидными данными с DECLINED-картой, нажать на кнопку `Продолжить`. Осуществляется отправка формы, появление сообщения об отклонении операции банком.
---
3. Форма пустая, нажать на кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщений об ошибке под всеми полями формы.
4. Заполнение формы валидными данными, поле `Номер карты` - пустое, нажать на кнопку 'Продолжить'. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Номер карты`.
5. Заполнение формы валидными данными, поле `Месяц` - пустое, нажать на кнопку 'Продолжить'. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Месяц`.
6. Заполнение формы валидными данными, поле `Год` - пустое, нажать на кнопку 'Продолжить'. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Год`.
7. Заполнение формы валидными данными, поле `Владелец` - пустое, нажать на кнопку 'Продолжить'. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Имя`.
8. Заполнение формы валидными данными, поле `CVC/CVV` - пустое, нажать на кнопку 'Продолжить'. Отправка формы не осуществляется, появление сообщения об ошибке у поля `CVC/CVV`.
---
9. Форма пустая, ввести в поле `Номер карты` символы кириллицы. В поле `Номер карты` данные не вводятся.
10. Форма пустая, ввести в поле `Номер карты` спецсимволы. В поле `Номер карты`данные не вводятся.
11. Форма пустая, ввести в поле `Месяц` символы латиницы. В поле `Месяц` данные не вводятся.
12. Форма пустая, ввести в поле `Месяц` спецсимволы. В поле `Месяц` данные не вводятся.
13. Форма пустая, ввести в поле `Год` символы латиницы. В поле `Год` данные не вводятся.
14. Форма пустая, ввести в поле `Год` спецсимволы. В поле `Год` данные не вводятся.
15. Форма пустая, ввести в поле `Владелец` символы кириллицы. В поле `Владелец` данные не вводятся.
16. Форма пустая, ввести в поле `Владелец` спецсимволы. В поле `Владелец` данные не вводятся.
17. Форма пустая, ввести в поле `Владелец` цифровое значение. В поле `Владелец` данные не вводятся. 
18. Форма пустая, ввести в поле `Владелец` иероглифы. В поле `Владелец` данные не вводятся.
19. Форма пустая, ввести в поле `Владелец` символы арабской вязи. В поле `Владелец` данные не вводятся.
20. Форма пустая, ввести в поле `CVC/CVV` символы на латинице. В поле `CVC/CVV` данные не вводятся.
21. Форма пустая, ввести в поле `CVC/CVV` спецсимволы. В поле `CVC/CVV` данные не вводятся.
---
22. Заполнение формы валидными данными, ввести в поле `Номер карты` цифровое значение из 17 символов. Последний символ в поле `Номер карты` не вводится.
23. Заполнение формы валидными данными, ввести в поле `Месяц` цифровое значение из 3 символов. Последний символ в поле `Месяц` не вводится.
24. Заполнение формы валидными данными, ввести в поле `Год` цифровое значение из 3 символов. Последний символ в поле `Год` не вводится.
25. Заполнение формы валидными данными, ввести в поле `CVC/CVV` цифровое значение из 4 символов. Последний символ в поле `CVC/CVV` не вводится.
---
26. Все поля формы заполнены валидными данными, ввести в поле `Номер карты` цифровое значение, состоящее из 16-х нулей, нажать кнопку `Продолжить`. Осуществляется отправка формы, появление сообщения об отклонении операции.
27. Все поля формы заполнены валидными данными, ввести в поле `Месяц` цифровое значение, состоящее из 2-х нулей, нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Месяц`.
28. Все поля формы заполнены валидными данными, ввести в поле `Год` цифровое значение, состоящее из 2-х нулей, нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Год`.
29. Все поля формы заполнены валидными данными, ввести в поле `CVC/CVV` цифровое значение, состоящее из 3-х нулей, нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `CVC/CVV`.
30. Все поля формы заполнены валидными данными, ввести в поле `CVC/CVV` цифровое значение "999", нажать кнопку `Продолжить`. Успешная отправка формы, появление сообщения об одобренной операции.
---
31. Все поля формы заполнены валидными данными, ввести в поле `Номер карты` цифровое значение, состоящее из 15 цифр, нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Номер карты`. 
32. Все поля формы заполнены валидными данными, ввести в поле `Месяц` цифровое значение "13", нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Месяц`.
33. Все поля формы заполнены валидными данными, ввести в поле `Месяц` 1 цифру, нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Месяц`.
34. Все поля формы заполнены валидными данными, ввести в поле `Год` 1 цифру, нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Год`.
35. Все поля формы заполнены валидными данными, ввести в поле `CVC/CVV` 1 цифру, нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `CVC/CVV`.
36. Все поля формы заполнены валидными данными, ввести в поле `Месяц` значение месяца, предшествующее текущему, ввести в поле `Год` значение текущего года. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Месяц`.
37. Все поля формы заполнены валидными данными, ввести в поле `Год` значение года, предшествующее текущему. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Год`.
---
## Вкладка "Купить в кредит"
## Предусловия:
Открыть главную страницу сервиса, нажать на кнопку `Купить в кредит`, открыв вкладку `Кредит по данным карты`.

### Позитивные сценарии:
1. Заполнить форму валидными данными с APPROVED-картой, нажать на кнопку `Продолжить`. Успешная отправка формы, появление сообщения об одобренной операции.

### Негативные сценарии:
2. Заполнить форму валидными данными с DECLINED-картой, нажать на кнопку `Продолжить`. Осуществляется отправка формы, появление сообщения об отклонении операции банком.
---
3. Форма пустая, нажать на кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщений об ошибке под всеми полями формы.
4. Заполнение формы валидными данными, поле `Номер карты` - пустое, нажать на кнопку 'Продолжить'. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Номер карты`.
5. Заполнение формы валидными данными, поле `Месяц` - пустое, нажать на кнопку 'Продолжить'. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Месяц`.
6. Заполнение формы валидными данными, поле `Год` - пустое, нажать на кнопку 'Продолжить'. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Год`.
7. Заполнение формы валидными данными, поле `Владелец` - пустое, нажать на кнопку 'Продолжить'. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Имя`.
8. Заполнение формы валидными данными, поле `CVC/CVV` - пустое, нажать на кнопку 'Продолжить'. Отправка формы не осуществляется, появление сообщения об ошибке у поля `CVC/CVV`.
---
9. Форма пустая, ввести в поле `Номер карты` символы кириллицы. В поле `Номер карты` данные не вводятся.
10. Форма пустая, ввести в поле `Номер карты` спецсимволы. В поле `Номер карты`данные не вводятся.
11. Форма пустая, ввести в поле `Месяц` символы латиницы. В поле `Месяц` данные не вводятся.
12. Форма пустая, ввести в поле `Месяц` спецсимволы. В поле `Месяц` данные не вводятся.
13. Форма пустая, ввести в поле `Год` символы латиницы. В поле `Год` данные не вводятся.
14. Форма пустая, ввести в поле `Год` спецсимволы. В поле `Год` данные не вводятся.
15. Форма пустая, ввести в поле `Владелец` символы кириллицы. В поле `Владелец` данные не вводятся.
16. Форма пустая, ввести в поле `Владелец` спецсимволы. В поле `Владелец` данные не вводятся.
17. Форма пустая, ввести в поле `Владелец` цифровое значение. В поле `Владелец` данные не вводятся.
18. Форма пустая, ввести в поле `Владелец` иероглифы. В поле `Владелец` данные не вводятся.
19. Форма пустая, ввести в поле `Владелец` символы арабской вязи. В поле `Владелец` данные не вводятся.
20. Форма пустая, ввести в поле `CVC/CVV` символы на латинице. В поле `CVC/CVV` данные не вводятся.
21. Форма пустая, ввести в поле `CVC/CVV` спецсимволы. В поле `CVC/CVV` данные не вводятся.
---
22. Заполнение формы валидными данными, ввести в поле `Номер карты` цифровое значение из 17 символов. Последний символ в поле `Номер карты` не вводится.
23. Заполнение формы валидными данными, ввести в поле `Месяц` цифровое значение из 3 символов. Последний символ в поле `Месяц` не вводится.
24. Заполнение формы валидными данными, ввести в поле `Год` цифровое значение из 3 символов. Последний символ в поле `Год` не вводится.
25. Заполнение формы валидными данными, ввести в поле `CVC/CVV` цифровое значение из 4 символов. Последний символ в поле `CVC/CVV` не вводится.
---
26. Все поля формы заполнены валидными данными, ввести в поле `Номер карты` цифровое значение, состоящее из 16-х нулей, нажать кнопку `Продолжить`. Осуществляется отправка формы, появление сообщения об отклонении операции.
27. Все поля формы заполнены валидными данными, ввести в поле `Месяц` цифровое значение, состоящее из 2-х нулей, нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Месяц`.
28. Все поля формы заполнены валидными данными, ввести в поле `Год` цифровое значение, состоящее из 2-х нулей, нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Год`.
29. Все поля формы заполнены валидными данными, ввести в поле `CVC/CVV` цифровое значение, состоящее из 3-х нулей, нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `CVC/CVV`.
30. Все поля формы заполнены валидными данными, ввести в поле `CVC/CVV` цифровое значение "999", нажать кнопку `Продолжить`. Успешная отправка формы, появление сообщения об одобренной операции.
---
31. Все поля формы заполнены валидными данными, ввести в поле `Номер карты` цифровое значение, состоящее из 15 цифр, нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Номер карты`.
32. Все поля формы заполнены валидными данными, ввести в поле `Месяц` цифровое значение "13", нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Месяц`.
33. Все поля формы заполнены валидными данными, ввести в поле `Месяц` 1 цифру, нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Месяц`.
34. Все поля формы заполнены валидными данными, ввести в поле `Год` 1 цифру, нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Год`.
35. Все поля формы заполнены валидными данными, ввести в поле `CVC/CVV` 1 цифру, нажать кнопку `Продолжить`. Отправка формы не осуществляется, появление сообщения об ошибке у поля `CVC/CVV`.
36. Все поля формы заполнены валидными данными, ввести в поле `Месяц` значение месяца, предшествующее текущему, ввести в поле `Год` значение текущего года. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Месяц`.
37. Все поля формы заполнены валидными данными, ввести в поле `Год` значение года, предшествующее текущему. Отправка формы не осуществляется, появление сообщения об ошибке у поля `Год`.
---
## API сценарии с проверкой результатов выполнения операций в базе данных:

38. Отправить POST запрос на адрес: URL http://localhost:8080/api/v1/pay Тело запроса: {"number": "4444 4444 4444 4441", "year": "27", "month": "12", "holder": "Petr Ivanov", "cvc": "123"}. Ожидаемый результат: код ответа сервера: 200; статус "APPROVED". В "order_entry" создается "payment_id" равный "transaction_id" в "payment_entity".
39. Отправить POST запрос на адрес: URL http://localhost:8080/api/v1/pay Тело запроса: {"number": "4444 4444 4444 4442", "year": "27", "month": "12", "holder": "Petr Ivanov", "cvc": "123"}. Ожидаемый результат: код ответа сервера: 400; статус "DECLINED". В "order_entry" не создается "payment_id".
40. Отправить POST запрос на адрес: URL http://localhost:8080/api/v1/credit Тело запроса: {"number": "4444 4444 4444 4442", "year": "27", "month": "12", "holder": "Petr Ivanov", "cvc": "123"}. Ожидаемый результат: статус "DECLINED". В "credit_request_entry" создается запись транзакции.
41. Отправить POST запрос на адрес: URL http://localhost:8080/api/v1/credit Тело запроса: {"number": "4444 4444 4444 4441", "year": "27", "month": "12", "holder": "Petr Ivanov", "cvc": "123"}. Ожидаемый результат: в "credit_request_entry" создается запись транзакции. В "order_entry" создается "credit_id" равный "bank_id" в "credit_request_entry".
42. Отправить POST запрос на адрес: URL http://localhost:8080/api/v1/credit Тело запроса: {"number": "4444 4444 4444 4442", "year": "27", "month": "12", "holder": "Petr Ivanov", "cvc": "123"}. Ожидаемый результат: статус "DECLINED". В "order_entry" не создается "credit_id" операции.
---
### Перечень используемых инструментов:

1. `IntelliJ IDEA Community Edition 2024.3` - функциональная среда разработки, подходящая для многих языков программирования;
2. `Java 11` - объектно-ориентированный язык программирования, оптимальный для написания понятных и наглядных автотестов;
3. `Docker` - ПО для автоматизации развёртывания и управления приложением в средах с поддержкой контейнеризации;
4. `Gradle` - мощная и простая система автоматической сборки проектов;
5. `Selenide` - фреймворк для автоматизированного тестирования веб-приложений на основе Selenium WebDriver;
6. `JUnit 5` - среда модульного тестирования для языка программирования Java;
7. `REST Assured` - для тестирования API сервиса, встраивается в тесты на Java;
8. `DBUtils` - библиотека, которая значительно упрощает работу с JDBC;
9. `Faker` - библиотека для генерации фальшивых данных. Подходит на все случаи жизни: тестирование кода, заполнение баз данных для стресс-тестов, анонимизация данных.
10. `Allure Report` - это инструмент для создания отчетов о тестировании с открытым исходным кодом. Он создает подробное представление о том, что было протестировано и извлекает максимум пользы из ежедневного выполнения тестов.
11. `Google Chrome` - это кроссплатформенный веб-браузер, разработанный компанией Google.
12. `Git` - это специальная программа, которая позволяет отслеживать любые изменения в файлах, хранить их версии и оперативно возвращаться в любое сохранённое состояние.
13. `GitHub` - это платформа для хранения и управления кодом, которая позволяет разработчикам совместно работать над проектами.

### Перечень и описание возможных рисков при автоматизации:

1. Отсутствие технической документации.
2. Отсутствие тестовых меток и сложности при составлении селекторов тестовой страницы приложения.
3. Возможное изменение верстки и селекторов страницы.
6. Временные затраты на настройку `Docker`.
7. Неверный выбор инструментов для тестирования. Зависимость от таких инструментов.
8. Возможные сложности при написании автотестов.

### Интервальная оценка с учётом рисков (в часах):
Для осуществления данного плана необходимо 48 часов.


### План сдачи работ (когда будут авто-тесты, результаты их прогона и отчёт по автоматизации):
Авто-тесты: 31.05.2025<br/>
Результаты прогона авто-тестов: 01.06.2025<br/>
Отчет по автоматизации: 03.06.2025 
    