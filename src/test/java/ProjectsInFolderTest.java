import model.HomePage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;
import static runner.TestUtils.getRandomStr;

public class ProjectsInFolderTest extends BaseTest {
    private static final String RANDOM_NAME = getRandomStr(10);
    private static final By NEW_ITEM_IN_FOLDER = By.xpath("//a[@href='/job/" + RANDOM_NAME + "/newJob']");
    private static final By OKAY_BUTTON = By.className("btn-decorator");
    private static final By DASHBOARD = By.cssSelector("#breadcrumbs li a");
    private static final By SUBMIT = By.xpath("//button[@type='submit']");

    @Test
    public void testCreateFolder() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .typeName(RANDOM_NAME)
                .selectFolderAndClickOk()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobList().contains(RANDOM_NAME));
    }

    @Test (dependsOnMethods = "testCreateFolder")
    public void createOrganizationFolderInFolderTest() {
        getDriver().findElement(By.xpath("//a[@href='job/" + RANDOM_NAME + "/']")).click();
        getDriver().findElement(NEW_ITEM_IN_FOLDER).click();
        getDriver().findElement(By.id("name")).sendKeys(RANDOM_NAME);

        TestUtils.scrollToEnd(getDriver());

        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(
                By.xpath("//span[contains(text(),'Organization Folder')]"))).click();

        getDriver().findElement(OKAY_BUTTON).click();
        getDriver().findElement(SUBMIT).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div[class='clear'] div h1")).getText(), RANDOM_NAME);
    }
}
