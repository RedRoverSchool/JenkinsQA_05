import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class BugBustersTest extends BaseTest {

    @Test
    public void testArailymSuccessLogIn() {
        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        getDriver().findElement(By.xpath("//body/a/i")).click();
        getDriver().findElement(By.xpath("//body/nav/ul/li/a[@href = 'profile.php#login']")).click();
        getDriver().findElement(By.id("txt-username")).sendKeys("John Doe");
        getDriver().findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        getDriver().findElement(By.id("btn-login")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h2")).getText(), "Make Appointment");

    }

    @Test
    public void testRadasSuccessLogIn() throws InterruptedException {
        getDriver().get("https://www.wunderground.com/");
        getDriver().findElement(By.xpath("//div[@id='global-header']/lib-login/div/p/span/a")).click();
        getDriver().findElement(By.xpath("/html/body/div[1]/div/div/div/div/div/div[3]/button[1]")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//input[@id='form-signin-email']")).sendKeys("motoxx68@gmail.com");
        getDriver().findElement(By.xpath("//input[@id='form-signin-password']")).sendKeys("Intsnewpassword");
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//input[@id='signIn'][@class='button']")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//div[@id = 'global-header']//button[@class = 'wu-account close-login ng-star-inserted']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//li[@translatecontext='wu-header-user-login']")).getText(), "Welcome back!");

    }
}
