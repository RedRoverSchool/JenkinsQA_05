package tests;

import model.HomePage;
import model.folder.FolderStatusPage;
import model.organization_folder.OrgFolderConfigPage;
import model.organization_folder.OrgFolderStatusPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class OrganizationFolderTest extends BaseTest {
    private static final String uniqueOrganizationFolderName = "folder" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    private static final String ORG_FOLDER_NAME = TestUtils.getRandomStr();
    private static final String ORG_FOLDER_NAME_CREATE = TestUtils.getRandomStr();
    private static final String NAME_ORG_FOLDER = TestUtils.getRandomStr();
    private static final String nameOrgFolderPOM = TestUtils.getRandomStr();
    private static final String nameFolderPOM = TestUtils.getRandomStr();
    private static final String NAME_FOLDER = TestUtils.getRandomStr();
    private static final String DISPLAY_NAME = TestUtils.getRandomStr();
    private static final By INPUT_NAME = By.xpath("//input [@name = 'name']");
    private static final By ORGANIZATION_FOLDER = By.xpath("//li[@class = 'jenkins_branch_OrganizationFolder']");
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By DASHBOARD = By.xpath("//a[text()='Dashboard']");
    private static final By APPLY_BUTTON = By.id("yui-gen13-button");
    private static final By SAVE_BUTTON = By.id("yui-gen15-button");
    private static final By INPUT_LINE = By.name("newName");
    private static final By RENAME_BUTTON = By.id("yui-gen1-button");
    private static final By TITLE = By.xpath("//div[@id='main-panel']/h1");
    private static final By ITEM_FOLDER = By.xpath("//span[text()='" + NAME_FOLDER + "']");
    private static final By ITEM_ORG_FOLDER = By.xpath("//span[text()= '" + NAME_ORG_FOLDER + "']");


    private WebElement notificationSaved() {
        return getDriver().findElement(By.cssSelector("#notification-bar"));
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(5));
    }

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
        String actualOrgFolderDisplayName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(uniqueOrganizationFolderName)
                .selectOrgFolderAndClickOk()
                .clickSaveButton()
                .getDisplayName();

        Assert.assertEquals(actualOrgFolderDisplayName, uniqueOrganizationFolderName);
    }

    @Test
    public void testRenameOrganizationFolder() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(nameOrgFolderPOM)
                .selectOrgFolderAndClickOk()
                .clickSaveButton()
                .clickRenameButton()
                .clearAndInputNewName("New name " + nameOrgFolderPOM)
                .goToDashboard();

        Assert.assertTrue(homePage.getJobList().contains("New name " + nameOrgFolderPOM));
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

    @Test
    public void testCreateOrganizationFolderWithCheckOnDashbord() {
        final String organizationFolderName = "OrganizationFolder_" + (int) (Math.random() * 100);
        boolean actualResult = false;

        getDriver().findElement(By.linkText("New Item")).click();
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.cssSelector(".jenkins_branch_OrganizationFolder"))).click();
        getInputName().sendKeys(organizationFolderName);
        getOkButton().click();
        getDriver().findElement(By.xpath("//li[@class='item']/a[@href='/']")).click();
        List<WebElement> list = getDriver().findElements(By.cssSelector(".jenkins-table__link.model-link.inside span"));

        Assert.assertTrue(list.size() > 0);

        for (WebElement a : list) {
            if (a.getText().equals(organizationFolderName)) {
                actualResult = true;
                break;
            }
        }

        Assert.assertTrue(actualResult);
    }

    @Test
    public void testCreateOrgFolder() {
        List<String> allFolders = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORG_FOLDER_NAME_CREATE)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickDashboard()
                .getJobList();

        Assert.assertTrue(allFolders.contains(ORG_FOLDER_NAME_CREATE));
    }

    @Test
    public void testOrgFolderEmptyNameErr() {
        String errMessageEmptyName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("")
                .selectOrgFolderAndClickOk()
                .getErrorMessageEmptyField();

        Assert.assertEquals(errMessageEmptyName,
                "» This field cannot be empty, please enter a valid name");
    }
    @Test
    public void testCreateOrgFolderEmptyName() {
        OrgFolderConfigPage orgFolderConfigPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName("")
                .selectOrgFolderAndClickOk();
        Assert.assertFalse(orgFolderConfigPage.isOkButtonEnabled());
    }


    @Ignore
    @Test
    public void testCheckNotificationAfterApply() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(ORG_FOLDER_NAME);
        getDriver().findElement(ORGANIZATION_FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(APPLY_BUTTON).click();

        Assert.assertEquals(getWait().until(ExpectedConditions.visibilityOf(notificationSaved()))
                .getText(), "Saved");
        Assert.assertEquals(notificationSaved().getAttribute("class")
                , "notif-alert-success notif-alert-show");
    }

    @Test(dependsOnMethods = "testCreateOrgFolder")
    public void testCreateOrgFolderExistName() {
        String errMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORG_FOLDER_NAME_CREATE)
                .selectExistFolderAndClickOk()
                .getErrorMessage();

        Assert.assertEquals(errMessage, "A job already exists with the name ‘"
                + ORG_FOLDER_NAME_CREATE + "’");
    }

    @Ignore
    @Test
    public void testMoveOrgFolderToDashboard() {
        getDashboard().click();

        getWait(5).until(ExpectedConditions.elementToBeClickable(ITEM_FOLDER));
        TestUtils.scrollToElement(getDriver(), getDriver().findElement(ITEM_FOLDER));
        getDriver().findElement(ITEM_FOLDER).click();

        getWait(5).until(ExpectedConditions.elementToBeClickable(ITEM_ORG_FOLDER));
        getDriver().findElement(ITEM_ORG_FOLDER).click();

        getDriver().findElement(By.linkText("Move")).click();
        getDriver().findElement(By.name("destination")).click();
        getDriver().findElement(By.xpath("//option[text()='Jenkins']")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
        getDashboard().click();
        getWait(5).until(ExpectedConditions.visibilityOf(getDriver().findElement(By.className("dashboard"))));

        Assert.assertTrue(getDriver().findElement(ITEM_ORG_FOLDER).isDisplayed());

        WebElement myFolder = getDriver().findElement(ITEM_FOLDER);
        getWait(5).until(ExpectedConditions.elementToBeClickable(myFolder));
        TestUtils.scrollToElement(getDriver(), myFolder);
        myFolder.click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h2[@class='h4']")).getText(),
                "This folder is empty");
    }

    @Test(dependsOnMethods = "testConfigureOrganizationFolder")
    public void testDeleteOrganizationFolderDependsMethods() {
        HomePage homePage = new HomePage(getDriver())
                .clickOrgFolder(DISPLAY_NAME)
                .clickDeleteOrganizationFolder()
                .clickSaveButton();

        Assert.assertFalse(homePage.getJobList().contains(DISPLAY_NAME));
    }

    @Test(dependsOnMethods = "testCreateOrganizFolder")
    public void testConfigureOrganizationFolder() {
        final String description = TestUtils.getRandomStr();

        OrgFolderStatusPage orgFolderStatusPage = new HomePage(getDriver())
                .clickOrgFolder(NAME_ORG_FOLDER)
                .clickConfigureSideMenu()
                .inputDisplayName(DISPLAY_NAME)
                .inputDescription(description)
                .clickSaveButton();

        Assert.assertEquals(orgFolderStatusPage.getDisplayName(), DISPLAY_NAME);
        Assert.assertEquals(orgFolderStatusPage.getDescription(), description);

        HomePage homePage = orgFolderStatusPage.goToDashboard();

        Assert.assertTrue(homePage.getJobList().contains(DISPLAY_NAME));
    }

    @Test
    public void testCreateOrganizFolder() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(NAME_ORG_FOLDER)
                .selectOrgFolderAndClickOk()
                .clickSaveButton()
                .goToDashboard();

        Assert.assertTrue(homePage.getJobList().contains(NAME_ORG_FOLDER));
    }

    @Test
    public void testOrgFolderCreate() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(nameOrgFolderPOM)
                .selectOrgFolderAndClickOk()
                .clickSaveButton()
                .goToDashboard();

        Assert.assertTrue(homePage.getJobList().contains(nameOrgFolderPOM));
    }

    @Test
    public void testFolderCreate() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(nameFolderPOM)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobList().contains(nameFolderPOM));
    }

    @Test(dependsOnMethods = {"testFolderCreate", "testOrgFolderCreate"})
    public void testMoveOrgFolderToFolder() {
        HomePage homePage = new HomePage(getDriver())
                .clickOrgFolder(nameOrgFolderPOM)
                .clickMoveButton()
                .selectFolder(nameFolderPOM)
                .clickMoveForOrgFolder()
                .goToDashboard();

        Assert.assertFalse(homePage.getJobList().contains(nameOrgFolderPOM));

        FolderStatusPage folderStatusPage = homePage.clickFolder(nameFolderPOM);

        Assert.assertTrue(folderStatusPage.getJobList().contains(nameOrgFolderPOM));
    }
}
