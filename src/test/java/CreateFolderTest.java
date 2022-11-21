import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class CreateFolderTest extends BaseTest {
    private final String folderName = "First job";

    public void deleteCreatedFolder(){
        getDriver().findElement(By.linkText(folderName)).click();
        getDriver().findElement(By.linkText("Delete Folder")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
    }

    @Test
    public void testCreateFolder(){
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//form/div[2]/div[2]/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        String actualFolderName = getDriver().findElement(By.linkText(folderName)).getText();

        Assert.assertEquals(folderName,actualFolderName);
        deleteCreatedFolder();
    }
}
