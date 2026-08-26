package org.example;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Задача 1 темы «Assertion»: четыре примера информативных ассертов.
 */
@Tag("homework3")
class AssertionExamplesTest {

    /** Проверяет метод, возвращающий boolean true. */
    @Test
    void shouldReturnTrueForEvenNumber() {
        boolean expected = true;
        boolean actual = App.isEven(8);

        assertTrue(actual,
                "Ожидалось: " + expected + "; фактически получено: " + actual);
    }

    /** Проверяет метод, возвращающий boolean false. */
    @Test
    void shouldReturnFalseForNegativeNumber() {
        boolean expected = false;
        boolean actual = App.isPositive(-10);

        assertFalse(actual,
                "Ожидалось: " + expected + "; фактически получено: " + actual);
    }

    /** Проверяет метод, который возвращает новый список. */
    @Test
    void shouldReturnListWithoutSpecificName() {
        List<String> expected = List.of("Анна", "Олег");
        List<String> actual = App.removeSpecificName(
                List.of("Анна", "Иван", "Олег", "Иван"),
                "Иван"
        );

        assertEquals(expected, actual,
                "Ожидалось: " + expected + "; фактически получено: " + actual);
    }

    /**
     * Намеренно падающий тест.
     * Метод правильно возвращает "Allowed", но expected специально задан как "Denied".
     * Запуск: ./gradlew runExpectedFailure
     */
    @Test
    @Tag("expected-failure")
    void shouldDemonstrateInformativeFailedAssertion() {
        String expected = "Denied"; // Специально неверное ожидаемое значение.
        String actual = App.checkAccess(25);

        assertEquals(expected, actual,
                "Ожидалось: " + expected + "; фактически получено: " + actual);
    }
}
