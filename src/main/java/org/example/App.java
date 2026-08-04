package org.example;

import java.util.ArrayList;
import java.util.List;

public class App {
    // Класс содержит только статические методы, поэтому создавать его объект не нужно.
    private App() {
    }

    public static void main(String[] args) {
        System.out.println("=== Запуск методов домашнего задания ===");

        // Задача 1
        isEven(4);

        // Задача 2
        checkAccess(19);

        // Задача 3
        isPositive(-5);

        // Задача 4
        getGrade(75);

        // Задача 5
        blastOff(5);

        // Задача 6
        sumToN(5);

        // Задача 7
        hasBug(new String[]{"Готово", "Bug", "Исправлено"});

        // Задача 8
        getEvenInRange(2, 10);

        // Задача 9
        findMax(new int[]{3, 8, -2, 15, 4});

        // Задача 10
        reverse(new String[]{"One", "Two", "Zero"});

        // Задача 11
        calcAverage(List.of(1, 2, 3, 4));

        // Задача 12
        removeSpecificName(List.of("Анна", "Иван", "Олег", "Иван"), "Иван");

        System.out.println("=== Все выбранные методы выполнены ===");
    }

    /**
     * Задача 1. Проверяет, является ли число чётным.
     * Остаток от деления чётного числа на 2 всегда равен нулю.
     */
    public static boolean isEven(int n) {
        boolean result = n % 2 == 0;
        System.out.println("Задача 1: число " + n + ", результат isEven = " + result);
        return result;
    }

    /**
     * Задача 2. Разрешает доступ только пользователям старше 18 лет.
     * Тернарный оператор выбирает одну из двух строк в зависимости от условия.
     */
    public static String checkAccess(int age) {
        String result = age > 18 ? "Allowed" : "Denied";
        System.out.println("Задача 2: возраст " + age + ", результат = " + result);
        return result;
    }

    /**
     * Задача 3. Проверяет, является ли число положительным или равным нулю.
     * Результат выбирается с помощью тернарного оператора.
     */
    public static boolean isPositive(int n) {
        boolean result = n >= 0 ? true : false;
        System.out.println("Задача 3: число " + n + ", результат isPositive = " + result);
        return result;
    }

    /**
     * Задача 4. Возвращает буквенную оценку для результата от 0 до 100.
     */
    public static String getGrade(int score) {
        String result;

        // Значения вне допустимого диапазона не могут быть оценены.
        if (score < 0 || score > 100) {
            result = "Error";
        } else if (score <= 20) {
            // Условия проверяются по возрастанию, поэтому достаточно верхней границы.
            result = "E";
        } else if (score <= 40) {
            result = "D";
        } else if (score <= 60) {
            result = "C";
        } else if (score <= 80) {
            result = "B";
        } else {
            result = "A";
        }

        System.out.println("Задача 4: баллы " + score + ", оценка = " + result);
        return result;
    }

    /**
     * Задача 5. Создаёт обратный отсчёт от start до 1 и добавляет «Поехали!».
     */
    public static String blastOff(int start) {
        StringBuilder result = new StringBuilder();
        for (int number = start; number >= 1; number--) {
            result.append(number).append(' ');
        }
        result.append("Поехали!");
        String countdown = result.toString();
        System.out.println("Задача 5: результат = " + countdown);
        return countdown;
    }

    /**
     * Задача 6. Складывает все целые числа от 1 до n включительно.
     */
    public static int sumToN(int n) {
        int sum = 0;
        for (int number = 1; number <= n; number++) {
            sum += number;
        }
        System.out.println("Задача 6: сумма от 1 до " + n + " = " + sum);
        return sum;
    }

    /**
     * Задача 7. Ищет слово Bug в массиве сообщений без учёта регистра.
     */
    public static boolean hasBug(String[] messages) {
        // В отсутствующем массиве невозможно найти сообщение.
        if (messages == null) {
            System.out.println("Задача 7: массив отсутствует, Bug не найден");
            return false;
        }
        for (String message : messages) {
            // Вызов equalsIgnoreCase у строки "Bug" безопасен, даже если message равен null.
            if ("Bug".equalsIgnoreCase(message)) {
                System.out.println("Задача 7: Bug найден, результат = true");
                return true;
            }
        }
        System.out.println("Задача 7: Bug не найден, результат = false");
        return false;
    }

    /**
     * Задача 8. Собирает чётные числа заданного диапазона в строку через пробел.
     */
    public static String getEvenInRange(int start, int end) {
        StringBuilder result = new StringBuilder();
        for (int number = start; number <= end; number++) {
            if (number % 2 == 0) {
                if (result.length() > 0) {
                    result.append(' ');
                }
                result.append(number);
            }
        }
        String evenNumbers = result.toString();
        System.out.println("Задача 8: диапазон от " + start + " до " + end
                + ", чётные числа = " + evenNumbers);
        return evenNumbers;
    }

    /**
     * Задача 9. Находит самое большое число в массиве.
     */
    public static int findMax(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        int max = arr[0];
        for (int number : arr) {
            if (number > max) {
                max = number;
            }
        }
        System.out.println("Задача 9: максимальное число = " + max);
        return max;
    }

    /**
     * Задача 10. Возвращает новый массив с элементами в обратном порядке.
     */
    public static String[] reverse(String[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        String[] reversed = new String[arr.length];
        for (int index = 0; index < arr.length; index++) {
            reversed[index] = arr[arr.length - 1 - index];
        }
        System.out.println("Задача 10: перевёрнутый массив = "
                + java.util.Arrays.toString(reversed));
        return reversed;
    }

    /**
     * Задача 11. Вычисляет среднее арифметическое чисел в списке.
     */
    public static double calcAverage(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List must not be empty");
        }
        long sum = 0;
        for (int number : list) {
            sum += number;
        }
        double average = (double) sum / list.size();
        System.out.println("Задача 11: среднее арифметическое = " + average);
        return average;
    }

    /**
     * Задача 12. Создаёт новый список без указанного имени.
     */
    public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
        if (list == null) {
            throw new IllegalArgumentException("List must not be null");
        }
        List<String> result = new ArrayList<>();
        for (String name : list) {
            if (nameToRemove == null ? name != null : !nameToRemove.equals(name)) {
                result.add(name);
            }
        }
        System.out.println("Задача 12: список после удаления имени \""
                + nameToRemove + "\" = " + result);
        return result;
    }
}
