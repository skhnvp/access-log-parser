package com.stepup.libs;

public enum Systems {
    BOT("Bot"),
    WINDOWS("Windows"),
    MACINTOSH("Macintosh"),
    IPHONE("iPhone"),
    ANDROID("Android"),
    LINUX("X11"),
    UNIDENTIFIED(null);

    public String code;

    Systems(String code) {
        this.code = code;
    }
}
