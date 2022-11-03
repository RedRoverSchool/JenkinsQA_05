import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class OlgaSTest extends BaseTest {


    @Test
    public void testProjectHerokuApp() {
        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/keypress']"));

        Assert.assertEquals(link.getText(), "Key and Mouse Press");
    }

    @Test

    public void testH1 () {

        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement textH1 = getDriver().findElement(
                By.xpath("//div[@class='jumbotron-fluid']/h1"));

        Assert.assertEquals(textH1.getText(),"Welcome to Formy");

    }
}
