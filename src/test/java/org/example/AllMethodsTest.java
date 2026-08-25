package org.example;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * Задача 2. Этот класс проверяет все 12 методов первой домашней работы.
 * Assert не используется: результат проверяется через if и выводится
 * строка TEST PASSED или TEST FAILED.
 *
 * Распределение аннотаций:
 * 4 метода с @Test, 4 с @RepeatedTest и 4 с @ParameterizedTest.
 */
class AllMethodsTest {
    private static final Random RANDOM = new Random();

    // -------------------- Четыре обычных теста @Test --------------------

    /** Задача 1: проверка чётности случайного числа. */
    @Test
    void testIsEven() {
        int number = randomInt(-100, 100);
        boolean expected = number % 2 == 0;
        boolean actual = App.isEven(number);

        printResult(actual == expected);
    }

    /** Задача 2: проверка доступа для случайного возраста. */
    @Test
    void testCheckAccess() {
        int age = randomInt(0, 99);
        String expected = age > 18 ? "Allowed" : "Denied";
        String actual = App.checkAccess(age);

        printResult(expected.equals(actual));
    }

    /** Задача 3: проверка случайного положительного или отрицательного числа. */
    @Test
    void testIsPositive() {
        int number = randomInt(-100, 100);
        boolean expected = number >= 0;
        boolean actual = App.isPositive(number);

        printResult(actual == expected);
    }

    /** Задача 4: получение оценки для случайного количества баллов. */
    @Test
    void testGetGrade() {
        int score = randomInt(-10, 110);
        String expected = expectedGrade(score);
        String actual = App.getGrade(score);

        printResult(expected.equals(actual));
    }

    // ---------------- Четыре повторных теста @RepeatedTest ----------------

    /** Задача 5: обратный отсчёт проверяется три раза со случайным стартом. */
    @RepeatedTest(3)
    void testBlastOff() {
        int start = randomInt(1, 10);
        StringBuilder expected = new StringBuilder();
        for (int number = start; number >= 1; number--) {
            expected.append(number).append(' ');
        }
        expected.append("Поехали!");

        String actual = App.blastOff(start);
        printResult(expected.toString().equals(actual));
    }

    /** Задача 6: сумма от 1 до случайного n проверяется три раза. */
    @RepeatedTest(3)
    void testSumToN() {
        int number = randomInt(1, 100);
        int expected = number * (number + 1) / 2;
        int actual = App.sumToN(number);

        printResult(actual == expected);
    }

    /** Задача 7: три раза создаётся новый случайный массив сообщений. */
    @RepeatedTest(3)
    void testHasBug() {
        boolean addBug = RANDOM.nextBoolean();
        String[] messages = addBug
                ? new String[]{"Done", "Fixed", "bUg"}
                : new String[]{"Done", "Fixed", "Ready"};

        boolean actual = App.hasBug(messages);
        printResult(actual == addBug);
    }

    /** Задача 8: три раза создаются новые границы диапазона. */
    @RepeatedTest(3)
    void testGetEvenInRange() {
        int start = randomInt(-20, 20);
        int end = start + randomInt(0, 20);
        String expected = expectedEvenNumbers(start, end);
        String actual = App.getEvenInRange(start, end);

        printResult(expected.equals(actual));
    }

    // ------------ Четыре параметризованных теста @ParameterizedTest ------------

    /** Задача 9: каждый случайный массив становится отдельным набором данных. */
    @ParameterizedTest
    @MethodSource("randomIntegerArrays")
    void testFindMax(int[] numbers) {
        int expected = Arrays.stream(numbers).max().orElseThrow();
        int actual = App.findMax(numbers);

        printResult(actual == expected);
    }

    /** Задача 10: проверка переворота нескольких массивов строк. */
    @ParameterizedTest
    @MethodSource("randomStringArrays")
    void testReverse(String[] words) {
        String[] expected = new String[words.length];
        for (int index = 0; index < words.length; index++) {
            expected[index] = words[words.length - 1 - index];
        }

        String[] actual = App.reverse(words);
        printResult(Arrays.equals(expected, actual));
    }

    /** Задача 11: проверка среднего арифметического случайных списков. */
    @ParameterizedTest
    @MethodSource("randomNumberLists")
    void testCalcAverage(List<Integer> numbers) {
        double expected = numbers.stream().mapToInt(Integer::intValue).average().orElseThrow();
        double actual = App.calcAverage(numbers);

        printResult(Double.compare(actual, expected) == 0);
    }

    /** Задача 12: проверка удаления имени из нескольких списков. */
    @ParameterizedTest
    @MethodSource("randomNameLists")
    void testRemoveSpecificName(List<String> names) {
        String nameToRemove = "Иван";
        List<String> expected = new ArrayList<>();
        for (String name : names) {
            if (!nameToRemove.equals(name)) {
                expected.add(name);
            }
        }

        List<String> actual = App.removeSpecificName(names, nameToRemove);
        printResult(expected.equals(actual));
    }

    /** Печатает результат проверки вместо использования assert. */
    private static void printResult(boolean passed) {
        if (passed) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    /** Возвращает случайное число, включая обе переданные границы. */
    private static int randomInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    private static String expectedGrade(int score) {
        if (score < 0 || score > 100) {
            return "Error";
        } else if (score <= 20) {
            return "E";
        } else if (score <= 40) {
            return "D";
        } else if (score <= 60) {
            return "C";
        } else if (score <= 80) {
            return "B";
        }
        return "A";
    }

    private static String expectedEvenNumbers(int start, int end) {
        StringBuilder expected = new StringBuilder();
        for (int number = start; number <= end; number++) {
            if (number % 2 == 0) {
                if (expected.length() > 0) {
                    expected.append(' ');
                }
                expected.append(number);
            }
        }
        return expected.toString();
    }

    static Stream<int[]> randomIntegerArrays() {
        return Stream.generate(() -> RANDOM.ints(5, -100, 101).toArray()).limit(4);
    }

    static Stream<Arguments> randomStringArrays() {
        String[] availableWords = {"One", "Two", "Three", "Zero", "Java"};
        return Stream.generate(() -> IntStream.range(0, 4)
                .mapToObj(index -> availableWords[RANDOM.nextInt(availableWords.length)])
                .toArray(String[]::new))
                // Arguments.of сообщает JUnit, что весь String[] — один параметр теста.
                .map(words -> Arguments.of((Object) words))
                .limit(4);
    }

    static Stream<List<Integer>> randomNumberLists() {
        return Stream.generate(() -> RANDOM.ints(5, -50, 51)
                .boxed()
                .collect(Collectors.toList())).limit(4);
    }

    static Stream<List<String>> randomNameLists() {
        String[] availableNames = {"Анна", "Иван", "Олег", "Мария"};
        return Stream.generate(() -> IntStream.range(0, 6)
                .mapToObj(index -> availableNames[RANDOM.nextInt(availableNames.length)])
                .collect(Collectors.toList())).limit(4);
    }
}
