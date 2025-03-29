package com.stepup;

import java.io.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/*
Вы познакомились с понятием исключения и с тем, как осуществляется обработка исключений в Java-приложениях.
Предлагаем вам выполнить практическое задание, чтобы закрепить полученные знания на практике.
Выполняйте задание последовательно по шагам.

Примерное время выполнения 30 минут.

●     Выполняйте задание в том же проекте AccessLogParser в ветке master.
●     После проверки существования файла и того, что указанный путь является путём именно к файлу,
а не к папке, напишите код, который будет построчно читать указанный файл:

FileReader fileReader = new FileReader(path);
BufferedReader reader =
   new BufferedReader(fileReader);
String line;
while ((line = reader.readLine()) != null) {
   int length = line.length();
}
●     Поскольку в данном коде есть целых два места, требующих обязательной обработки исключений,
поместите этот код внутрь конструкции try…catch, внутри которой пропишите обработку всех исключений:

try {
   // code here
} catch (Exception ex) {
   ex.printStackTrace();
}
●     Для проверки кода используйте файл реального access-лога.
●     Допишите самостоятельно код таким образом, чтобы он по итогам выполнения программы выводил:

общее количество строк в файле;
длину самой длинной строки в файле;
длину самой короткой строки в файле
●   Допишите код таким образом, чтобы он прекращал своё выполнение (выбрасывал исключение) в случаях,
если в файле встретилась строка длиннее 1024 символов. Создайте для данного исключения собственный
класс исключения или объект класса RuntimeException,
при создании которого в конструктор передайте понятное сообщение об ошибке.

●     Сделайте коммит в ветку master вашего репозитория access-log-parser.
*/

public class Main {
    public static void main(String[] args) throws IOException {
        File f = new File("access.log"); // access 1024

        if (f.isFile()) {
            FileReader fileReader = new FileReader(f);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;

            int minLength = 0, maxLength = 0, countLines = 0;

            try {
                while ((line = reader.readLine()) != null) {
                    int length = line.length();

                    if (length >= 1024) {
                        throw new tooBigLine();
                    }

                    if (countLines == 0 || minLength > length) {
                        minLength = length;
                    }
                    if (countLines == 0 || maxLength < length) {
                        maxLength = length;
                    }

                    countLines++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.printf("Общее количество строк в файле: %d" +
                    "\nДлина самой длинной строки в файле: %d" +
                    "\nДлина самой короткой строки в файле: %d", countLines, maxLength, minLength);

        } else {
            throw new FileNotFoundException("Файл не был найден");
        }

    }
}