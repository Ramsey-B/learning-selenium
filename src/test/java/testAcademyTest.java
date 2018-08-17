import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class testAcademyTest {
    public WebDriver driver;
    public String testUrl = "http://swtestacademy.com/";

    @BeforeMethod
    public void setupTest() {
        System.setProperty("webdriver.chrome.driver","C:/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.navigate().to(testUrl);
    }

    @Test
    public void firstTest() {
        String title = driver.getTitle();
        System.out.println("Page Title: " + title);
        Assert.assertEquals(title, "Software Test Academy", "Title Assertion Failed!");
    }

    @AfterMethod
    public void endTest() {
        driver.quit();
    }
}
