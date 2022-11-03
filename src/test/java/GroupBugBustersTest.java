import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupBugBustersTest extends BaseTest {

    @Test
    public void testArailymSuccessLogIn(){
        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        getDriver().findElement(By.xpath("//body/a/i")).click();
        getDriver().findElement(By.xpath("//body/nav/ul/li/a[@href = 'profile.php#login']")).click();
        getDriver().findElement(By.id("txt-username")).sendKeys("John Doe");
        getDriver().findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        getDriver().findElement(By.id("btn-login")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//h2")).getText(), "Make Appointment");
    }
}
