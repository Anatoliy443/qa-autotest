package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Тестовый класс для задачи 1 второго домашнего задания.
 * Здесь запускаются три метода из темы «Базовая Java».
 */
class RandomMethodsTest {
    private static final Random RANDOM = new Random();

    /** Выполняется перед каждым обычным, повторным и параметризованным тестом. */
    @BeforeEach
    void printTestStart() {
        System.out.println("========================Test method start");
    }

    /** Выполняется после каждого обычного, повторного и параметризованного теста. */
    @AfterEach
    void printTestEnd() {
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // isEven запускается один раз со случайным числом от 1 до 100.
    @Test
    void runIsEvenWithRandomNumber() {
        int number = RANDOM.nextInt(100) + 1;
        App.isEven(number);
    }

    // checkAccess запускается 20 раз. Для каждого запуска создаётся возраст от 0 до 99.
    @RepeatedTest(20)
    void runCheckAccessTwentyTimes() {
        int age = RANDOM.nextInt(100);
        App.checkAccess(age);
    }

    // Каждый элемент потока становится отдельным запуском параметризованного теста.
    @ParameterizedTest
    @MethodSource("randomScores")
    void runGetGradeWithRandomScores(int score) {
        App.getGrade(score);
    }

    // Создаём массив из 10 случайных оценок от 0 до 100 включительно.
    static IntStream randomScores() {
        return RANDOM.ints(10, 0, 101);
    }
}
