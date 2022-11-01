import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class FriaufTest extends BaseTest {

    static {
        WebDriverManager.chromedriver().setup();
    }


    @Test
    public void positiveLoginTest() throws InterruptedException {

        getDriver().get("http://qa.jtalks.org/jcommune/");
        String actualTitle = getDriver().getTitle();
        String expectedTitle = "TESTФорум Jcommune (QA instance)";
        Assert.assertEquals(expectedTitle, actualTitle);

        Thread.sleep(2000);

        WebElement signIn = getDriver().findElement(By.id("signin"));
        signIn.click();
        WebElement userName = getDriver().findElement(By.id("userName"));
        userName.sendKeys("test-user");
        WebElement password = getDriver().findElement(By.id("password"));
        password.sendKeys("test-user");
        WebElement signInButton = getDriver().findElement(By.id("signin-submit-button"));
        signInButton.click();
        Thread.sleep(3000);
        WebElement userMenu = getDriver().findElement(By.id("user-dropdown-menu-link"));
        String userMenuText = userMenu.getText();
        Assert.assertTrue(userMenuText.contains("test-user"));
    }


    @Test
    public void negativeLoginTest() throws InterruptedException {
        getDriver().get("http://qa.jtalks.org/jcommune/");
        String actualTitle = getDriver().getTitle();
        String expectedTitle = "TESTФорум Jcommune (QA instance)";
        Assert.assertEquals(expectedTitle, actualTitle);

        Thread.sleep(2000);

        WebElement signIn = getDriver().findElement(By.id("signin"));
        signIn.click();
        WebElement userName = getDriver().findElement(By.id("userName"));
        userName.sendKeys("test-user");
        WebElement password = getDriver().findElement(By.id("password"));
        password.sendKeys("test");
        WebElement signInButton = getDriver().findElement(By.id("signin-submit-button"));
        signInButton.click();
        Thread.sleep(3000);

        WebElement errorMessage = getDriver().findElement(By.xpath("//span[@class = 'help-inline _error']"));

        String errorMessageText = errorMessage.getText();
        Assert.assertTrue(errorMessageText.contains("Неверное имя пользователя или пароль"));
    }




}
