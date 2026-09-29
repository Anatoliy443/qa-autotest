# qa-autotest

Учебный Java-проект с решениями задач по базовой Java и автоматизированными
тестами на Gradle и JUnit 5.

## Требования

- JDK 17+ для запуска Gradle Wrapper и учебного API (проверено на JDK 25).
- Gradle устанавливается автоматически через `gradlew`. Исходный код совместим с Java 14.

## Запуск всех тестов с итоговым сообщением

Задача `runAllTests` запускает тесты домашней работы №3 с тегом `homework3`. После неё автоматически
выполняется `testRunIsOver`, которая выводит `Test run is over`.

macOS/Linux:

```shell
./gradlew runAllTests
```

Windows:

```powershell
.\gradlew.bat runAllTests
```

## Тестовые классы

- `RandomMethodsTest` проверяет требования задачи 1: случайные данные,
  повторный и параметризованный запуск, строки начала и конца теста.
- `AllMethodsTest` содержит 12 тестовых методов с информативными ассертами.
  Каждый метод запускается не менее 10 раз.
- `AssertionExamplesTest` содержит четыре примера ассертов, включая проверку
  булевого значения, списка и намеренно падающий тест.

## Домашнее задание «Assertion»

Обычный успешный запуск использует фильтрацию по тегу `homework3` и исключает
учебный тег `expected-failure`:

```shell
./gradlew runAllTests
```

Для отдельной демонстрации информативного падающего ассерта:

```shell
./gradlew runExpectedFailure
```

Задача `runExpectedFailure` должна завершиться с ошибкой. В сообщении будет
указано ожидаемое и фактическое значение.


## Домашнее задание №4: REST Assured

[Инструкция, покрытие и результаты](docs/homework4.md).

1. Запустить учебный API с пустой базой в отдельном терминале:

   ```shell
   bash scripts/start-training-api.sh "$HOME/Downloads/oa_at_train_project-1.0-SNAPSHOT.jar.zip"
   ```

2. Указать пароль учебного администратора и запустить все API-тесты:

   ```shell
   read -s API_PASSWORD
   export API_PASSWORD
   ./gradlew apiTest
   ```

Swagger: <http://127.0.0.1:8080/swagger-ui/index.html>.
HTML-отчёт: `build/reports/tests/apiTest/index.html`.

В предоставленной версии API найден дефект: GET отсутствующего товара возвращает
500 вместо 404. Поэтому один тест **должен упасть до исправления API**;
ожидание 404 сохранено согласно Swagger. Подробности в [отчёте](docs/homework4.md).
