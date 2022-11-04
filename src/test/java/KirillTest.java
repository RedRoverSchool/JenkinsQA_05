
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class KirillTest extends BaseTest {

@Test
    public void testSomething()  {

    getDriver().get("https://weather.com/");

    WebElement link = getDriver().findElement(By.xpath("//a[@href='https://newsroom.ibm.com/the-weather-company']"));

    Assert.assertEquals(link.getText(), "Press Room");







    }

}
