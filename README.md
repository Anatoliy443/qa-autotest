# qa-autotest

Учебный Java-проект с решениями задач по базовой Java и автоматизированными
тестами на Gradle и JUnit 5.

## Требования

- JDK 14+
- Gradle 7.6+

## Запуск всех тестов с итоговым сообщением

Задача `runAllTests` запускает все JUnit-тесты. После неё автоматически
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
- `AllMethodsTest` содержит 12 тестовых методов для всех методов из первой
  домашней работы и печатает `TEST PASSED` или `TEST FAILED`.
