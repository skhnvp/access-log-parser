package com.stepup.parse;

import com.stepup.libs.MethodsHTTP;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {
    private final String ip;
    private final LocalDateTime timestamp;
    private final MethodsHTTP methodHTTP;
    private final String url;
    private final int responseCode;
    private final int traffic;
    private final String referer;
    private final UserAgent userAgent;

    public LogEntry(String line) {
        Pattern pattern = Pattern.compile("^(.+) \\S \\S \\[(.+?)\\] \"(\\w{3,4}) (.+?) HTTP/\\d\\.\\d\" (\\d{3}) (\\d+) \"(.+|-)\" \"(.+|-)\"$");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            this.ip = matcher.group(1);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(matcher.group(2), formatter); //для сохранения ЧП
            this.timestamp = zonedDateTime.toLocalDateTime();

            this.methodHTTP = MethodsHTTP.valueOf(matcher.group(3));
            this.url = matcher.group(4);
            this.responseCode = Integer.parseInt(matcher.group(5));
            this.traffic = Integer.parseInt(matcher.group(6));

            if(!matcher.group(7).equals("-")) {
                this.referer = matcher.group(7);
            } else {
                this.referer = null;
            }

            if(!matcher.group(8).equals("-")) {
                this.userAgent = new UserAgent(matcher.group(8));
            } else {
                this.userAgent = null;
            }
        } else {
            System.out.println("ERROR LINE: " + line);
            throw new RuntimeException("Ошибка регулярного выражения");
        }
    }

    public String getIp() {
        return ip;
    }

    public LocalDateTime getTimestamp() {
        return timestamp; //как я понял по доке он safe
    }

    public MethodsHTTP getMethodHTTP() {
        return methodHTTP;
    }

    public String getUrl() {
        return url;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getTraffic() {
        return traffic;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        if (userAgent == null) {
            return null;
        } else {
            return new UserAgent(this.userAgent.getSystemType(),this.userAgent.getBrowserType());
        }

    }
}
