package com.stepup;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Objects;

/*
Вы познакомились с основными универсальными методами объектов Java, присущими классу Object,
— toString(), hashCode() и equals(), а также с особенностями и способами сравнения объектов в Java.
Для закрепления полученных знаний предлагаем вам выполнить представленное ниже задание.

Примерное время выполнения 30 минут.

●     Выполняйте задание в том же проекте AccessLogParser в ветке master.

●     Удалите из вашего кода фрагменты, выводящие длины самой короткой и самой длинной строки в файле.
Сохраните в нём код, выбрасывающий исключение в случае, если в файле встретилась строка длиннее 1024 символов.

●     Напишите код, который будет разделять каждую строку на составляющие.
Описание составляющих находится во введении ко всем заданиям (раздел “Файл состоит из строк следующего вида”).

●     В рамках текущего задания вам потребуется работать с фрагментами “User-Agent”,
в которых содержится информация о браузерах или других программах, обращавшихся к веб-сайту.
Задача программы — определять долю запросов к сайту от двух самых популярных поисковых ботов — Googlebot и YandexBot.
Информация о ботах содержится во фрагменте, описывающем User-Agent, в следующем виде:

https://davtb-teachbase.api.eric.s3storage.ru/material_images/a8f74cabea1f80a65ba346740a3bbd7181b2b96a.png
●     Обработайте фрагмент User-Agent следующим образом:

выделите часть, которая находится в первых скобках;
разделите эту часть по точке с запятой:
String[] parts = firstBrackets.split(";");
if (parts.length >= 2) {
   String fragment = parts[1];
}
очистьте от пробелов каждый получившийся фрагмент;
возьмите второй фрагмент;
отделите в этом фрагменте часть до слэша.
Получившийся фрагмент будет соответствовать используемой программе, которая производит запросы.

●     Определяя равенство найденного фрагмента строкам Googlebot или YandexBot,
подсчитывайте количество строк в файле, соответствующих запросам от данных ботов.

●     Выведите в консоль долю запросов от YandexBot и Googlebot к веб-сайту относительно общего числа сделанных запросов.

●     Сделайте коммит в ветку master вашего репозитория access-log-parser.
*/

public class Main {
    public static void main(String[] args) throws IOException {
        File f = new File("access.log"); // access 1024

        if (f.isFile()) {
            FileReader fileReader = new FileReader(f);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            char bracket = '(';
            char slash = '/';

            int counterYandexBot = 0;
            int counterGooglebot = 0;

            try {
                while ((line = reader.readLine()) != null) {
                    int length = line.length();

                    if (length >= 1024) { //выкидываем эксепшен если строка 1024 и более символов
                        throw new tooBigLine();
                    }

                    if ((line.contains("YandexBot") || line.contains("Googlebot")) && line.indexOf(bracket) != -1) { //проверяем что в строке есть текст YandexBot или Googlebot и открывающая скобка
                        String firstBrackets = line.substring(line.indexOf(bracket) + 1); //обрезаем всё до скобки
                        String[] parts = firstBrackets.split(";"); //нарезаем строку

                        if (parts.length >= 2) {
                            String fragment = parts[1];

                            if (fragment.indexOf(slash) != -1) {
                                fragment = fragment.substring(0, fragment.indexOf(slash)).trim(); //берем второе слово убираем пробелы и отсекаем слеш и всё после него

                                if (fragment.equals("YandexBot")) {
                                    counterYandexBot++;
                                }
                                if (fragment.equals("Googlebot")) {
                                    counterGooglebot++;
                                }

                            }
                        }
                    }
                }
                System.out.println("Запросов от YandexBot: " + counterYandexBot + "\nЗапросов от Googlebot: " + counterGooglebot);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } else {
            throw new FileNotFoundException("Файл не был найден");
        }

    }
}