package com.stepup.main;

import com.stepup.parse.LogEntry;
import com.stepup.exc.tooBigLine;
import com.stepup.stat.Statistics;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
Вы познакомились с основными понятиями и принципами объектно-ориентированного программирования в языке Java.
В рамках этого задания вам предстоит применить полученные знания на практике и тем самым закрепить их.

Примерное время выполнения 2 часа 30 минут.

●     Выполняйте задание в том же проекте AccessLogParser в ветке master.
●     В предыдущем задании вы писали код, который “разбирает” строку из лог-файла на составляющие.
В этом задании вам необходимо разработать класс LogEntry, объекты которого будут соответствовать строкам из лог-файла,
а свойства (поля) — отдельным частям каждой такой строки.

●     Создайте класс LogEntry со свойствами (полями), соответствующими компонентам строк лог-файла:
IP-адресу,
дате и времени запроса,
методу запроса,
пути запроса,
коду ответа,
размеру отданных сервером данных,
referer,
а также User-Agent.
Возможные методы HTTP-запросов положите в enum. Типы остальных полей определите самостоятельно.

●     Для всех созданных в классе LogEntry свойств (полей) создайте геттеры,
а сами свойства (поля) пометьте ключевым словом final.
●     Создайте в классе LogEntry конструктор, который будет принимать в качестве единственного параметра строку,
разбирать её на составляющие и устанавливать значения всех свойств (полей) класса.
●     Создайте класс UserAgent по тому же принципу: с final-свойствами (полями), соответствующими свойствам,
заданным в строке User-Agent (см. ниже), и геттерами для этих свойств.
●     Создайте также в классе UserAgent конструктор, который будет принимать в качестве параметра строку User-Agent
и извлекать из неё свойства. Из строки User-Agent необходимо извлекать два свойства:
тип операционной системы (Windows, macOS или Linux) и браузера (Edge, Firefox, Chrome, Opera или другой).
Для определения типа операционной системы и браузера воспользуйтесь инструкцией.

●     Создайте класс для расчётов статистики — Statistics.
У этого класса должен быть конструктор без параметров, в котором должны инициализироваться переменные класса.
●     Добавьте в класс Statistics метод addEntry, принимающий в качестве параметра объект класса LogEntry.

●     Реализуйте в классе Statistics подсчёт среднего объёма трафика сайта за час. Для этого:
- создайте у класса свойство (поле) int totalTraffic, в которое в методе addEntry добавляйте объём данных, отданных сервером;
- создайте свойства (поля) minTime и maxTime класса LocalDateTime и заполняйте их в методе addEntry,
если время в добавляемой записи из лога меньше minTime или больше maxTime соответственно;
- реализуйте в классе метод getTrafficRate, в котором вычисляйте разницу между
maxTime и minTime в часах и делите общий объём трафика на эту разницу.
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

    }
}