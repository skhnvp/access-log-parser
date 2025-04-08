package com.stepup.parse;

import com.stepup.libs.Browsers;
import com.stepup.libs.Systems;

import ua_parser.Client;
import ua_parser.Parser;

import java.util.Arrays;
import java.util.HashSet;

public class UserAgent {
    private final Systems systemType;
    private final Browsers browserType;
    private static final Parser uaParser = new Parser();

    public UserAgent(String userAgentLine) {
        Client client = uaParser.parse(userAgentLine); //программа работает очень долго

        browserType = checkBrowser(client.userAgent.family);
        systemType = checkSystem(client.os.family);

//        browserType = checkBrowser(userAgentLine); //для сравнения если просто в строке искать значение
//        systemType = checkSystem(userAgentLine);
    }

    public UserAgent(Systems systemType, Browsers browserType) {
        this.systemType = systemType;
        this.browserType = browserType;
    }

    public Browsers checkBrowser(String browserStringFromAgentLine) {
        if (browserStringFromAgentLine.equals("Other")) {
            return Browsers.OTHER;
        }

        return Arrays.stream(Browsers.values())
                .filter(browser -> browserStringFromAgentLine.toLowerCase().contains(browser.code))
                .findFirst()
                .orElse(Browsers.OTHER);
    }

    public Systems checkSystem(String systemStringFromAgentLine) {
        if (systemStringFromAgentLine.equals("Other")) {
            return Systems.OTHER;
        }

        return Arrays.stream(Systems.values())
                .filter(system -> systemStringFromAgentLine.toLowerCase().contains(system.code))
                .findFirst()
                .orElse(Systems.OTHER);
    }

    public Systems getSystemType() {
        return Systems.valueOf(systemType.name());
    }

    public Browsers getBrowserType() {
        return Browsers.valueOf(browserType.name());
    }
}
