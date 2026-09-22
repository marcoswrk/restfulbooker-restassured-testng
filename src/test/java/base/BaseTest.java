package base;

import org.testng.annotations.BeforeSuite;

public class BaseTest {

    static {
        System.setProperty("jsse.enableSNIExtension", "false");
    }

    @BeforeSuite
    public void setupSuite() {

        System.setProperty("jsse.enableSNIExtension", "false");
    }
}