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


    }




