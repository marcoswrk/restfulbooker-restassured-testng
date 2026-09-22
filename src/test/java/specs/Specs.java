package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specs {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setRelaxedHTTPSValidation()
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .addHeader("User-Agent", USER_AGENT)
                .build();
    }

    public static ResponseSpecification okJson() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
}