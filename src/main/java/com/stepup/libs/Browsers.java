package com.stepup.libs;

public enum Browsers {
    FIREFOX("firefox"),
    OPERA("opera"),
    EDGE("edge"),
    CHROME("chrome"),
    SAFARI("safari"),
    OTHER("other");

    public String code;

    Browsers(String code) {
        this.code = code;
    }
}
