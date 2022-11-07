import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class CordedWareTest extends BaseTest {

    @Ignore
    @Test
    public void testTemperatureForCity_HappyPath_Test() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/home/cordedware/IdeaProjects/chromedriver");

        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        // AAA
        // Arrange
        String url = "https://openweathermap.org/";

        String expectedResult = "°F";

        // Act
        driver.get(url); // 1.
        Thread.sleep(6000);

        WebElement searchButtonWithTemperature = driver.findElement( // 2.
                By.xpath("//div[@class = 'switch-container']//div[3][@class = 'option']")
        );
        searchButtonWithTemperature.click();
        Thread.sleep(6000);

        WebElement checkTemperatureHeader = driver.findElement( // 3.
                By.xpath("//div[@class = 'current-container mobile-padding']/div[2]/div/span[@class = 'heading']")
        );
        checkTemperatureHeader.click();
        Thread.sleep(3000);
        String actualResult = checkTemperatureHeader.getText().substring(2); // .substring(2) находит только "°F" удаля 2 символа вначале

        // Assert
        Assert.assertEquals(actualResult, expectedResult);


        driver.quit();
    }

    @Test
    public void testHerokuApp() {
        getDriver().get("https:/formy-project.herokuapp.com/");

        WebElement link = getDriver().findElement(
                By.xpath("//li/a[@href='/autocomplete']")
        );
        Assert.assertEquals(link.getText(), "Autocomplete");
    }
}
