import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;

public class GroupDreamTeamTest extends BaseTest {

    @Test
    public void test_signInDarina() {
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
    public void testSimonGertzMintHouseDateSelectionNoPastDate() {
        final long dayInMillis = 86400000;

        getDriver().get("https://minthouse.com/");

        WebElement propertyList = getDriver().findElement(By
                .xpath("//div[@class='hero hero-home']//span[@class='text'][contains(text(),'Where to next')]"));
        new WebDriverWait(getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(propertyList));
        new Actions(getDriver()).click(propertyList).perform();
        getDriver().findElement(By
                .xpath("//div[@class='dropdown active']//a[@tabindex='0'][contains(text(),'Mint House Austin – South Congress')]")).click();

        WebElement dates = getDriver().findElement(By
                .xpath("//div[@class='hero hero-home']//span[@class='value']//span[@class='t-check-in'][contains(text(),'Select date')]"));
        new WebDriverWait(getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(dates));
        new Actions(getDriver()).click(dates).perform();

        WebElement checkClickable = getDriver()
                .findElement(By
                        .xpath("//div[@class='hero hero-home']//div[@class='month-item no-previous-month']//div[@class='container__days']//div[@class='day-item is-today']"));
        new WebDriverWait(getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(checkClickable));
        new Actions(getDriver()).click(dates).perform();

        long todayMillis = Long.parseLong(getDriver()
                .findElement(By
                        .xpath("//div[@class='hero hero-home']//div[@class='month-item no-previous-month']//div[@class='container__days']//div[@class='day-item is-today']"))
                .getAttribute("data-time"));
        String actualResult = getDriver()
                .findElement(By
                        .xpath("//div[@class='hero hero-home']//div[@class='month-item no-previous-month']//div[@class='container__days']//div[@data-time=" + Math.subtractExact(todayMillis, dayInMillis) + "]"))
                .getAttribute("class");

        Assert.assertTrue(actualResult.contains("is-locked"));
    }

    @Ignore
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

    @Test
    public void test_signInAppointmentDarina() {
        final var url = "https://katalon-demo-cura.herokuapp.com/";
        final var username = "John Doe";
        final var password = "ThisIsNotAPassword";

        getDriver().get(url);
        getDriver().findElement(By.xpath("//a[@id='btn-make-appointment']")).click();
        getDriver().findElement(By.xpath("//input[@id='txt-username']")).sendKeys(username);
        getDriver().findElement(By.xpath("//input[@id='txt-password']")).sendKeys(password);
        getDriver().findElement(By.xpath("//button[@id='btn-login']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h2[text() = 'Make Appointment']")).isDisplayed());
    }

    @Test
    public void test_appointmentDarina(){
        final var url = "https://katalon-demo-cura.herokuapp.com/";
        final var username = "John Doe";
        final var password = "ThisIsNotAPassword";
        final var textAppointment = "Help me! Java kills me!";

        getDriver().get(url);
        getDriver().findElement(By.xpath("//a[@id='btn-make-appointment']")).click();
        getDriver().findElement(By.xpath("//input[@id='txt-username']")).sendKeys(username);
        getDriver().findElement(By.xpath("//input[@id='txt-password']")).sendKeys(password);
        getDriver().findElement(By.xpath("//button[@id='btn-login']")).click();
        getDriver().findElement(By.xpath("//div[@class='input-group-addon']")).click();
        getDriver().findElement(By.xpath("//td[@class = 'day' and text() = '30']")).click();
        getDriver().findElement(By.xpath("//textarea[@class = 'form-control']")).sendKeys(textAppointment);
        getDriver().findElement(By.xpath("//button[@id = 'btn-book-appointment']")).click();
        var confirmationText = getDriver().findElement(By.xpath("//h2[text() = 'Appointment Confirmation']")).getText();

        Assert.assertEquals(confirmationText, "Appointment Confirmation");
    }

    @Test
    public void testMintHouseSource2(){
        getDriver().get("https://minthouse.com/");
        String currentSource = getDriver().getPageSource();
        boolean actualResult = currentSource.contains("div");

        Assert.assertTrue(actualResult);
    }

    @Test
    public void test_Ina() {
        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");
        WebElement color = getDriver().findElement(By.name("my-colors"));
        String initialColor = color.getAttribute("value");
        color.sendKeys(Keys.ARROW_LEFT);
        String changedColor = color.getAttribute("value");

        Assert.assertNotEquals(initialColor, changedColor);
    }
}
