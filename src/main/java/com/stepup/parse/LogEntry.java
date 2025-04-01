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

        //Example
        // "44.135.240.229 - - [25/Sep/2022:06:25:08 +0300] \"GET /housekeeping/?lg=2&p=506&rss=1&t=2 HTTP/1.0\" 200 1368 \"https://rosinform.ru/rubric/top/maks2015/\" \"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36 Edge/18.18362\"";
        Pattern pattern = Pattern.compile("^(.+) \\S \\S \\[(.+?)\\] \"(\\w{3,4}) (.+?) HTTP/\\d\\.\\d\" (\\d{3}) (\\d+) \"(.+|-)\" \"(.+|-)\"$");
        /*
         * Ради интереса научился немного работать с регуляркой
         * По сути разделил строку так же на пробелы
         * Выделил важные мне блоки () и указал что мне не важно содержимое используя .+ и .+|- (если блок пустой "-")
         */

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

    public String getResponseCode() {
        return responseCode;
    }

    public int getTraffic() {
        return traffic;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return new UserAgent(this.userAgent.getSystemType(),this.userAgent.getBrowserType());
    }
}
