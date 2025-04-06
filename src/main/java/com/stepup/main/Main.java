package com.stepup.main;

import com.stepup.parse.LogEntry;
import com.stepup.exc.tooBigLine;
import com.stepup.stat.Statistics;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
В рамках этого модуля вы изучили все основные аспекты работы со Stream API.
Теперь пора применить полученные знания на практике! Предлагаем вам для этого выполнить задание.

●     Продолжайте работать в проекте AccessLogParser в ветке master.

●     В классе Statistics реализуйте:

● Метод подсчёта среднего количества посещений сайта за час.
1. Разделите количество посещений пользователей на период времени в часах, за который имеются записи в логе.
2. В расчёте должны участвовать только обращения к сайту через обычные браузеры (не боты).
Бота можно распознать по слову “bot” внутри описания User-Agent.
Для такого подсчёта создайте отдельное свойство (поле) в классе.

● Метод подсчёта среднего количества ошибочных запросов в час.
1. Разделите период времени в часах, за который имеются записи в логе,
на количество запросов, по которым был ошибочный код ответа (4xx или 5xx).
2. Для подсчёта ответов с ошибочными кодами создайте в классе специальную переменную.
Добавляйте в неё единицу в методе addEntry,
если в него передана строка с информацией о запросе с ошибочным кодом ответа.

● Метод расчёта средней посещаемости одним пользователем.
1. Одним пользователем считается пользователь с одним и тем же IP-адресом, не являющийся ботом.
2. Для расчёта можно разделить общее количество посещений реальными пользователями (не ботами)
на число уникальных IP-адресов таких пользователей.

●     Сделайте коммит в ветку master вашего репозитория access-log-parser.
*/

public class Main {
    public static void main(String[] args) throws IOException {
        File f = new File("access.log"); // access 1024


        if (f.isFile()) {
            FileReader fileReader = new FileReader(f);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    if (line.length() >= 1024) { //выкидываем эксепшен если строка 1024 и более символов
                        throw new tooBigLine();
                    }

                    LogEntry le = new LogEntry(line);
                    Statistics.addEntry(le);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } else {
            throw new FileNotFoundException("Файл не был найден");
        }
        System.out.println("TotalTraffic: " + Statistics.getTotalTraffic());
        System.out.println("MinTime: " + Statistics.getMinTime());
        System.out.println("MaxTime: " + Statistics.getMaxTime());

        System.out.println(Statistics.getTrafficRate());

        System.out.println("Частота встречаемости каждой операционной системы: " + Statistics.getAllSystems());
        System.out.println("Доли операционных систем: " + Statistics.getStatistics(Statistics.getAllSystems()));

        System.out.println("Частота встречаемости каждого браузера: " + Statistics.getAllBrowsers());
        System.out.println("Доли каждого браузера: " + Statistics.getStatistics(Statistics.getAllBrowsers()));

        System.out.println("Подсчёт среднего количества посещений сайта за час: " + Statistics.countAvgPerHour(Statistics.getCountUsers(),Statistics.getMinTime(),Statistics.getMaxTime()));
        System.out.println("Подсчёт среднего количества ошибочных запросов в час: " + Statistics.countAvgPerHour(Statistics.getCountFailureReq(),Statistics.getMinTime(),Statistics.getMaxTime()));
        System.out.println("Расчёт средней посещаемости одним пользователем: " + Statistics.countAvgOneUserReq());

        //System.out.println("Список всех существующих страниц: " + Statistics.getAllUrls());



    }
}
