import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class FolderWithoutConfigTest extends BaseTest {
    private static final String NAME_FOLDER = "test folder 1";
    private static final String VALID_NAME = "New project(1.1)";

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

    @Test(dependsOnMethods = "testCreateFolderWithoutConfig")
    public void testCreateFreestyleProjectInFolderByCreateJob(){
        getDriver().findElement(By.linkText(NAME_FOLDER)).click();
        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys(VALID_NAME);
        getDriver().findElement(By.xpath("//img[@class='icon-freestyle-project icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                "Project " + VALID_NAME);
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectInFolderByCreateJob")
    public void testDeleteFreestyleProjectInFolder(){
        getDriver().findElement(By.linkText(NAME_FOLDER)).click();
        getDriver().findElement(By.linkText(VALID_NAME)).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[7]/span/a/span[1]")).click();
        getDriver().switchTo().alert().accept();

        Assert.assertNotNull(getDriver().findElement(By.className("empty-state-block")));
    }
}
