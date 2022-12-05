import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import static runner.TestUtils.getRandomStr;

public class ProjectsInFolderTest extends BaseTest {
    private static final String RANDOM_NAME = getRandomStr(10);
    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By OKAY_BUTTON = By.className("btn-decorator");
    private static final By DASHBOARD = By.cssSelector("#breadcrumbs li a");

    @Test
    public void testCreateFolder(){
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(By.id("name")).sendKeys(RANDOM_NAME);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(OKAY_BUTTON).click();
        getDriver().findElement(DASHBOARD).click();

        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//a[@href='job/" + RANDOM_NAME + "/']")).isDisplayed());
    }
}
