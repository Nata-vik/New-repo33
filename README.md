# Сервис заказа путешествия

___
## Описание приложения

### Бизнес часть

Приложение представляет из себя веб-сервис.
Приложение предлагает купить тур по определённой цене с помощью двух способов:
1. Обычная оплата по дебетовой карте.
1. Выдача кредита по данным банковской карты.

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
* сервису платежей (далее - Payment Gate)
* кредитному сервису (далее - Credit Gate)

Приложение должно в собственной СУБД сохранять информацию о том, каким способом был совершён платёж и успешно ли он был совершён (при этом данные карт сохранять не допускается).

### Техническая часть

Само приложение расположено в файле [`aqa-shop.jar`](artifacts/aqa-shop.jar) и запускается на порту `8080`.

В файле [`application.properties`](application.properties) приведён ряд типовых настроек:
* учётные данные и url для подключения к СУБД
* url-адреса банковских сервисов

### СУБД

Заявлена поддержка двух СУБД:
* MySQL
* PostgreSQL

Учётные данные и url для подключения задаются в файле [`application.properties`](application.properties).

### Банковские сервисы

Разработчики подготовили симулятор банковских сервисов, который может принимать запросы в нужном формате и генерировать ответы.

Симулятор расположен в каталоге [`gate-simulator`](gate-simulator). Для запуска необходимо перейти в этот каталог.

Запускается симулятор командой `npm start` на порту 9999.

Симулятор позволяет для заданного набора карт генерировать предопределённые ответы.

Набор карт представлен в формате JSON в файле [`data.json`](gate-simulator/data.json).

Сервис симулирует и Payment Gate и Credit Gate.
___
### Начало работы

Для запуска тестов потребуется следующее ПО:
* GitHUB
* IntelliJ IDEA
* Git
* Docker Desktop
* Google Chrome

### Установка и запуск

1. Скачать код репозитория любым способом.
2. Запускаем контейнер с помощью команды в консоли:
```
docker-compose up
```
3. Запустить SUT.

для MySQL в консоли ввести команду: 
```
java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/db
```

для PostgreSQL в консоли ввести команду: 
```
java -jar .\artifacts\aqa-shop.jar
```

4. Запустить тесты командой в консоли:

для MySQL в консоли ввести команду: 
```
./gradlew clean test -Ddb=jdbc:mysql://localhost:3306/db
```

для PostgreSQL в консоли ввести команду: 
```
./gradlew clean test
```
___

### Дополнительные сведения:

Для просмотра отчета `Allure Report` после выполнения тестов ввести в консоли:
```
./gradlew allureServe
```
___
Установка `headless`-режима `Selenide` осуществляется путем внесения дополнительного свойства в файл `build.gradle`:
```
systemProperty 'selenide.headless', System.getProperty('selenide.headless')
```
___
### После окончания работы:

1. Завершить работу `SUT` сочетанием клавиш `CTRL + C`.
2. Завершить работу контейнеров командой в консоли:
```
docker-compose down
```
___
