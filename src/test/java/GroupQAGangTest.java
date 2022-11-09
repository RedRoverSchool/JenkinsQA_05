import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class GroupQAGangTest extends BaseTest {

    @Test
    public void testText() {
        getDriver().get("https://koma.lux.pl/");
        WebElement link = getDriver().findElement(By.xpath("//*[@id=\"main-nav\"]/ul/li[2]/a"));
        Assert.assertEquals(link.getText(), "Strona główna");
    }

    @Test
    public void testTextTitlePageApi_PaulLiberman() throws InterruptedException {
        getDriver().get("https://openweathermap.org/");
        Thread.sleep(6000);
        getDriver().findElement(By.xpath("//div[@id='desktop-menu']/ul/li/a[@href='/api']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://openweathermap.org/api");
        Assert.assertEquals(
                getDriver()
                        .findElement(By.xpath("//div[@class='col-sm-7']/h1[text()='Weather API']"))
                        .getText(), "Weather API");
    }
}
