import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupDreamTeamTest extends BaseTest {

    @Test
    public void testFirstTest() {
        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");
        WebElement textInput = getDriver().findElement(By.id("my-text-id"));
        textInput.clear();
        textInput.sendKeys("Hey man");
        WebElement dropdownSelect = getDriver().findElement(By.name("my-select"));
        Select sel = new Select(dropdownSelect);
        sel.selectByValue("1");
        WebElement one = getDriver().findElement(By.xpath("//option[@value='1']"));
        Assert.assertTrue(one.isSelected());
    }
}
