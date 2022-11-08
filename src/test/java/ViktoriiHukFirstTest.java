import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class ViktoriiHukFirstTest extends BaseTest {


    @Test
    public void testWenPageApiHas30OrangeButten () throws InterruptedException {

        String url = "https://openweathermap.org/";
        int expectedResult = 30;

        getDriver().get(url);
        getDriver().manage().window().maximize();
        Thread.sleep(5000);

        WebElement MenuAPI = getDriver().findElement(
                By.xpath("//div[@id = 'desktop-menu']/ul/li[2]/a")
        );
        MenuAPI.click();

        int actualResult = getDriver().findElements(
                By.xpath("//a[contains(@class, 'orange')]")).size();

        Assert.assertEquals(actualResult,expectedResult);

    }
    @Test
    public void testWenClickToLogo()throws InterruptedException {

        String url = "https://openweathermap.org/";
        String expectedResult = "https://openweathermap.org/";

        getDriver().get(url);
        getDriver().manage().window().maximize();
        Thread.sleep(5000);

        WebElement searchLogo = getDriver().findElement(
                By.xpath("//li[@class = 'logo']//img")
        );
        searchLogo.click();

        String actualResult = getDriver().getCurrentUrl();

        Assert.assertEquals(actualResult, expectedResult);

    }
}
