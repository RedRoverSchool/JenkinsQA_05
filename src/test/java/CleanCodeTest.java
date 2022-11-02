import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class CleanCodeTest extends BaseTest {

    @Test
    public void testFolkInstruments() {
        getDriver().get("http://ludowe.instrumenty.edu.pl/en/instruments/show/instrument/4653");
        WebElement link = getDriver().findElement(By.xpath("/html/body/div[2]/div[4]/div/div/div/div/div[5]/h2"));
//        Thread.sleep(3000);
        Assert.assertEquals(link.getText(), "ritual scepter");
    }


}
