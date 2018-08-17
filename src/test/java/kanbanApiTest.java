
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class kanbanApiTest {
    Properties prop = new Properties();
    String baseUrl;
    String generatedString;
    String sessionId;
    String boardId;

    @BeforeClass()
    public void setup() throws IOException {
        FileInputStream kanbanProps = new FileInputStream("C:\\Users\\ramsey.bland\\IdeaProjects\\learningselenium\\src\\test\\java\\Files\\kanban.properties");
        prop.load(kanbanProps);
        baseUrl = prop.getProperty("KANBAN_API_URL");
        RestAssured.baseURI = baseUrl;
        generatedString = RandomStringUtils.randomAlphanumeric(17);
    }

    @Test
    public void loginTest() {
        sessionId = given().
                contentType(ContentType.JSON).
                body(prop.getProperty("KANBAN_LOGIN")).
                when().
                post("/login").
                then().log().all().
                assertThat().
                body("message", equalTo("successfully logged in")).
                body("data._id", equalTo(prop.getProperty("KANBAN_USER_ID"))).
                extract().cookie("connect.sid");
    }

    @Test(priority = 1)
    public void createBoardTest() {
        boardId = given().
                contentType(ContentType.JSON).
                header("Cookie", "connect.sid="+sessionId).
                body("{\n" +
                        "    \"title\": \"test-"+generatedString+"\",\n" +
                        "    \"description\": \"board created by automated test\",\n" +
                        "    \"author\": \""+prop.getProperty("KANBAN_USER_DISPLAYNAME")+"\"\n" +
                        "}").log().all().
                when().
                post("/board").
                then().log().all().
                assertThat().
                statusCode(200).
                body("userId", equalTo(prop.getProperty("KANBAN_USER_ID"))).
                body("author", equalTo(prop.getProperty("KANBAN_USER_DISPLAYNAME"))).
                body("title", equalTo("test-"+generatedString)).
                extract().path("_id");
        System.out.println(sessionId);
    }
}
