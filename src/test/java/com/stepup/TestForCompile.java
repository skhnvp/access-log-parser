package com.stepup;

import com.stepup.parse.UserAgent;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

@ExtendWith(EducationExt.class)
public class TestForCompile {
    @BeforeEach
    public void first(){
        System.out.println("Started");
    }
    @AfterEach
    public void last(){
        System.out.println("Ended");
    }

    @RepeatedTest(value = 4, name = "Название")
    public void myFirstTest(){
        int i = 2;
        int j = 1;

        System.out.println(i+" "+j);

        Assertions.assertNotEquals(i,j);

    }


    @ParameterizedTest(name = "Название3")
    @MethodSource("com.stepup.Generator#ints")
    public void myThirdTest(int x){
        Assertions.assertNotEquals(x,x);
    }

    @Test
    @DisplayName("Русское название")
    public void mySecondTest(){
        int i = 1;
        int j = 1;

        //Assertions.assertNotEquals(i,j);

        UserAgent ua = new UserAgent("109.195.139.199        - - [25/Sep/2022:06:25:14 +0300] \"GET /housekeeping/?rss=1&p=53&lg=1 HTTP/1.0\" 200 2514 \"https://www.nova-news.ru//cooking/?rss=1&p=53&lg=1\"  sdf \"Mozilla/5.0 (iPad; CPU OS 9_3_4 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) GSA/17.1.129017588 Mobile/13G35 Safari/600.1.4");

        Assertions.assertThrows(IllegalArgumentException.class, () -> ua.getSystemType());
    }

}
class EducationExt implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        System.out.println("text extencion");
    }
}