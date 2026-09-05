package tests;
import org.testng.annotations.Test;
import base.BaseTest;

import static io.restassured.RestAssured.*;


public class PingTest extends BaseTest {
      @Test
    public void getPing() {
        get ("/ping").then().statusCode(201).log().all();
        
    }

}
