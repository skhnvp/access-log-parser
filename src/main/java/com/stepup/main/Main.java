package com.stepup.main;

import com.stepup.parse.LogEntry;
import com.stepup.exc.tooBigLine;
import com.stepup.stat.Statistics;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
В этом модуле вы познакомились с различными типами коллекций, используемыми в языке программирования Java, изучили их отличия и основной функционал. Чтобы закрепить полученные знания, предлагаем вам выполнить это задание.

●     Продолжайте работать в проекте AccessLogParser в ветке master.

●     В классе Statistics реализуйте метод, который будет возвращать список всех существующих страниц сайта. Для этого создайте в классе переменную класса HashSet<String>. В эту переменную при выполнении метода addEntry добавляйте адреса существующих страниц (с кодом ответа 200) сайта.

●     В классе Statistics также реализуйте метод, который будет возвращать статистику операционных систем пользователей сайта.

Для этого создайте в классе переменную класса HashMap<String, Integer>, в которой подсчитывайте частоту встречаемости каждой операционной системы.
При выполнении метода addEntry проверяйте, есть ли в этом HashMap запись с такой операционной системой. Если нет, вставляйте такую запись. Если есть, добавляйте к соответствующему значению единицу. В итоге получится HashMap, ключи которого будут названиями операционных систем, а значения — их количествами в лог-файле.
Метод в результате должен создавать новый HashMap<String, Double> и в качестве ключей рассчитывать долю для каждой операционной системы (от 0 до 1). Чтобы рассчитать долю конкретной операционной системы, нужно разделить количество конкретной операционной системы на общее количество для всех операционных систем.
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
        System.out.println("Доли операционных систем: " + Statistics.statSystems());

        //System.out.println("Список всех существующих страниц: " + Statistics.getAllUrls());



    }
}
