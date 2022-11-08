import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
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

    @Test
    public void testFoxtiptopSliderMouse() {
        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");
        WebElement exampleRange = getDriver().findElement(By.cssSelector("body main  div form div div:nth-child(3) label:nth-child(3) input"));
        Assert.assertTrue(exampleRange.isDisplayed());

        Actions slide = new Actions(getDriver());
        slide.moveToElement(exampleRange).clickAndHold(exampleRange).moveByOffset(50, 0)
                .release().build().perform();

        int rangeValue = Integer.parseInt(exampleRange.getAttribute("value"));
        Assert.assertEquals(rangeValue, 6);
    }

    @Test
    public void testFoxtiptopSliderKeyboard() {
        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");
        WebElement exampleRange = getDriver().findElement(By.name("my-range"));
        for (int i = 1; i <= 4; i++) {
            exampleRange.sendKeys(Keys.ARROW_RIGHT);
        }

        int rangeValue = Integer.parseInt(exampleRange.getAttribute("value"));
        Assert.assertEquals(rangeValue, 9);
    }
    @Test
    public void testTemperatureInFahrenheit() throws InterruptedException {
        final String url = "https://openweathermap.org/";
        final String symbolF = "°F";

        getDriver().get(url);
        Thread.sleep(3000);
        WebElement temperatureF = getDriver().findElement
                (By.xpath("//div[text()='Imperial: °F, mph']"));
        Thread.sleep(2000);
        temperatureF.click();
        WebElement imageTempF = getDriver().findElement(By.xpath("//div[@class ='current-temp']"));

        Assert.assertTrue(imageTempF.getText().contains(symbolF));
    }
}
