package tests;
import org.testng.annotations.Test;
import base.BaseTest;
import net.minidev.json.JSONObject;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;
import api.AuthApi;


public class AuthTest extends BaseTest {
    // Valida que a autenticação com usuário e senha corretos retorna o status code 200
    @Test
    public void putAuth() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "admin");
        requestParams.put("password", "password123");
        given().
        contentType("application/json").
        body(requestParams.toJSONString()).
        when().
            post("/auth").
        then().
            statusCode(200);
    }

    @Test
    public void putAuthInvalid() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "admin");
        requestParams.put("password", "wrongpassword");
        given().
        contentType("application/json").
        body(requestParams.toJSONString()).
        when().
            post("/auth").
        then().
            statusCode(200)
            .body("reason", equalTo("Bad credentials"));
    }
}