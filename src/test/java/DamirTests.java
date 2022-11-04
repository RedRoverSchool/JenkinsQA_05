import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class DamirTests extends BaseTest{
    @Test
    public void testAcceptHomePage() {
        getDriver().get("https://the-internet.herokuapp.com/");
        WebElement pageLogo = getDriver().findElement(By.xpath("/html/body/div[2]/div/h1"));
        Assert.assertEquals("Welcome to the-internet", pageLogo.getText());

    }

    @Test
    public void TestAcceptABTestingPage()  {
        getDriver().get("https://the-internet.herokuapp.com/");
        WebElement ABTestingPage = getDriver().findElement(By.xpath("//*[@id=\"content\"]/ul/li[1]/a"));
        ABTestingPage.click();
        WebElement Text = getDriver().findElement(By.xpath("//*[@id=\"page-footer\"]/div/div/a"));
        Assert.assertEquals("Elemental Selenium",Text.getText());
    }

}
