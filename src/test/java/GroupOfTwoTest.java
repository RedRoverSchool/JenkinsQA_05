import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupOfTwoTest extends BaseTest {
    @Test
    public void newSomethingTest() throws InterruptedException {
        getDriver().get("https://the-internet.herokuapp.com/");
        getDriver().findElement(By.xpath("//a[@href='/checkboxes']")).click();
        Thread.sleep(2000);
        WebElement checkBoxElement = getDriver().findElement(By.xpath("//body/div[2]/div[1]/div[1]/form[1]/input[1]"));
        WebElement checkBox = getDriver().findElement(By.xpath("//body/div[2]/div[1]/div[1]/form[1]/input[2]"));
        Assert.assertTrue(checkBox.isSelected());
        Assert.assertFalse(checkBoxElement.isSelected());
    }
}
