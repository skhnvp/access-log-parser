package com.stepup;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Введите первое число:");
        int firstInputNum = s.nextInt();

        System.out.println("Введите второе число:");
        int secondInputNum = s.nextInt();

        s.close();

        int sum = firstInputNum + secondInputNum;
        int diff = firstInputNum - secondInputNum;
        int multi = firstInputNum * secondInputNum;
        double div = (double) firstInputNum / secondInputNum;

        System.out.print("\nСумма чисел: " + sum +
                "\nРазница чисел: " + diff +
                "\nПроизведение чисел: " + multi +
                "\nРезультат деления чисел: " + div
        );

    }
}