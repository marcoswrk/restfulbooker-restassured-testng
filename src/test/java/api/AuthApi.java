package api;

import io.restassured.response.Response;
import specs.Specs;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthApi {

    public static String generateToken() {
        return given(Specs.withJsonBody())
                .body(Map.of("username", "admin", "password", "password123"))
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract().path("token");
    }

    public static Response authenticate(String username, String password) {
        return given(Specs.withJsonBody())
                .body(Map.of("username", username, "password", password))
                .when()
                .post("/auth");
    }
}