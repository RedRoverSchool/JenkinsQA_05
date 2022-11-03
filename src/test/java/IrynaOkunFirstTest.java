import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class IrynaOkunFirstTest extends BaseTest {

    @Test

    public void testHerokuappButtons() {

        //String url = "https://formy-project.herokuapp.com/";

        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement link = getDriver().findElement(By.xpath("//div/li/a[text()='Buttons']"));

        link.click();

        WebElement buttonsPage = getDriver().findElement(By.xpath("//div/div/div/button[text()='Warning']"));

        Assert.assertEquals(buttonsPage.getText(), "Warning");

    }

}
