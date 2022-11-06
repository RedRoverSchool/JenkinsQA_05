import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

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
    public void testArailymIsElementsDisplayed(){
        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        Assert.assertTrue(getDriver().findElement(By.xpath("//i[@class='fa fa-facebook fa-fw fa-3x']"))
                .isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//i[@class='fa fa-twitter fa-fw fa-3x']"))
                .isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//i[@class='fa fa-dribbble fa-fw fa-3x']"))
                .isDisplayed());
    }
    }




