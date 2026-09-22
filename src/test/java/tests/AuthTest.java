package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import api.AuthApi;

import static org.hamcrest.Matchers.equalTo;

public class AuthTest extends BaseTest {

    @Test
    public void validAuth() {
        AuthApi.authenticate("admin", "password123")
                .then()
                .statusCode(200)
                .body("token", org.hamcrest.Matchers.notNullValue());
    }

    @Test
    public void invalidAuth() {
        AuthApi.authenticate("admin", "wrongpassword")
                .then()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }
}