package com.stepup.main;

import com.stepup.parse.LogEntry;
import com.stepup.exc.tooBigLine;
import com.stepup.stat.Statistics;

import java.io.*;

/*
В рамках этого модуля вы изучили все основные аспекты работы со Stream API.
Теперь пора применить полученные знания на практике! Предлагаем вам для этого выполнить задание.
●     Продолжайте работать в проекте AccessLogParser в ветке master.
●     В классе Statistics реализуйте:

● Метод расчёта пиковой посещаемости сайта (в секунду).
1. Для расчёта нужно фиксировать количество посещений за одну каждую секунду,
и затем метод должен вернуть максимальное количество посещений для какой-то одной секунды.
Можно для этого использовать HashMap<Integer, Integer>, где в качестве ключей должны быть отдельные секунды,
а в качестве значений — количества посещений в эту секунду.
2. В расчёте должны участвовать только обращения к сайту через обычные браузеры (не боты).
Бота можно распознать по слову “bot” внутри описания User-Agent.
Для такого подсчёта создайте отдельное свойство (поле) в классе.

● Метод, возвращающий список сайтов, со страниц которых есть ссылки на текущий сайт.
1. Для получения данного списка собирайте домены для всех referer-ов в HashSet<String>.
Причём, важно собирать именно адреса доменов.
К примеру, для referer: https://nova-news.ru/wp-login.php, доменное имя будет: nova-news.ru

● Метод расчёта максимальной посещаемости одним пользователем.
1. Одним пользователем считается пользователь с одним и тем же IP-адресом, не являющийся ботом.
2. Для расчёта нужно для каждого уникального пользователя (не бота) посчитать количество посещений,
и затем взять максимальное количество из всех пользователей.

● Сделайте коммит в ветку master вашего репозитория access-log-parser.
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

        System.out.println("Подсчёт среднего количества посещений сайта за час: " + Statistics.countAvgPerHour(Statistics.getCountNotUnicUsers(),Statistics.getMinTime(),Statistics.getMaxTime()));
        System.out.println("Подсчёт среднего количества ошибочных запросов в час: " + Statistics.countAvgPerHour(Statistics.getCountFailureReq(),Statistics.getMinTime(),Statistics.getMaxTime()));
        System.out.println("Расчёт средней посещаемости одним пользователем: " + Statistics.countAvgOneUserReq());

        //System.out.println("Список всех существующих страниц: " + Statistics.getAllUrls());

        System.out.println("Метод расчёта пиковой посещаемости сайта (в секунду). Наибольшее кол-во запросов в секунду: " + Statistics.getMaxFromMapValues(Statistics.getRps().values()).get());
        System.out.println("Метод, возвращающий список сайтов, со страниц которых есть ссылки на текущий сайт: " + Statistics.getAllRefers());
        System.out.println("Метод расчёта максимальной посещаемости одним пользователем: " + Statistics.getMaxFromMapValues(Statistics.getMapUnicUsers().values()).get());
    }
}
