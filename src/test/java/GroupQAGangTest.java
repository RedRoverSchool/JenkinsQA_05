import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;

public class GroupQAGangTest extends BaseTest {

    @Test
    public void testText() {
        getDriver().get("https://koma.lux.pl/");
        WebElement link = getDriver().findElement(By.xpath("//*[@id=\"main-nav\"]/ul/li[2]/a"));
        Assert.assertEquals(link.getText(), "Strona główna");
    }

    @Test
    public void testDashboardLinkFromMainPage_WhenClick() {

        String url = "https://openweathermap.org/";
        String expectedResult = "Dashboard";

        getDriver().get(url);

        Boolean dynamicElement = new WebDriverWait(getDriver(), Duration.ofSeconds(60))
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[@class ='owm-loader-container']/div[@class='owm-loader']")));

        WebElement dashboardButton = getDriver().findElement(
                By.xpath("//div[@id='desktop-menu']//li/a[text() = 'Dashboard']"));

        dashboardButton.click();

        WebElement NameHeader = getDriver().findElement(By.xpath("//div/h1/b")
        );

        String actualResult = NameHeader.getText();

        Assert.assertEquals(actualResult, expectedResult);

    }

    @Test
    public void testLinkFromMainPageToDashboardAndBackToMainPage_WhenClick() {

        String url = "https://openweathermap.org/";
        String expectedResult = "Сurrent weather and forecast - OpenWeatherMap";

        getDriver().get(url);

        Boolean dynamicElement = (new WebDriverWait(getDriver(), Duration.ofSeconds(60)))
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[@class ='owm-loader-container']/div[@class='owm-loader']")));

        WebElement dashboardButton = getDriver().findElement(
                By.xpath("//div[@id='desktop-menu']//li/a[text() = 'Dashboard']"));

        dashboardButton.click();

        WebElement NameHeader = getDriver().findElement(By.xpath("//div/h1/b")
        );

        String actualResult1 = NameHeader.getText();

        WebElement HomeButton = getDriver().findElement(
                By.xpath("//div/ol/li/a[@href = '/']"));

        HomeButton.click();

        dynamicElement = (new WebDriverWait(getDriver(), Duration.ofSeconds(60)))
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[@class ='owm-loader-container']/div[@class='owm-loader']")));

        String actualResult = getDriver().getTitle();

        Assert.assertEquals(actualResult, expectedResult);
    }
}
