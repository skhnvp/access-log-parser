package com.stepup.stat;

import com.stepup.libs.Systems;
import com.stepup.parse.LogEntry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Statistics {
    private static long totalTraffic = 0;
    private static LocalDateTime minTime;
    private static LocalDateTime maxTime;
    private static Set<String> setUrls = new HashSet<>();
    private static Map<String, Integer> mapSystems = new HashMap<>();
    private static Set<String> setNotFoundedUrls = new HashSet<>();
    private static Map<String, Integer> mapBrowsers = new HashMap<>();
    private static long countNotUnicUsers = 0;
    private static long countFailureReq = 0;
    private static Map<LocalDateTime, Integer> rps = new HashMap<>();
    private static Set<String> setRefers = new HashSet<>();
    private static Map<String, Integer> mapUnicUsers = new HashMap<>();
    private static Pattern refererPattern = Pattern.compile("^https?://([^/]+)");
    public Statistics() {
    }


    public static void addEntry(LogEntry le) {
        totalTraffic += le.getTraffic();

        if (rps.containsKey(le.getTimestamp())
                && le.getUserAgent() != null
                && le.getUserAgent().getSystemType() != Systems.OTHER
                && le.getUserAgent().getSystemType() != Systems.BOT) {
            rps.put(le.getTimestamp(), rps.get(le.getTimestamp()) + 1);
        } else {
            rps.put(le.getTimestamp(), 1);
        }

        if (minTime == null || minTime.isAfter(le.getTimestamp())) {
            minTime = le.getTimestamp();
        }
        if (maxTime == null || maxTime.isBefore(le.getTimestamp())) {
            maxTime = le.getTimestamp();
        }
        if (le.getResponseCode() == 200) {
            setUrls.add(le.getUrl());
        } else if (le.getResponseCode() == 404) {
            setNotFoundedUrls.add(le.getUrl());
        }
        if (le.getResponseCode() != 200) {
            countFailureReq++;
        }
        if (le.getUserAgent() != null) {
            mapSystems.merge(le.getUserAgent().getSystemType().toString(), 1, Integer::sum); //значение 1 по умолчанию либо, либо Integer::sum которая объединяет старое и новое значение
            mapBrowsers.merge(le.getUserAgent().getBrowserType().toString(), 1, Integer::sum);

            if (!le.getUserAgent().getSystemType().equals(Systems.BOT)
                    || !le.getUserAgent().getSystemType().equals(Systems.OTHER)) {
                countNotUnicUsers++;
                mapUnicUsers.merge(le.getIp(), 1, Integer::sum);
            }
        }
        if (le.getReferer() != null) {

            Matcher matcher = refererPattern.matcher(le.getReferer());
            if (matcher.find()) {
                setRefers.add(matcher.group(1));
            }
        }
    }

    public static Map<String, Double> getStatistics(Map<String, Integer> map) {
        //Общий метод подсчитывания долей
        // Сумма всех значений
        double sum = map.values().stream()
                .mapToInt(Integer::intValue) //используем стрим примитивов для суммирования
                .sum();

        // Подсчёт долей с округлением до двух знаков после запятой
        return map.entrySet().stream() //делаем Set Map'ов для того чтобы было удобно доставать значения key/value
                .collect(Collectors.toMap( //и конвертируем обратно в Map
                        Map.Entry::getKey,
                        entry -> Math.round((entry.getValue() / sum) * 10000.0) / 100.0
                ));
    }

    public static double countAvgPerHour(long count, LocalDateTime minTime, LocalDateTime maxTime) {
        //Общий метод подсчитывания среднего кол-ва countable переменных за время
        return (double) count / Duration.between(minTime, maxTime).toHours();
    }

    public static double countAvgOneUserReq() {
        //Метод расчёта средней посещаемости одним пользователем.
        return (double) countNotUnicUsers / mapUnicUsers.size();
    }

    public static Optional<Integer> getMaxFromMapValues(Collection<Integer> valuesFromMap) {
        //метод для возврата максимального значения стрима
        return valuesFromMap.stream().max(Comparator.naturalOrder());
    }

    public static Set<String> getAllRefers() {
        return new HashSet<>(setRefers);
    }

    public static Map<String, Integer> getMapUnicUsers() {
        return new HashMap<>(mapUnicUsers);
    }

    public static long getTrafficRate() {
        long hours = Duration.between(minTime, maxTime).toHours();
        return totalTraffic / hours;
    }

    /// GETTERS SETTERS:

    public static Map<LocalDateTime, Integer> getRps() {
        return new HashMap<>(rps);
    }

    public static long getCountNotUnicUsers() {
        return countNotUnicUsers;
    }

    public static long getCountFailureReq() {
        return countFailureReq;
    }

    public static Map<String, Integer> getAllBrowsers() {
        return new HashMap<>(mapBrowsers);
    }

    public static Map<String, Integer> getAllSystems() {
        return new HashMap<>(mapSystems);
    }

    public static Set<String> getAllUrls() {
        return new HashSet<>(setUrls);
    }

    public static long getTotalTraffic() {
        return totalTraffic;
    }

    public static LocalDateTime getMinTime() {
        return minTime;
    }

    public static LocalDateTime getMaxTime() {
        return maxTime;
    }
}
