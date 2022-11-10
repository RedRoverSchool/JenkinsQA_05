import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class AlexTest extends BaseTest {

    @Test
    public void testSearchResults() {
        final String expectedResult = "en.wikipedia.org";

        getDriver().get("https://ya.ru/");

        getDriver().findElement(By.id("text")).sendKeys("Vilnius");
        getDriver().findElement(By.xpath("//*[@type='submit']")).click();

        String actualResult = getDriver().findElement(By.xpath("//b[contains(text(), 'en.wikipedia.org')]"))
                .getText();

        Assert.assertEquals(actualResult, expectedResult);
    }
}
