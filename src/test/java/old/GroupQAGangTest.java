package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Ignore
public class GroupQAGangTest extends BaseTest {

    @Test
    public void testText() {

        getDriver().get("https://koma.lux.pl/");

        WebElement link = getDriver().findElement(By.xpath("//*[@id='main-nav']/ul/li[2]/a"));

        Assert.assertEquals(link.getText(), "Strona główna");
    }


    @Test
    public void testLinkOfApiPage() throws InterruptedException {

        getDriver().get("https://openweathermap.org/");

        Thread.sleep(6000);
        getDriver().findElement(By.xpath("//div[@id='desktop-menu']/ul/li/a[@href='/api']")).click();
        Thread.sleep(1000);

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://openweathermap.org/api");
        Assert.assertEquals(getDriver().findElement(
                        By.xpath("//div[@class='col-sm-7']/h1[text()='Weather API']")).getText(),
                "Weather API");
    }

    @Test
    public void testPricingWeather() throws InterruptedException {

        getDriver().get("https://openweathermap.org/");

        getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.MICROSECONDS);
        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/price']"));

        Thread.sleep(6000);
        link.click();

        String actualResult = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(actualResult, "Pricing");

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