package api;

import static io.restassured.RestAssured.given;

import net.minidev.json.JSONObject;
import api.AuthApi;

public class AuthApi {

    public static String generateToken() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "admin");
        requestParams.put("password", "password123");
        return given().
                contentType("application/json").
                body(requestParams.toJSONString()).
                when().
                post("/auth").
                then().
                statusCode(200).
                extract().path("token");

    }
}
