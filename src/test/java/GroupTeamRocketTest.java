import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupTeamRocketTest extends BaseTest {

    @Test
    public void testAddElementHerokuapp() {
        getDriver().get("https://the-internet.herokuapp.com/");
        getDriver().findElement(By.xpath("//a[@href='/add_remove_elements/']")).click();
        getDriver().findElement(By.xpath("//button[@onclick='addElement()']")).click();
        Assert.assertTrue(getDriver().findElement(By.xpath("//button[@class='added-manually']")).isDisplayed());
    }
    @Test
    public void testSwagLabs_LogIn()  {

        getDriver ().get ("https://www.saucedemo.com");
        getDriver ().findElement (By.id ("user-name")).sendKeys ("standard_user");
        getDriver ().findElement (By.id ("password")).sendKeys ("secret_sauce");
        getDriver ().findElement (By.id ("login-button")).click ();
        Assert.assertEquals (getDriver ().getCurrentUrl (),"https://www.saucedemo.com/inventory.html");
    }
}