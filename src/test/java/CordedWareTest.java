import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class CordedWareTest extends BaseTest {

    @Test
    public void testTittleAndUrlWhenGuideMenu_HappyPath_Test() throws InterruptedException{

        // AAA
        // Arrange
        String url = "https://openweathermap.org/";
        String expectedResultUrl = "https://openweathermap.org/guide";
        String expectedResultTitle = "OpenWeatherMap API guide - OpenWeatherMap";

        // Act
        getDriver().get(url);
        Thread.sleep(10000);

        WebElement selectButtonMenu = getDriver().findElement(
                By.xpath("//a[@href = '/guide']")
        );
        selectButtonMenu.click();
        Thread.sleep(10000);

        String actualResultUrl = getDriver().getCurrentUrl();
        String actualResultTitle = getDriver().getTitle();

        // Assert
        Assert.assertEquals(actualResultUrl, expectedResultUrl);
        Assert.assertEquals(actualResultTitle, expectedResultTitle);

        getDriver().quit();
    }

    @Test
    public void testTemperatureForCity_HappyPath_Test() throws InterruptedException {

        // AAA
        // Arrange
        String url = "https://openweathermap.org/";
        String fSymbol = "°F";
        String expectedResult = "°F";

        // Act
        getDriver().get(url);
        Thread.sleep(10000);

        WebElement searchButtonWithTemperature = getDriver().findElement(
                By.xpath("//div[3][@class = 'option']")
        );
        searchButtonWithTemperature.click();
        Thread.sleep(10000);

        WebElement checkTemperatureHeader = getDriver().findElement(
                By.xpath("//div[@class = 'current-container mobile-padding']/div[2]/div/span[@class = 'heading']")
        );
        checkTemperatureHeader.click();
        Thread.sleep(10000);

        String actualResult = checkTemperatureHeader.getText().substring(checkTemperatureHeader.getText().length()-2);

        // Assert
        Assert.assertEquals(actualResult, expectedResult);

        getDriver().quit();
    }
}
