import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class SpiritMastersTest extends BaseTest {

    @Test
    public void switchToSecondWindow_OlPolezhaeva_Test() {

        getDriver().get("https://www.toolsqa.com/selenium-training/");

        WebElement toolsQAHeader= getDriver().findElement(By.xpath("//div[@class='col-auto']//li[3]"));
        toolsQAHeader.click();

        for(String tab : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(tab);
        }
        getDriver().findElement(By.xpath("//div[@class='card-body']/h5")).click();

        Assert.assertEquals(getDriver().findElement(By.className("main-header")).getText(), "Elements");
    }
}
