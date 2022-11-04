import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class MykhailoTest extends BaseTest {
    @Test
    public void testWeb(){
        getDriver().get("https://formy-project.herokuapp.com");
        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/buttons']"));
        Assert.assertEquals(link.getText(),"Buttons");
    }


}
