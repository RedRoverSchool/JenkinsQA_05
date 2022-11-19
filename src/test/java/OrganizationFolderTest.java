import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class OrganizationFolderTest extends BaseTest {

    private static final By INPUT_NAME = By.xpath("//input [@name = 'name']");
    private static final By ORGANIZATION_FOLDER = By.xpath("//li[@class = 'jenkins_branch_OrganizationFolder']");
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By DASHBOARD = By.xpath("//a[text()='Dashboard']");
    private static final By APPLY_BUTTON = By.id("yui-gen13-button");
    private static final By SAVE_BUTTON = By.id("yui-gen15-button");
    private static final By INPUT_LINE = By.name("newName");
    private static final By RENAME_BUTTON = By.id("yui-gen1-button");
    private static final By TITLE = By.xpath("//div[@id='main-panel']/h1");

    public WebElement getInputName() {
        return getDriver().findElement(INPUT_NAME);
    }
    public WebElement getOrganizationFolder() {
        return getDriver().findElement(ORGANIZATION_FOLDER);
    }
    public WebElement getOkButton() {
        return getDriver().findElement(OK_BUTTON);
    }
    public WebElement getDashboard() {
        return getDriver().findElement(DASHBOARD);
    }
    public WebElement getApplyButton() {
        return getDriver().findElement(APPLY_BUTTON);
    }

    private void createNewOrganizationFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(uniqueOrganizationFolderName);
        getDriver().findElement(ORGANIZATION_FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
    }

    @Ignore
    @Test
    public void testCreateOrganizationFolder(){
        getDriver().findElement(By.linkText("New Item")).click();
        getInputName().sendKeys("First Organization Folder");
        getOrganizationFolder().click();
        getOkButton().click();
        getApplyButton().click();
        getDashboard().click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href ='job/First%20Organization%20Folder/']"))
                .getText(), "First Organization Folder");
    }

    @Ignore
    @Test
    public void testRenameOrganizationFolder(){
        getDriver().findElement(By.linkText("New Item")).click();
        getInputName().sendKeys("Existing Organization Name");
        getDriver().findElement(By.xpath("//li[@class='jenkins_branch_OrganizationFolder']")).click();
        getOkButton().click();
        getDriver().findElement(By.id("yui-gen15-button")).click();
        getDriver().findElement(By.xpath("(//a[@class='task-link '])[7]")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys("New Organization Folder");
        getDriver().findElement(By.id("yui-gen1-button")).click();
        getDashboard().click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='job/New%20Organization%20Folder/']"))
                .getText(), "New Organization Folder");
    }

    @Test
    public void testRenameOrganizationFolder1() {
        createNewOrganizationFolder();

        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(INPUT_LINE).clear();
        getDriver().findElement(INPUT_LINE).sendKeys(uniqueOrganizationFolderName + "1");
        getDriver().findElement(RENAME_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(TITLE).getText(), uniqueOrganizationFolderName + "1");
    }
}
