import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class ICDreamTeamTest extends BaseTest {

    @Test
    public void firstTest() {
        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");
        WebElement text = getDriver().findElement(By.id("my-text-id"));
        text.sendKeys("Hello!");
        getDriver().findElement(By.xpath("//button")).click();
        String actualResult = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(actualResult, "Form submitted");
    }

}
