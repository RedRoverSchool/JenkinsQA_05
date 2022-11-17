import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class FolderWithoutConfigTest extends BaseTest {
    final String NAME_FOLDER = "test folder 1";

    @Test
    public void testCreateFolderWithoutConfig() {
        getDriver().findElement(By.partialLinkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(NAME_FOLDER);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("yui-gen6")).click();
        getDriver().findElement(By.className("item")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[text()='" + NAME_FOLDER + "']")).getText(), NAME_FOLDER);
    }
}
