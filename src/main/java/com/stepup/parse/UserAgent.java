package com.stepup.parse;

import com.stepup.libs.Browsers;
import com.stepup.libs.Systems;

public class UserAgent {
    private final Systems systemType;
    private final Browsers browserType;

    public UserAgent(String userAgentLine) {

        //Example
        //"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36 Edge/18.18362";

        /*
        * Тут уже не смог осилить регулярку, из за того что этот блок слишком динамичный
        * Возникли сложности при определении браузера
        * */

        /*
        https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/User-Agent

        Firefox UA string
        Mozilla/5.0 (platform; rv:gecko-version) Gecko/gecko-trail Firefox/firefox-version
        Examples
        Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0
        Mozilla/5.0 (Macintosh; Intel Mac OS X x.y; rv:42.0) Gecko/20100101 Firefox/42.0

        Chrome UA string
        Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36

        Opera UA string
        Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36 OPR/38.0.2220.41

        Microsoft Edge UA string
        Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.59

        Safari UA string
        Mozilla/5.0 (iPhone; CPU iPhone OS 13_5_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1.1 Mobile/15E148 Safari/604.1

        Crawler and bot UA strings
        Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)
        Mozilla/5.0 (compatible; YandexAccessibilityBot/3.0; +http://yandex.com/bots)
        */

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
