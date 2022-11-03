import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupDreamTeamTest extends BaseTest {

    @Test
    public void testDarina() {
        getDriver().get("https://the-internet.herokuapp.com/login");
        WebElement usernameTextField = getDriver().findElement(By.id("username"));
        WebElement passwordTextField = getDriver().findElement(By.id("password"));
        WebElement loginButton = getDriver().findElement(By.xpath("//button[@type='submit']"));

        usernameTextField.sendKeys("tomsmith");
        passwordTextField.sendKeys("SuperSecretPassword!");
        loginButton.click();

        WebElement confirmationMessage = getDriver().findElement(By.xpath("//div[@id='flash']"));
        Assert.assertEquals(confirmationMessage.getText(), "You logged into a secure area!\n" + "×");
    }

    @Test
    public void testIna() {
        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");
        getDriver().findElement(By.id("my-text-id")).sendKeys("Hello!");
        getDriver().findElement(By.xpath("//button")).click();
        String actualResult = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(actualResult, "Form submitted");
    }

    @Test
    public void ElenaTest() {
        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");
        WebElement textInput = getDriver().findElement(By.id("my-text-id"));
        textInput.clear();
        textInput.sendKeys("Hello everybody");
        WebElement dropdownSelect = getDriver().findElement(By.name("my-select"));
        Select sel = new Select(dropdownSelect);
        sel.selectByValue("1");
        WebElement one = getDriver().findElement(By.xpath("//option[@value='1']"));
        Assert.assertTrue(one.isSelected());
    }
}
