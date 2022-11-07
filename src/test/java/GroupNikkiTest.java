import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GroupNikkiTest extends BaseTest {

    static {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void julieTest1() {

        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        String actualTitle = getDriver().getTitle();
        String expectedTitle = "CURA Healthcare Service";
        Assert.assertEquals(actualTitle, expectedTitle);


    }
    @Test
    public void makeUpAppointmentTest() {

        getDriver().get("https://katalon-demo-cura.herokuapp.com/");



        WebElement makeAppointment = getDriver().findElement(By.id("btn-make-appointment"));

        Assert.assertTrue(makeAppointment.isDisplayed());
        makeAppointment.click();

        WebElement userName = getDriver().findElement(By.xpath("//input[@id= 'txt-username']"));
        userName.sendKeys("John Doe");

        WebElement password = getDriver().findElement(By.name("password"));
        password.sendKeys("ThisIsNotAPassword");

        WebElement login = getDriver().findElement(By.id("btn-login"));
        Assert.assertTrue(login.isDisplayed());
        login.click();

    }

    @Test
    public void testKate_SuccessOpenUpMenu () {

        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        WebElement menu = getDriver().findElement(By.xpath("//body/a[@id='menu-toggle']"));
        menu.click();

        WebElement menuHeader = getDriver().findElement(By.xpath("//body/nav//a[@href='./']"));
        String actualResult = menuHeader.getText();
        String expectedResult = "CURA Healthcare";

        Assert.assertEquals(actualResult, expectedResult);

    }
    @Test
    public void testMouseover_WebdDiverUniversityCom() {

        getDriver().get("https://webdriveruniversity.com/Actions/index.html");

        Actions actions = new Actions(getDriver());

        List<String> buttonsList = Arrays.asList("Hover Over Me First!", "Hover Over Me Second!", "Hover Over Me Third!");

        for (int i = 0; i < buttonsList.size(); i++) {
            String xpath = String.format("//button[text()='%s']", buttonsList.get(i));

            WebElement button = getDriver().findElement(By.xpath(xpath));

            actions.moveToElement(button).build().perform();

            WebElement dropDownText = getDriver().findElement(By.xpath(String.format("//*[@id=\"div-hover\"]/div[%d]/div/a", i + 1)));

            Assert.assertTrue(dropDownText.isDisplayed());
        }
    }

    @Test
    public void testArailymIsElementsDisplayed(){
        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        Assert.assertTrue(getDriver().findElement(By.xpath("//i[@class='fa fa-facebook fa-fw fa-3x']"))
                .isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//i[@class='fa fa-twitter fa-fw fa-3x']"))
                .isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//i[@class='fa fa-dribbble fa-fw fa-3x']"))
                .isDisplayed());
    }

    @Test
    public void testSergeDotMintHouseForm() {

        String url = "https://minthouse.com/";

        getDriver().get(url);
        getDriver().findElement(By.xpath("//ul[contains(@role,'menu')]//li[contains(@class,'contact-us-trigger-wrap')]//a[contains(@class,'menu__link')]//span[contains(@class,'global-menu__span')][contains(text(),'Contact us')]")).click();
        getDriver().findElement(By.cssSelector("div form span > input[id='off-first-name']")).sendKeys("NameName");
        getDriver().findElement(By.cssSelector("#off-first-name")).submit();
        String actualResult = getDriver().findElement(By.xpath("//form[@action='/#wpcf7-f928-o5']//span[@class='wpcf7-form-control-wrap']/span")).getText();
        String expectedResult = "The field is required.";

        Assert.assertEquals(actualResult,expectedResult);
    }

    @Test
    public void testSergeDotMintHouseDateSelection() throws InterruptedException {

        String url = "https://minthouse.com/";

        getDriver().get(url);

        WebElement prop = getDriver().findElement(By.xpath("//div[@class='hero hero-home']//div[@class='container']//div[@class='row']//div[@class='col-sm-12']//div[@class='booker']//div[@class='t-datepicker main-datepicker']//form[@class='booking-form']//div[@class='destination']//span[@class='value']//span[@class='text'][contains(text(),'Where to next')]"));
        new WebDriverWait(getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(prop));
        new Actions(getDriver())
                .click(prop)
                .perform();
        getDriver().findElement(By.xpath("//div[@class='dropdown active']//ul[@class='dropdown-menu']//li//ul//li//a[@tabindex='0'][contains(text(),'Mint House Austin – South Congress')]")).click();

        WebElement dates = getDriver().findElement(By.xpath("//div[@class='hero hero-home']//div[@class='container']//div[@class='row']//div[@class='col-sm-12']//div[@class='booker']//div[@class='t-datepicker main-datepicker']//form[@class='booking-form']//div[@class='booker-calendar']//div[@class='check-in']//span[@class='value']//span[@class='t-check-in'][contains(text(),'Select date')]"));
        new WebDriverWait(getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(dates));
        new Actions(getDriver())
                .click(dates)
                .perform();
        String i = "none";
        List<WebElement> DATELIST = new ArrayList<>(getDriver().findElements(By.xpath("//div[@class='hero hero-home']//div[@class='container']//div[@class='row']//div[@class='col-sm-12']//div[@class='booker']//div[@class='t-datepicker main-datepicker']//form[@class='booking-form']//div[@class='booker-calendar']//div[@class='litepicker']//div[@class='container__main']//div[@class='container__months']//div[@class='month-item no-previous-month']//div[@class='container__days']//div[@class='day-item']")));
        for(WebElement element : DATELIST) {
            if(TimeUnit.MILLISECONDS.toDays(Math.subtractExact(Long.parseLong(element.getAttribute("data-time")), System.currentTimeMillis())) == 14 ) {
                i = element.getAttribute("data-time");
                new WebDriverWait(getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(element));
                new Actions(getDriver())
                        .click(element)
                        .perform();
                break;
            }
        }
        String actualResult = getDriver().findElement(By.xpath("//div[@class='hero hero-home']//div[@class='container']//div[@class='row']//div[@class='col-sm-12']//div[@class='booker']//div[@class='t-datepicker main-datepicker']//form[@class='booking-form']//div[@class='booker-calendar']//div[@class='litepicker']//div[@class='container__main']//div[@class='container__months']//div[@class='month-item no-previous-month']//div[@class='container__days']//div[@data-time=" + i + "]")).getAttribute("class");

        Assert.assertTrue(actualResult.contains("is-start-date is-locked"));
    }
}




