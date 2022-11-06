import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class KirillNewTest extends BaseTest {
    @Test
    public void testSomething(){
        getDriver().get("https://the-internet.herokuapp.com/");
        WebElement link = getDriver().findElement(By.xpath("//a[@href='/dropdown']"));
        Assert.assertEquals(link.getText(), "Dropdown");
    }


}
