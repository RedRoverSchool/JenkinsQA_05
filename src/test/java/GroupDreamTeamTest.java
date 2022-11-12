import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class GroupDreamTeamTest extends BaseTest {
    private static final String URL_DEMO = "https://katalon-demo-cura.herokuapp.com/";
    private static final String USERNAME_DEMO = "John Doe";
    private static final String PASSWORD_DEMO = "ThisIsNotAPassword";
    private static final String URL_MINTHOUSE = "https://minthouse.com/";
    private static final String URL_WEBFORM = "https://www.selenium.dev/selenium/web/web-form.html";
    private static final String URL_BOOKING = "https://automationintesting.online/";

    @Test
    public void test_signInDarina() {
        getDriver().get("https://the-internet.herokuapp.com/login");
        getDriver().findElement(By.id("username")).sendKeys("tomsmith");
        getDriver().findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        WebElement confirmationMessage = getDriver().findElement(By.xpath("//div[@id='flash']"));

        Assert.assertEquals(confirmationMessage.getText(), "You logged into a secure area!\n" + "×");
    }

    @Test
    public void testIna() {
        getDriver().get(URL_WEBFORM);
        getDriver().findElement(By.id("my-text-id")).sendKeys("Hello!");
        getDriver().findElement(By.xpath("//button")).click();
        String actualResult = getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(actualResult, "Form submitted");
    }

    @Test
    public void ElenaTest() {
        getDriver().get(URL_WEBFORM);
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
        getDriver().get(URL_WEBFORM);
        WebElement exampleRange = getDriver().findElement(By.cssSelector("body main  div form div div:nth-child(3) label:nth-child(3) input"));
        Assert.assertTrue(exampleRange.isDisplayed());

        Actions action = new Actions(getDriver());
        action.moveToElement(exampleRange).clickAndHold(exampleRange).moveByOffset(50, 0)
                .release().build().perform();

        int rangeValue = Integer.parseInt(exampleRange.getAttribute("value"));
        Assert.assertEquals(rangeValue, 6);
    }

    @Test
    public void testFoxtiptopSliderKeyboard() {
        getDriver().get(URL_WEBFORM);
        WebElement exampleRange = getDriver().findElement(By.name("my-range"));
        for (int i = 1; i <= 4; i++) {
            exampleRange.sendKeys(Keys.ARROW_RIGHT);
        }

        int rangeValue = Integer.parseInt(exampleRange.getAttribute("value"));
        Assert.assertEquals(rangeValue, 9);
    }


    @Test
    public void testSimonGertzMintHouseDateSelectionNoPastDate(){
        final long dayInMillis = 86400000;
        
        getDriver().get(URL_MINTHOUSE);

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
                        .xpath("//div[@class='hero hero-home']//div[@class='month-item no-previous-month']//div[@class='container__days']//div[@data-time=" + Math.subtractExact(todayMillis,dayInMillis) + "]"))
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
        getDriver().get(URL_DEMO);
        getDriver().findElement(By.xpath("//a[@id='btn-make-appointment']")).click();
        getDriver().findElement(By.xpath("//input[@id='txt-username']")).sendKeys(USERNAME_DEMO);
        getDriver().findElement(By.xpath("//input[@id='txt-password']")).sendKeys(PASSWORD_DEMO);
        getDriver().findElement(By.xpath("//button[@id='btn-login']")).click();
        WebElement confirmationMessage = getDriver().findElement(By.xpath("//h2[text() = 'Make Appointment']"));

        Assert.assertTrue(confirmationMessage.isDisplayed());
        Assert.assertEquals(confirmationMessage.getText(),"Make Appointment");

    }

    @Test
    public void test_appointmentDarina(){
        final String textAppointment = "Help me! Java kills me!";

        getDriver().get(URL_DEMO);
        getDriver().findElement(By.xpath("//a[@id='btn-make-appointment']")).click();
        getDriver().findElement(By.xpath("//input[@id='txt-username']")).sendKeys(USERNAME_DEMO);
        getDriver().findElement(By.xpath("//input[@id='txt-password']")).sendKeys(PASSWORD_DEMO);
        getDriver().findElement(By.xpath("//button[@id='btn-login']")).click();
        getDriver().findElement(By.xpath("//div[@class='input-group-addon']")).click();
        getDriver().findElement(By.xpath("//td[@class = 'day' and text() = '30']")).click();
        getDriver().findElement(By.xpath("//textarea[@class = 'form-control']")).sendKeys(textAppointment);
        getDriver().findElement(By.xpath("//button[@id = 'btn-book-appointment']")).click();
        WebElement confirmationMessage = getDriver().findElement(By.xpath("//h2[text() = 'Appointment Confirmation']"));

        Assert.assertEquals(confirmationMessage.getText(), "Appointment Confirmation");
    }

    @Test
    public void testMintHouseSource2(){
        getDriver().get(URL_MINTHOUSE);
        String currentSource = getDriver().getPageSource();
        boolean actualResult = currentSource.contains("div");

        Assert.assertTrue(actualResult);
    }

    @Test
    public void test_Ina() {
        getDriver().get(URL_WEBFORM);
        WebElement color = getDriver().findElement(By.name("my-colors"));
        String initialColor = color.getAttribute("value");
        color.sendKeys(Keys.ARROW_LEFT);
        String changedColor = color.getAttribute("value");

        Assert.assertNotEquals(initialColor, changedColor);
    }

    @Test
    public void testElena2() {
        getDriver().get(URL_DEMO);
        getDriver().findElement(By.id("menu-toggle")).click();
        getDriver().findElement(By.xpath("//li/a[text()='Login']")).click();
        getDriver().findElement(By.xpath("//input[@id='txt-username']")).sendKeys(USERNAME_DEMO);
        getDriver().findElement(By.xpath("//input[@id='txt-password']")).sendKeys(PASSWORD_DEMO);

        Assert.assertTrue(true, "//div[@class='col-sm-12 text-center']");
    }

    private static String getUniqueEmail(){
        return "dar" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "@gmail.com";
    }
    @Test
    public void test_bookingDarina(){
        getDriver().get(URL_BOOKING);
        getDriver().findElement(By.xpath("//input[@data-testid='ContactName']")).sendKeys(USERNAME_DEMO);
        getDriver().findElement(By.xpath("//input[@data-testid='ContactEmail']")).sendKeys(getUniqueEmail());
        getDriver().findElement(By.xpath("//input[@data-testid='ContactPhone']")).sendKeys("12345678912");
        getDriver().findElement(By.xpath("//input[@data-testid='ContactSubject']")).sendKeys("Booking");
        getDriver().findElement(By.xpath("//textarea[@data-testid='ContactDescription']")).sendKeys("I need a room for today");
        getDriver().findElement(By.id("submitContact")).click();
        WebElement confirmationMessage = getDriver().findElement(By.xpath("//div[@class = 'col-sm-5']/div/h2"));

        Assert.assertEquals(confirmationMessage.getText(), "Thanks for getting in touch " + USERNAME_DEMO + "!");
    }

    @Test
    public void test_invalidBookingDarina(){
        getDriver().get(URL_BOOKING);
        getDriver().findElement(By.xpath("//input[@data-testid='ContactName']")).sendKeys(USERNAME_DEMO);
        getDriver().findElement(By.xpath("//input[@data-testid='ContactEmail']")).sendKeys(getUniqueEmail());
        getDriver().findElement(By.xpath("//input[@data-testid='ContactPhone']")).sendKeys("12345678912");
        getDriver().findElement(By.xpath("//input[@data-testid='ContactSubject']")).sendKeys("Booking");
        getDriver().findElement(By.xpath("//textarea[@data-testid='ContactDescription']")).sendKeys("I need a room");
        getDriver().findElement(By.id("submitContact")).click();
        WebElement errorMessage = getDriver().findElement(By.xpath("//div[@class='alert alert-danger']/p"));

        Assert.assertEquals(errorMessage.getText(), "Message must be between 20 and 2000 characters.");
    }

}
