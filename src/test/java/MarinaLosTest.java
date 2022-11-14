import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class MarinaLosTest extends BaseTest {

    public void testHerokuApp() {
        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/autocomplete']"));

        Assert.assertEquals(link.getText(), "Autocomplete");
    }

    @Test
    public void testHerokuAppComponentsList(){
        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement buttons = getDriver().findElement(By.xpath("//li/a[text()='Buttons']"));

        Assert.assertEquals(buttons.getText(), "Buttons");
    }

    @Test
    public void testFahrenheit() throws InterruptedException {
        getDriver().get("https://openweathermap.org/");

        Thread.sleep(3000);

        WebElement pressFahrenheit = getDriver().findElement(
                By.xpath("//div[@id='weather-widget']//div[text()='Imperial: °F, mph']")
        );

        Thread.sleep(3000);

        pressFahrenheit.click();

        WebElement resultFahrenheit = getDriver().findElement (
                By.xpath("//div[text()='Imperial: °F, mph']")
        );

        Assert.assertEquals(resultFahrenheit.getText(),"Imperial: °F, mph");
    }

}
