import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class ElenaStratTest extends BaseTest {

    @Test
    public void testProjectHerokuApp() {
        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/keypress']"));

        Assert.assertEquals(link.getText(), "Key and Mouse Press");
    }
}
