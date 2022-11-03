import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;

public class FriaufTest extends BaseTest {

    static {
        WebDriverManager.chromedriver().setup();
    }


    @Test
    public void positiveLoginTest()  {

        getDriver().get("http://qa.jtalks.org/jcommune/");
        getDriver().manage().window().maximize();
        String actualTitle = getDriver().getTitle();
        String expectedTitle = "TESTФорум Jcommune (QA instance)";
        Assert.assertEquals(expectedTitle, actualTitle);

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        getDriver().findElement(By.id("signin")).click();
        getDriver().findElement(By.id("userName")).sendKeys("test-user");
        getDriver().findElement(By.id("password")).sendKeys("test-user");
        getDriver().findElement(By.id("signin-submit-button")).click();

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        Assert.assertTrue(getDriver().findElement(By.id("user-dropdown-menu-link")).getText().contains("test-user"));
    }


    @Test
    public void negativeLoginTest()  {
        getDriver().get("http://qa.jtalks.org/jcommune/");
        getDriver().manage().window().maximize();
        String actualTitle = getDriver().getTitle();
        String expectedTitle = "TESTФорум Jcommune (QA instance)";
        Assert.assertEquals(expectedTitle, actualTitle);

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        getDriver().findElement(By.id("signin")).click();
        getDriver().findElement(By.id("userName")).sendKeys("test-user");
        getDriver().findElement(By.id("password")).sendKeys("test");
        getDriver().findElement(By.id("signin-submit-button")).click();

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        Assert.assertTrue(getDriver().findElement(By.xpath("//span[@class = 'help-inline _error']")).getText().contains("Неверное имя пользователя или пароль"));
    }




}
