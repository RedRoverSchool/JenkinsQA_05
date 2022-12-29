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
    private static final String DISPLAY_NAME = TestUtils.getRandomStr();
    private static final By INPUT_NAME = By.xpath("//input [@name = 'name']");
    private static final By ORGANIZATION_FOLDER = By.xpath("//li[@class = 'jenkins_branch_OrganizationFolder']");
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By APPLY_BUTTON = By.id("yui-gen13-button");

    private WebElement notificationSaved() {
        return getDriver().findElement(By.cssSelector("#notification-bar"));
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(5));
    }

    @Test
    public void testCreateOrganizationFolder() {
        String actualOrgFolderDisplayName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(uniqueOrganizationFolderName)
                .selectOrgFolderAndClickOk()
                .clickSaveBtn(OrgFolderStatusPage.class)
                .getNameText();

        Assert.assertEquals(actualOrgFolderDisplayName, uniqueOrganizationFolderName);
    }

    @Test
    public void testRenameOrganizationFolder() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(nameOrgFolderPOM)
                .selectOrgFolderAndClickOk()
                .clickSaveBtn(OrgFolderStatusPage.class)
                .clickRenameSideMenu()
                .clearFieldAndInputNewName("New name " + nameOrgFolderPOM)
                .clickRenameButton()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains("New name " + nameOrgFolderPOM));
    }

    @Test
    public void testCreateOrgFolder() {
        List<String> allFolders = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ORG_FOLDER_NAME_CREATE)
                .selectFolderAndClickOk()
                .clickSaveBtn(FolderStatusPage.class)
                .clickDashboard()
                .getJobNamesList();

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
    public void testCreateOrgFolderWithEmptyName() {
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

    @Test(dependsOnMethods = "testConfigureOrganizationFolder")
    public void testDeleteOrganizationFolderDependsMethods() {
        HomePage homePage = new HomePage(getDriver())
                .clickOrgFolder(DISPLAY_NAME)
                .clickDeleteOrganizationFolder()
                .clickSaveButton();

        Assert.assertFalse(homePage.getJobNamesList().contains(DISPLAY_NAME));
    }

    @Test(dependsOnMethods = "testCreateOrganizFolder")
    public void testConfigureOrganizationFolder() {
        final String description = TestUtils.getRandomStr();

        OrgFolderStatusPage orgFolderStatusPage = new HomePage(getDriver())
                .clickOrgFolder(NAME_ORG_FOLDER)
                .clickConfigureSideMenu()
                .inputDisplayName(DISPLAY_NAME)
                .inputDescription(description)
                .clickSaveBtn(OrgFolderStatusPage.class);

        Assert.assertEquals(orgFolderStatusPage.getNameText(), DISPLAY_NAME);
        Assert.assertEquals(orgFolderStatusPage.getFolderDescriptionText(), description);

        HomePage homePage = orgFolderStatusPage.clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(DISPLAY_NAME));
    }

    @Test
    public void testCreateOrganizFolder() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(NAME_ORG_FOLDER)
                .selectOrgFolderAndClickOk()
                .clickSaveBtn(OrgFolderStatusPage.class)
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(NAME_ORG_FOLDER));
    }

    @Test
    public void testOrgFolderCreate() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(nameOrgFolderPOM)
                .selectOrgFolderAndClickOk()
                .clickSaveBtn(OrgFolderStatusPage.class)
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(nameOrgFolderPOM));
    }

    @Test
    public void testFolderCreate() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(nameFolderPOM)
                .selectFolderAndClickOk()
                .clickSaveBtn(FolderStatusPage.class)
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(nameFolderPOM));
    }

    @Test(dependsOnMethods = {"testFolderCreate", "testOrgFolderCreate"})
    public void testMoveOrgFolderToFolder() {
        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickOrgFolder(nameOrgFolderPOM)
                .clickMoveButton()
                .selectFolder(nameFolderPOM)
                .clickMove()
                .clickDashboard()
                .clickFolder(nameFolderPOM);

        Assert.assertTrue(folderStatusPage.getJobList().contains(nameOrgFolderPOM));
    }

    @Test(dependsOnMethods = "testMoveOrgFolderToFolder")
    public void testMoveOrgFolderToDashboard() {
        HomePage homePage = new HomePage(getDriver())
                .clickFolder(nameFolderPOM)
                .clickOrgFolder(nameOrgFolderPOM)
                .clickMoveButton()
                .selectOptionToDashBoard()
                .clickMove()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(nameOrgFolderPOM));
    }

}
