package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {
    // Задача 1: проверяем чётное и нечётное числа.
    @Test
    void shouldCheckIfNumberIsEven() {
        assertTrue(App.isEven(4));
        assertFalse(App.isEven(3));
        System.out.println("Тест задачи 1 успешно завершён");
    }

    // Задача 2: доступ разрешён только пользователю старше 18 лет.
    @Test
    void shouldCheckAccessByAge() {
        assertEquals("Allowed", App.checkAccess(19));
        assertEquals("Denied", App.checkAccess(18));
        System.out.println("Тест задачи 2 успешно завершён");
    }

    // Задача 3: ноль считается положительным по условию задания.
    @Test
    void shouldCheckIfNumberIsPositive() {
        assertTrue(App.isPositive(0));
        assertTrue(App.isPositive(5));
        assertFalse(App.isPositive(-1));
        System.out.println("Тест задачи 3 успешно завершён");
    }

    // Задача 4: проверяем оценки и ошибку для недопустимого результата.
    @Test
    void shouldReturnGradeByScore() {
        assertEquals("E", App.getGrade(20));
        assertEquals("D", App.getGrade(21));
        assertEquals("C", App.getGrade(60));
        assertEquals("B", App.getGrade(61));
        assertEquals("A", App.getGrade(100));
        assertEquals("Error", App.getGrade(101));
        System.out.println("Тест задачи 4 успешно завершён");
    }

    // Задача 5: проверяем строку обратного отсчёта.
    @Test
    void shouldCreateBlastOffCountdown() {
        assertEquals("5 4 3 2 1 Поехали!", App.blastOff(5));
        System.out.println("Тест задачи 5 успешно завершён");
    }

    // Задача 6: 1 + 2 + 3 + 4 + 5 должно быть равно 15.
    @Test
    void shouldCalculateSumToNumber() {
        assertEquals(15, App.sumToN(5));
        System.out.println("Тест задачи 6 успешно завершён");
    }

    // Задача 7: слово Bug ищется без учёта регистра.
    @Test
    void shouldFindBugInMessages() {
        assertTrue(App.hasBug(new String[]{"Done", "bug"}));
        assertFalse(App.hasBug(new String[]{"Done", "Fixed"}));
        System.out.println("Тест задачи 7 успешно завершён");
    }

    // Задача 8: в диапазоне от 2 до 5 чётными являются 2 и 4.
    @Test
    void shouldReturnEvenNumbersInRange() {
        assertEquals("2 4", App.getEvenInRange(2, 5));
        System.out.println("Тест задачи 8 успешно завершён");
    }

    // Задача 9: самое большое число в массиве — 9.
    @Test
    void shouldFindMaximumNumber() {
        assertEquals(9, App.findMax(new int[]{-3, 9, 2}));
        System.out.println("Тест задачи 9 успешно завершён");
    }

    // Задача 10: проверяем новый массив с обратным порядком элементов.
    @Test
    void shouldReverseArray() {
        String[] expected = {"Zero", "Two", "One"};
        String[] actual = App.reverse(new String[]{"One", "Two", "Zero"});

        assertArrayEquals(expected, actual);
        System.out.println("Тест задачи 10 успешно завершён");
    }

    // Задача 11: среднее чисел 1, 2, 3 и 4 должно быть равно 2.5.
    @Test
    void shouldCalculateAverage() {
        double expected = 2.5;
        double actual = App.calcAverage(List.of(1, 2, 3, 4));

        assertEquals(expected, actual);
        System.out.println("Тест задачи 11 успешно завершён");
    }

    // Задача 12: из нового списка должны быть удалены все имена "Иван".
    @Test
    void shouldRemoveSpecificName() {
        List<String> names = List.of("Анна", "Иван", "Олег", "Иван");
        List<String> expected = List.of("Анна", "Олег");

        List<String> actual = App.removeSpecificName(names, "Иван");

        assertEquals(expected, actual);
        System.out.println("Тест задачи 12 успешно завершён");
    }
}
