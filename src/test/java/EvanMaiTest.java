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

    }
}
