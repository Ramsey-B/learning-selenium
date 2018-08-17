import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class githubTest {
    private WebDriver driver;
    String baseUrl = "https://ramsey-b.github.io/";
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.navigate().to(baseUrl);
        wait = new WebDriverWait(driver, 10);
    }

    @Test(priority = 1)
    public void verifyPageTest() {
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Ramsey Bland", "Assert page is \"Ramsey Bland's\" Github page");
    }

    @Test(priority = 2)
    public void navToKanzBanz() {
        WebElement kanbanBtn = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[2]/section/div[4]/div/div[2]/div[1]/div/div/a/button"));
        kanbanBtn.click();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        Assert.assertEquals(driver.getTitle(), "KanzBanz", "Assert that page title is \"KanzBanz\"");
    }

    @Test(priority = 3)
    public void loginTest() {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("email"))));
        WebElement passwordInput = driver.findElement(By.name("password"));
        emailInput.sendKeys("joe@email.com");
        passwordInput.sendKeys("joe");
        WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/form/button"));
        loginBtn.click();
        WebElement loggedIn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/nav/div[3]/div[2]/a[1]")));
        Assert.assertEquals(loggedIn.getText(), "Uzer: " + "joe", "Assert user logged in and name is displayed as \"joe\"");
    }

    @Test(priority = 4)
    public void createBoard() {
        WebElement addBoardBtn = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("addBoard"))));
        addBoardBtn.click();
        ArrayList<WebElement> inputs = new ArrayList<WebElement>(driver.findElements(By.id("b-title")));
        WebElement emailInput = inputs.get(0);
        WebElement descriptionInput = inputs.get(1);
        emailInput.sendKeys("Automated Test");
        descriptionInput.sendKeys("This is an automated test description");
        WebElement submitBtn = driver.findElement(By.xpath("//*[@id=\"boardForm\"]/button"));
        submitBtn.click();
    }


    @AfterClass
    public void endTest() {
        driver.quit();
    }
}
