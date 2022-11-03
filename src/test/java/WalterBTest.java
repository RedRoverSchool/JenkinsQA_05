import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.time.Duration;


public class WalterBTest extends BaseTest {

    @Test

    public void getPage() {

        getDriver().get("https://www.demoblaze.com/");
        WebElement link = getDriver().findElement(By.xpath("//*[@id=\'fotcont\']/div[1]/div/div/p"));

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        Assert.assertEquals(link.getText(), "We believe performance needs to be validated at every stage of " +
                "the software development cycle and our open source compatible," +
                " massively scalable platform makes that a reality.");

    }
}
