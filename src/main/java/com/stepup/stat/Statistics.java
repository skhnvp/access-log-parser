package com.stepup.stat;

import com.stepup.libs.Systems;
import com.stepup.parse.LogEntry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Statistics() {
    }


    public static void addEntry(LogEntry le) {
        totalTraffic += le.getTraffic();

        if (rps.containsKey(le.getTimestamp())
                && le.getUserAgent() != null
                && le.getUserAgent().getSystemType() != Systems.UNIDENTIFIED
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
            if (mapSystems.containsKey(le.getUserAgent().getSystemType().toString())) {
                mapSystems.put(le.getUserAgent().getSystemType().toString(), mapSystems.get(le.getUserAgent().getSystemType().toString()) + 1);
            } else {
                mapSystems.put(le.getUserAgent().getSystemType().toString(), 1);
            }

            if (mapBrowsers.containsKey(le.getUserAgent().getBrowserType().toString())) {
                mapBrowsers.put(le.getUserAgent().getBrowserType().toString(), mapBrowsers.get(le.getUserAgent().getBrowserType().toString()) + 1);
            } else {
                mapBrowsers.put(le.getUserAgent().getBrowserType().toString(), 1);
            }

            if (!le.getUserAgent().getSystemType().equals(Systems.BOT)
                    || !le.getUserAgent().getSystemType().equals(Systems.UNIDENTIFIED)) {
                countNotUnicUsers++;
                if (mapUnicUsers.containsKey(le.getIp())) { //
                    mapUnicUsers.put(le.getIp(), mapUnicUsers.get(le.getIp()) + 1);
                } else {
                    mapUnicUsers.put(le.getIp(), 1);
                }


                //setUsers.add();
            }
        }
        if (le.getReferer() != null) {
            Pattern pattern = Pattern.compile("^https?://([^/]+)");
            Matcher matcher = pattern.matcher(le.getReferer());
            if (matcher.find()) {
                setRefers.add(matcher.group(1));
            }
        }
    }

    public static Map<String, Double> getStatistics(Map<String, Integer> map) {
        //Общий метод подсчитывания долей
        double sum = 0;
        Map<String, Double> statMap = new HashMap<>();

        for (Integer i : map.values()) {
            sum += i;
        }

        for (String s : map.keySet()) {
            statMap.put(s, Math.round((map.get(s) / sum) * 100) / 100.);
        }

        return statMap;
    }

    public static long countAvgPerHour(long count, LocalDateTime minTime, LocalDateTime maxTime) {
        //Общий метод подсчитывания среднего кол-ва countable переменных за время
        return count / Duration.between(minTime, maxTime).toHours();
    }

    public static long countAvgOneUserReq() {
        //Метод расчёта средней посещаемости одним пользователем.
        return countNotUnicUsers / mapUnicUsers.size();
    }

    public static Optional<Integer> getMax(Collection<Integer> map) {
        //метод для возврата максимального значения стрима
        return map.stream().max(Comparator.naturalOrder());
    }

    public static Set<String> getAllRefers() {
        return new HashSet<>(setRefers);
    }

    public static Map<String, Integer> getMapUnicUsers() {
        return new HashMap<>(mapUnicUsers);
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

    public static long getTrafficRate() {
        long hours = Duration.between(minTime, maxTime).toHours();
        return totalTraffic / hours;
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
