import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class IrynaKlTest extends BaseTest {
    @Test

    public void testTemperatureInFahrenheit() throws InterruptedException {

        getDriver().get("https://openweathermap.org/");
        Thread.sleep(5000);

        WebElement temperatureF = getDriver().findElement
                (By.xpath("//div[text()='Imperial: °F, mph']"));

        temperatureF.click();
        Thread.sleep(2000);
        WebElement imageTempF = getDriver().findElement(By.xpath("//div[@class ='current-temp']"));

        Assert.assertTrue(imageTempF.getText().contains("°F"));

    }


}
