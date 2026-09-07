package base;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.*;

public class BaseTest {
    @BeforeClass 
    public void setup() {
        baseURI = "https://restful-booker.herokuapp.com";
        requestSpecification =  new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }
}

