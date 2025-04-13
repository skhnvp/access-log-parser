package com.stepup.libs;

public enum Systems {
    BOT("bot"),
    WINDOWS("windows"),
    MACINTOSH("mac os x"),
    IPHONE("ios"),
    ANDROID("android"),
    LINUX("linux"),
    OTHER("other");

    public String code;

    Systems(String code) {
        this.code = code;
    }
}
