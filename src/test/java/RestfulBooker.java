import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;


public class RestfulBooker extends BaseTest {
      @Test
    public void getPing() {
        get ("/ping").then().statusCode(201).log().all();
        
    }

}
