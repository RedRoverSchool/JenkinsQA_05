import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.Arrays;
import java.util.List;

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
}




