import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class EvanMaiTest extends BaseTest {
    @Test
    public void test1() {
        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        WebElement textWeCare = getDriver().findElement(By.xpath("//*[@id=\"top\"]/div/h3"));
        Assert.assertEquals(textWeCare.getText(), "We Care About Your Health");
    }
    @Test
    public void test2() {
        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        Assert.assertEquals(getDriver().getTitle(),"CURA Healthcare Service");
    }
    @Test
    public void test3() {
        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        WebElement makeAppoiment = getDriver().findElement(By.id("btn-make-appointment"));
        makeAppoiment.click();
        getDriver().findElement(By.name("username")).sendKeys("Evan");
        getDriver().findElement(By.name("password")).sendKeys("123456789");
        getDriver().findElement(By.className("btn-default")).click();
        boolean currentUrl = getDriver().getCurrentUrl().equals("https://katalon-demo-cura.herokuapp.com/profile.php#login");
        Assert.assertTrue(currentUrl);
    }
    @Test
    public void test4() {
        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        Assert.assertTrue(getDriver().findElement(By.xpath("/html/body/footer/div/div/div/ul[1]/li[1]")).isDisplayed());
    }
    @Test
    public void test5() {
        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        Assert.assertTrue(getDriver().findElement(By.xpath("/html/body/footer/div/div/div/ul[1]/li[1]")).isDisplayed());
    }
}
