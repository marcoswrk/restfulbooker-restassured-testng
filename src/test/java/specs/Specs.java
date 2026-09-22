package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specs {

    private Specs() {}

    public static RequestSpecification base() {
        return new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .setAccept(ContentType.JSON)
                .addHeader("User-Agent", "restfulbooker-portfolio-tests/1.0")
                .log(LogDetail.URI)
                .build();
    }

    public static RequestSpecification withJsonBody() {
        return new RequestSpecBuilder()
                .addRequestSpecification(base())
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification authenticated(String token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(base())
                .addCookie("token", token)
                .build();
    }

    public static RequestSpecification authenticatedWithJsonBody(String token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(withJsonBody())
                .addCookie("token", token)
                .build();
    }

    public static ResponseSpecification okJson() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
}