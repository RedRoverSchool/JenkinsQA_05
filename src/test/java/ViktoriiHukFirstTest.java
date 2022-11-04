import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
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
                By.xpath("//div[@id = 'desktop-menu']//li[2]/a")
        );
        MenuAPI.click();

        int actualResult = getDriver().findElements(
                By.xpath("//a[contains(@class, 'orange')]")).size();

        Assert.assertEquals(actualResult,expectedResult);

    }



}
