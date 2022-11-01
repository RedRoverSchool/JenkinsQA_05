import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class MBatozTest extends BaseTest {

    @Test
    public void testHerokuApp() {
        //https://formy-project.herokuapp.com/
        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/autocomplete']"));

        Assert.assertEquals(link.getText(), "Autocomplete");

    }

    @Test
    public void testHerokuAppSearchCheckboxMenu() {
        getDriver().get("https://formy-project.herokuapp.com/");

        //Thread.sleep(5000);
        WebElement link1 = getDriver().findElement(By.xpath("//li/a[@href='/checkbox']"));

        Assert.assertEquals(link1.getText(), "Checkbox");
    }

}
