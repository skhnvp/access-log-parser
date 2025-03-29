package com.stepup;

public class tooBigLine extends RuntimeException {
    public tooBigLine() {
        super("Found too big line!");
    }
}
