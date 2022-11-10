import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class EugeneSTest extends BaseTest {


    @Test
    public void testWeather () throws InterruptedException {

        getDriver().get("https://dzen.ru/");
        WebElement link = getDriver().findElement(By.xpath("//span[contains(text(),'Войти')]"));

        Thread.sleep(5000);

        Assert.assertEquals(link.getText(), "Войти");


    }
}
