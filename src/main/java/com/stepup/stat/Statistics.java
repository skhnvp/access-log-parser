package com.stepup.stat;

import com.stepup.libs.MethodsHTTP;
import com.stepup.parse.LogEntry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Statistics {
    private static long totalTraffic = 0;
    private static LocalDateTime minTime;
    private static LocalDateTime maxTime;
    private static Set<String> setUrls = new HashSet<>();
    private static Map<String, Integer> mapSystems = new HashMap<>();
    private static Set<String> setNotFoundedUrls = new HashSet<>();
    private static Map<String, Integer> mapBrowsers = new HashMap<>();

    public Statistics() {
    }


    public static void addEntry(LogEntry le) {
        totalTraffic += le.getTraffic();

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
        }

    }

    public static Map<String, Double> getStatistics(Map<String, Integer> map) {
        double sum = 0;
        Map<String, Double> statMap = new HashMap<>();

        for (Integer i : map.values()) {
            sum += i;
        }

        for (String s : map.keySet()) {
            statMap.put(s, Math.round((map.get(s) / sum) * 100) / 100. );
        }

        return statMap;
    }

    public static Map<String, Integer> getAllBrowsers() {
        return new HashMap<>(mapBrowsers);
    }

    public static Set<String> getAllUrls() {
        return new HashSet<>(setUrls);
    }

    public static Map<String, Integer> getAllSystems() {
        return new HashMap<>(mapSystems);
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
