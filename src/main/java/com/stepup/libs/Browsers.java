package com.stepup.libs;

public enum Browsers {
    FIREFOX("Firefox"),
    OPERA("OPR"),
    EDGE("Edg"),
    CHROME("Safari","Chrome"),
    SAFARI("Safari","Version"),
    UNIDENTIFIED(null);

    public String[] codes;

    Browsers(String... codes) {
        this.codes = codes;
    }
}
