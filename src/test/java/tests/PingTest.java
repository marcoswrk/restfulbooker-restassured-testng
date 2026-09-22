package tests;

import org.testng.annotations.Test;
import base.BaseTest;
import specs.Specs;

import static io.restassured.RestAssured.*;

public class PingTest extends BaseTest {

    @Test
    public void getPing() {
        given()
                .spec(Specs.requestSpec())
                .header("Accept", "*/*")
                .when()
                .get("/ping")
                .then()
                .statusCode(201);
    }
}