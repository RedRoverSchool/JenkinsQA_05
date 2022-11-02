import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.time.Duration;

@Test
public class WalterBTest extends BaseTest {

    @Ignore
    public void getPage() {

        getDriver().get("http://automationpractice.com/index.php/");
        WebElement link = getDriver().findElement(By.xpath("//*[@id='editorial_block_center']/h1"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        Assert.assertEquals(link.getText(), "Automation Practice Website");

    }
}
