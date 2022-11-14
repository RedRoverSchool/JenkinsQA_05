import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
    public void testSignInDarina() {
        getDriver().get("https://the-internet.herokuapp.com/login");
        getDriver().findElement(By.id("username")).sendKeys("tomsmith");
        getDriver().findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        WebElement confirmationMessage = getDriver().findElement(By.xpath("//div[@id='flash']"));

        Assert.assertEquals(confirmationMessage.getText(), "You logged into a secure area!\n" + "×");
    }

    @Test
    public void testFormSubmittedIna() {
        getDriver().get(URL_WEBFORM);
        getDriver().findElement(By.id("my-text-id")).sendKeys("Hello!");
        getDriver().findElement(By.xpath("//button")).click();
        WebElement confirmMessage = getDriver().findElement(By.xpath("//h1"));

        Assert.assertEquals(confirmMessage.getText(), "Form submitted");
    }

    @Test
    public void testDropdownMenuElena() {
        getDriver().get(URL_WEBFORM);
        WebElement textInput = getDriver().findElement(By.id("my-text-id"));
        textInput.clear();
        textInput.sendKeys("Hello everybody");
        WebElement dropdownSelect = getDriver().findElement(By.name("my-select"));
        Select select = new Select(dropdownSelect);
        select.selectByValue("1");
        WebElement oneKey = getDriver().findElement(By.xpath("//option[@value='1']"));

        Assert.assertTrue(oneKey.isSelected());
    }

    @Test
    public void testSliderMouseFoxtiptop() {
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
    public void testSliderKeyboardFoxtiptop() {
        getDriver().get(URL_WEBFORM);
        WebElement exampleRange = getDriver().findElement(By.name("my-range"));
        for (int i = 1; i <= 4; i++) {
            exampleRange.sendKeys(Keys.ARROW_RIGHT);
        }

        int rangeValue = Integer.parseInt(exampleRange.getAttribute("value"));
        Assert.assertEquals(rangeValue, 9);
    }

    @Test
    public void testMintHouseDateSelectionNoPastDateSimonGertz() {
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
                        .xpath("//div[@class='hero hero-home']//div[@class='month-item no-previous-month']//div[@class='container__days']//div[@data-time=" + Math.subtractExact(todayMillis, dayInMillis) + "]"))
                .getAttribute("class");

        Assert.assertTrue(actualResult.contains("is-locked"));
    }

    @Test
    public void testSignInAppointmentDarina() {
        getDriver().get(URL_DEMO);
        getDriver().findElement(By.xpath("//a[@id='btn-make-appointment']")).click();
        getDriver().findElement(By.xpath("//input[@id='txt-username']")).sendKeys(USERNAME_DEMO);
        getDriver().findElement(By.xpath("//input[@id='txt-password']")).sendKeys(PASSWORD_DEMO);
        getDriver().findElement(By.xpath("//button[@id='btn-login']")).click();
        WebElement confirmationMessage = getDriver().findElement(By.xpath("//h2[text() = 'Make Appointment']"));

        Assert.assertTrue(confirmationMessage.isDisplayed());
        Assert.assertEquals(confirmationMessage.getText(), "Make Appointment");
    }

    @Test
    public void testAppointmentDarina() {
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
    public void testMintHouseSource2Natalia() {
        getDriver().get(URL_MINTHOUSE);
        String currentSource = getDriver().getPageSource();

        Assert.assertTrue(currentSource.contains("div"));
    }

    @Test
    public void testChangeColorIna() {
        getDriver().get(URL_WEBFORM);
        WebElement color = getDriver().findElement(By.name("my-colors"));
        String initialColor = color.getAttribute("value");
        color.sendKeys(Keys.ARROW_LEFT);
        String changedColor = color.getAttribute("value");

        Assert.assertNotEquals(initialColor, changedColor);
    }

    @Test
    public void testMakeAppointmentElena() {
        getDriver().get(URL_DEMO);
        getDriver().findElement(By.id("menu-toggle")).click();
        getDriver().findElement(By.xpath("//li/a[text()='Login']")).click();
        getDriver().findElement(By.xpath("//input[@id='txt-username']")).sendKeys(USERNAME_DEMO);
        getDriver().findElement(By.xpath("//input[@id='txt-password']")).sendKeys(PASSWORD_DEMO);
        getDriver().findElement(By.id("btn-login")).click();
        WebElement textAppoint = getDriver().findElement(By.xpath("//h2[text() = 'Make Appointment']"));

        Assert.assertEquals(textAppoint.getText(), "Make Appointment");
    }

    private static String getUniqueEmail() {
        return "dar" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "@gmail.com";
    }

    @Test
    public void testBookingDarina() {
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
    public void testInvalidBookingDarina() {
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

    @Test
    public void testCheckButtonElena() {
        getDriver().get(URL_WEBFORM);
        WebElement messageInput = getDriver().findElement
                (By.xpath("//label[contains(text(),'File input')]"));
        WebElement checkbox2 = getDriver().findElement(By.id("my-check-2"));
        checkbox2.click();
        WebElement buttonDefault = getDriver().findElement(By.id("my-radio-2"));
        buttonDefault.click();

        Assert.assertEquals(messageInput.getText(), "File input");
        Assert.assertTrue(checkbox2.isSelected());
        Assert.assertTrue(buttonDefault.isSelected());
    }

    @Test
    public void testRadioButtonCheckBoxIna(){
        getDriver().get(URL_WEBFORM);
        WebElement radio1 = getDriver().findElement(By.id("my-radio-1"));
        WebElement radio2 = getDriver().findElement(By.id("my-radio-2"));
        radio2.click();
        WebElement check1 = getDriver().findElement(By.id("my-check-1"));
        WebElement check2 = getDriver().findElement(By.id("my-check-2"));
        check2.click();

        Assert.assertTrue(!(radio1.isSelected()) && radio2.isSelected());
        Assert.assertTrue(check1.isSelected() && check2.isSelected());
    }

    @Test
    public void testMintHouseSlideOutImageSimon() {
        getDriver().get(URL_MINTHOUSE);

        WebElement slideOutMenu = getDriver().findElement(
                By.xpath("//*[local-name()='svg' and @id='destination-plus']/*[local-name()='path']"));
        new Actions(getDriver())
                .moveToElement(slideOutMenu).click().pause(500)
                .moveToElement(getDriver().findElement(By.cssSelector("#destination-list a"))).pause(500).perform();
        boolean actualResult = getDriver().findElement(By.cssSelector(".destination-item.active img")).isDisplayed();

        Assert.assertTrue(actualResult);
    }
}
