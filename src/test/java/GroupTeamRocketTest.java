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
        getDriver().get ("https://www.saucedemo.com");
        getDriver().findElement (By.id ("user-name")).sendKeys ("standard_user");
        getDriver().findElement (By.id ("password")).sendKeys ("secret_sauce");
        getDriver().findElement (By.id ("login-button")).click ();
        Assert.assertEquals (getDriver().getCurrentUrl (),"https://www.saucedemo.com/inventory.html");
    }
    @Test
    public void testFindTitleGuide_NataliiaOliver() throws InterruptedException {
        getDriver().get("https://openweathermap.org/");
        Thread.sleep(5000);
        getDriver().findElement(By.xpath("//div[@id='desktop-menu']/ul/li/a[@href='/guide']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(getDriver().getCurrentUrl(), "https://openweathermap.org/guide");
        Assert.assertEquals(
                getDriver()
                        .findElement(By.xpath("//div[@class='col-sm-7']/h1[text()='Guide']"))
                        .getText(),
                "Guide"
        );
    }
    @Test
    public void testCart() {
        getDriver().get ("https://www.saucedemo.com");
        getDriver().findElement (By.id ("user-name")).sendKeys ("standard_user");
        getDriver().findElement (By.id ("password")).sendKeys ("secret_sauce");
        getDriver().findElement (By.id ("login-button")).click ();
        getDriver().findElement (By.id ("add-to-cart-sauce-labs-backpack")).click ();
        getDriver().findElement (By.id ("shopping_cart_container")).click ();
        Assert.assertTrue (getDriver().findElement (By.id ("item_4_title_link")).isDisplayed ());
        }
     @Test
     public void testAboutLinkRedirect() {
        getDriver().get ("https://www.saucedemo.com");
        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();
        getDriver().findElement(By.id("react-burger-menu-btn")).click();
        getDriver().findElement(By.id("about_sidebar_link")).click();
        Assert.assertEquals(getDriver().getCurrentUrl(),"https://saucelabs.com/");
    }
}