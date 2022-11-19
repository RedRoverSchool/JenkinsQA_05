import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class AddFolderDescriptionTest extends BaseTest {

    private static final String FOLDERNAME = "SomeFolderName";
    private static final String DESCRIPTION = "Some random folder description";

    @Test
    public void testAddFolderDescription() {

        getDriver().findElement(By.xpath("//div[@id='tasks']//a")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(FOLDERNAME);
        getDriver().findElement(By.xpath("//div[@id='j-add-item-type-nested-projects']//li")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.tagName("textarea")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.id("yui-gen6-button")).click();
        getDriver().findElement(By.xpath("//ul[@id='breadcrumbs']//li")).click();
        getDriver().findElement(By.xpath(String.format("//span[text()='%s']", FOLDERNAME))).click();

        Assert.assertEquals(getDriver().findElement(By.id("view-message")).getText(), DESCRIPTION);
    }
}
