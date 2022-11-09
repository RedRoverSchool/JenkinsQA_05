import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class MaxIaskoTest extends BaseTest {

    @Test
    public void testHerokuAppButtons() {

        String url = "https://formy-project.herokuapp.com/";
        String expectedButton = "Danger";

        getDriver().get(url);

        WebElement linkButton = getDriver().findElement(
                By.xpath("//li/a[@href='/buttons']"));
        linkButton.click();

        WebElement getButtonName = getDriver().findElement(
                By.xpath("//button[@class ='btn btn-lg btn-danger']"));
        String actualButton = getButtonName.getText();
        Assert.assertEquals(actualButton, expectedButton);
    }
}
