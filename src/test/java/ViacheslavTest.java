import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class ViacheslavTest extends BaseTest {


    @Test
    public void testDragAndDropClick() {
        getDriver().get("https://formy-project.herokuapp.com/");

        getDriver().findElement(By.xpath("//li/a[@href='/dragdrop']")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://formy-project.herokuapp.com/dragdrop");
    }
}
