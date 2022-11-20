import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrganizationFolderTest extends BaseTest {
    private static final String uniqueOrganizationFolderName = "folder" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
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
    public WebElement getInputLine() {
        return getDriver().findElement(INPUT_LINE);
    }
    public WebElement getSaveButton() {
        return getDriver().findElement(SAVE_BUTTON);
    }

    private void createNewOrganizationFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(uniqueOrganizationFolderName);
        getDriver().findElement(ORGANIZATION_FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
    }

    @Test
    public void testCreateOrganizationFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getInputName().sendKeys(uniqueOrganizationFolderName + "2");
        getOrganizationFolder().click();
        getOkButton().click();
        getSaveButton().click();

        Assert.assertEquals(getDriver().findElement(TITLE).getText(), uniqueOrganizationFolderName + "2");
    }

    @Test
    public void testRenameOrganizationFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getInputName().sendKeys("Existing Organization Name");
        getOrganizationFolder().click();
        getOkButton().click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.linkText("Rename")).click();
        getInputLine().clear();
        getInputLine().sendKeys("New Organization Folder");
        getDriver().findElement(RENAME_BUTTON).click();
        getDashboard().click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='job/New%20Organization%20Folder/']"))
                .getText(), "New Organization Folder");
    }

    @Test
    public void testRenameOrganizationFolder1()  {
        createNewOrganizationFolder();

        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(INPUT_LINE).clear();
        getDriver().findElement(INPUT_LINE).sendKeys(uniqueOrganizationFolderName + "1");
        getDriver().findElement(RENAME_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(TITLE).getText(), uniqueOrganizationFolderName + "1");
    }

    @Test
    public void testCreateOrganizationFolderWithCheckOnDashbord() {
        final String organizationFolderName = "OrganizationFolder_" + (int) (Math.random() * 100);
        boolean actualResult = false;

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.cssSelector(".jenkins_branch_OrganizationFolder")).click();
        getInputName().sendKeys(organizationFolderName);
        getOkButton().click();
        getDriver().findElement(By.xpath("//li[@class='item']/a[@href='/']")).click();
        List<WebElement> list = getDriver().findElements(By.cssSelector(".jenkins-table__link.model-link.inside span"));

        Assert.assertTrue(list.size()>0);

        for (WebElement a : list){
           if(a.getText().equals(organizationFolderName)){
                actualResult = true;
                break;
           }
        }

        Assert.assertTrue(actualResult);
    }

    @Test
    public void testCreateOrgFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(uniqueOrganizationFolderName + 5);
        getDriver().findElement(ORGANIZATION_FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertEquals(getDriver().
                findElement(By.xpath("//div[@id='main-panel']/h1")).getText(), uniqueOrganizationFolderName + 5);
    }

    @Test
    public void testCreateOrgFolderEmptyName(){
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(ORGANIZATION_FOLDER).click();

        Assert.assertEquals(getDriver().
                        findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(getDriver().findElement(OK_BUTTON).isEnabled());
    }
}
