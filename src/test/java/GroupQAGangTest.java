import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupQAGangTest extends BaseTest {

    @Test
    public void testText() {
        getDriver().get("https://koma.lux.pl/");
        WebElement link = getDriver().findElement(By.xpath("//*[@id=\"main-nav\"]/ul/li[2]/a"));
        Assert.assertEquals(link.getText(), "Strona główna");
    }
}
