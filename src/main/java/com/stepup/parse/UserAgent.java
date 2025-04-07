package com.stepup.parse;

import com.stepup.libs.Browsers;
import com.stepup.libs.Systems;

public class UserAgent {
    private final Systems systemType;
    private final Browsers browserType;

    public UserAgent(String userAgentLine) {
        browserType = checkBrowser(userAgentLine);
        systemType = checkSystem(userAgentLine);
    }

    public UserAgent(Systems systemType, Browsers browserType) {
        this.systemType = systemType;
        this.browserType = browserType;
    }

    public Browsers checkBrowser(String userAgentLine) {
        for (Browsers browser : Browsers.values()) {
            if (browser.codes == null) {
                continue;
            }

            int checkCodes = 0;

            for (String code : browser.codes) {
                if (userAgentLine.contains(code)) {
                    checkCodes++;
                }
            }

            if (checkCodes == browser.codes.length) {
                return browser;
            }
        }

        return Browsers.UNIDENTIFIED;
    }

    public Systems checkSystem(String userAgentLine) {
        for (Systems system : Systems.values()) {
            if (system.code == null) {
                continue;
            }

            if (userAgentLine.contains(system.code)) {
                return system;
            }
        }

        return Systems.UNIDENTIFIED;
    }

    public Systems getSystemType() {
        return Systems.valueOf(systemType.name());
    }

    public Browsers getBrowserType() {
        return Browsers.valueOf(browserType.name());
    }
}
