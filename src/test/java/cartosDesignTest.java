import DataProviders.navBarData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class cartosDesignTest {
    private WebDriver driver;
    private String baseUrl = "https://www.cartosdesign.com/";
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver","C:/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.navigate().to(baseUrl);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void checkTitleTest() {
        Assert.assertEquals(driver.getTitle(), "Cartos Design", "Assert that current page title matches \"Cartos Design\"");
    }

    @Test
    public void h1TagTest() {
        String h1Text = driver.findElement(By.tagName("h1")).getText();
        Assert.assertEquals(h1Text, "cartos", "Assert that <h1> text matches \"cartos\"");
    }

    @Test(dataProvider = "NavBarData", dataProviderClass = navBarData.class)
    public void navBarTest(String pageTitle, String navBtnId) {
        WebElement featuresBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(navBtnId)));
        featuresBtn.click();
        Assert.assertEquals(driver.getTitle(), pageTitle, "Assert that \""+navBtnId.toUpperCase()+"\" nav button navigates to \""+navBtnId+"\" page");
    }

    @Test
    public void navToContact() {
        driver.navigate().to(baseUrl + "/contact");
        String title = driver.getTitle();
        Assert.assertEquals(title, "Contact Us");
    }

    @AfterClass
    public void endTest() {
        driver.quit();
    }

}
