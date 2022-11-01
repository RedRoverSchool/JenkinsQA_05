package group_don_simon_tutankhamon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class IKrlkvTest extends BaseTest {

    @Test
    public void testButtonsLinkTextHerokuApp() {

        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement buttonsLink = getDriver().findElement(By.xpath("//li/a[@href='/buttons']"));

        Assert.assertEquals(buttonsLink.getText(), "Buttons");
    }

    @Test
    public void testButtonsPageURLHerokuApp() {

        getDriver().get("https://formy-project.herokuapp.com/");

        WebElement buttonsLink = getDriver().findElement(By.linkText("Buttons"));

        buttonsLink.click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://formy-project.herokuapp.com/buttons");
    }
}