package tests;

import model.HomePage;
import model.multiconfiguration.MultiConfigurationProjectStatusPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.time.Duration;
import java.util.List;

public class MulticonfigurationProjectTest extends BaseTest {
    private static final String PROJECT_NAME = TestUtils.getRandomStr(8);
    private static final String NEW_PROJECT_NAME = TestUtils.getRandomStr(8);
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By DASHBOARD = By.xpath("//img[@id='jenkins-head-icon']");
    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By SAVE_BUTTON = By.xpath("//button[@type='submit']");
    private static final By INPUT_NAME = By.id("name");
    private static final By CONFIGURE = By.xpath(String.format("//a[@href='/job/%s/configure']", PROJECT_NAME));
    private static final By DISABLE_PROJECT = By.id("yui-gen1-button");
    private static final By ENABLE_PROJECT_BUTTON = By.xpath("//button[normalize-space()='Enable'][1]");
    private WebDriverWait wait;
    private static final By MULTI_CONFIGURATION_PROJECT = By.cssSelector(".hudson_matrix_MatrixProject");

    private void deleteNewMCProject(String project) {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", project))).click();
        getDriver().findElement(By.xpath(String.format("//a[@href = contains(., '%s')]/button", project))).click();
        getDriver().findElement(
                By.xpath("//div[@id = 'tasks']//span[contains(text(), 'Delete Multi-configuration project')]")).click();
        getDriver().switchTo().alert().accept();
    }

    @Test
    public void testCreateMultiConfigurationProjectWithValidName() {

        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSave()
                .goToDashboard();

        Assert.assertTrue(homePage.getJobList().contains(PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectWithValidName")
    public void testMulticonfigurationProjectAddDescription() {

        MultiConfigurationProjectStatusPage multConfPage = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .clickAddDescription()
                .fillDescription("Description")
                .clickSave();

        Assert.assertEquals(multConfPage.getDescriptionText(), "Description");
    }

    @Ignore
    @Test(dependsOnMethods = "testMulticonfigurationProjectAddDescription")
    public void testMultiConfigurationProjectRenameProjectViaDropDownMenu() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        getDriver().findElement(
                By.xpath("//a[@class='jenkins-table__link model-link inside']//button[@class='jenkins-menu-dropdown-chevron']")).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//span[text() = 'Rename']"))).click();
        getDriver().findElement(By.xpath(String.format("//input[@value='%s']", PROJECT_NAME))).clear();
        getDriver().findElement(By.xpath(String.format("//input[@value='%s']", PROJECT_NAME))).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.id("yui-gen1-button")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath(String.format("//h1[contains(text(),'Project %s')]", NEW_PROJECT_NAME))).getText(), String.format("Project %s", NEW_PROJECT_NAME));
    }

    @Ignore
    @Test(dependsOnMethods = "testMultiConfigurationProjectRenameProjectViaDropDownMenu")
    public void testMultiConfigurationProjectRenameProjectViaSideMenu() {

        getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", NEW_PROJECT_NAME))).click();
        getDriver().findElement(By.xpath(String.format("//a[@href='/job/%s/confirm-rename']", NEW_PROJECT_NAME))).click();
        getDriver().findElement(By.xpath(String.format("//input[@value='%s']", NEW_PROJECT_NAME))).clear();
        getDriver().findElement(By.xpath(String.format("//input[@value='%s']", NEW_PROJECT_NAME))).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[contains(text(),'Rename')]")).click();

        Assert.assertEquals(getDriver().findElement(
                        By.xpath(String.format("//h1[contains(text(),'Project %s')]", PROJECT_NAME))).getText(),
                String.format("Project %s", PROJECT_NAME));
    }

    @Ignore
    @Test
    public void testMultiConfigurationProjectDelete() {

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        getDriver().findElement(By.xpath("//tr[@id = 'job_FirstMultiProject']/descendant::td//button")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Delete Multi-configuration project')]")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }

    @Ignore
    @Test(dependsOnMethods = "testMulticonfigurationProjectAddDescription")
    public void testMultiConfigurationProjectDisable() {
        getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", PROJECT_NAME))).click();
        getDriver().findElement(CONFIGURE).click();
        getDriver().findElement(By.xpath("//label[@for='enable-disable-project']")).click();
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertTrue(getDriver().findElement(By.id("enable-project"))
                .getText().contains("This project is currently disabled"));
    }

    @Ignore
    @Test(dependsOnMethods = "testMultiConfigurationProjectDisable")
    public void testMultiConfigurationProjectEnable() {
        getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", PROJECT_NAME))).click();
        getDriver().findElement(CONFIGURE).click();
        getDriver().findElement(By.xpath("//label[@for='enable-disable-project']")).click();
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//button[contains(text(),'Disable Project')]")).isDisplayed());

        deleteNewMCProject(PROJECT_NAME);
    }

    @Test
    public void testCreateMultiConfigurationProjectWithDescription() {
        final String nameMCP = "MultiConfigProject000302";
        final String descriptionMCP = "Description000302";

        MultiConfigurationProjectStatusPage multiConfigProject = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(nameMCP)
                .selectMultiConfigurationProjectAndClickOk()
                .inputDescription(descriptionMCP)
                .showPreview()
                .clickSaveButton()
                .goToDashboard()
                .clickMultConfJobName(nameMCP);

        MultiConfigurationProjectStatusPage multiConfigProjectPreview = new MultiConfigurationProjectStatusPage(getDriver());

        Assert.assertEquals(multiConfigProject.getNameMultiConfigProject(nameMCP), nameMCP);
        Assert.assertEquals(multiConfigProject.getDescriptionText(), descriptionMCP);
        Assert.assertEquals(multiConfigProjectPreview.getDescriptionText(), descriptionMCP);

        multiConfigProject.deleteMultiConfigProject();
    }

    @Ignore
    @Test
    public void testMultiConfigurationProjectBuild() {
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(INPUT_NAME).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[contains(text(), 'Multi-configuration project')]")).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD).click();

        getDriver().findElement(By.xpath("//a[@href='job/" + NEW_PROJECT_NAME + "/']")).click();
        List<WebElement> build_row_before_build = getDriver().findElements(By.xpath("//tr[@page-entry-id]"));
        int amountOfBuildsBeforeBuildNow = build_row_before_build.size();

        getDriver().findElement(By.linkText("Build Now")).click();

        List<WebElement> build_row_after_build = getDriver().findElements(By.xpath("//tr[@page-entry-id]"));
        int amountOfBuildsAfterBuildNow = build_row_after_build.size();

        Assert.assertNotEquals(amountOfBuildsAfterBuildNow, amountOfBuildsBeforeBuildNow);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectWithValidName")
    public void testCreateNewMCProjectAsCopyFromExistingProject() {

        String actualProjectName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(NEW_PROJECT_NAME)
                .setCopyFromItemName(PROJECT_NAME)
                .clickOK()
                .clickSave()
                .goToDashboard()
                .getJobName(NEW_PROJECT_NAME);

        Assert.assertEquals(actualProjectName, NEW_PROJECT_NAME);
    }

    @Ignore
    @Test
    public void testCreateMultiConfigurationProjectDisabled() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(MulticonfigurationProjectTest.PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[text()='Multi-configuration project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id='toggle-switch-enable-disable-project']/label/span[2]")).click();
        getDriver().findElement(
                By.xpath("//span[@class='yui-button yui-submit-button submit-button primary']")).click();

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']")).click();
        String actualStatus = getDriver().findElement(By.xpath("//*[@id='enable-project']")).getText();

        Assert.assertEquals(actualStatus, "This project is currently disabled\nEnable");
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectWithValidName")
    public void testFindMultiConfigurationProject() {
        getDriver().findElement(By.cssSelector("#search-box")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.cssSelector("#search-box")).sendKeys(Keys.ENTER);
        getDriver().findElement(By.xpath("//div/ul/li/a[text()='" + PROJECT_NAME + "']"));

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/ul/li/a[text()='" + PROJECT_NAME + "']")).getText(),
                PROJECT_NAME);
    }

    @Test
    public void testDisableMultiConfigurationProjectCheckIconDashboardPage() {
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(INPUT_NAME).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[contains(text(), 'Multi-configuration project')]")).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", PROJECT_NAME))).click();
        getDriver().findElement(DISABLE_PROJECT).click();
        getDriver().findElement(DASHBOARD).click();

        Assert.assertTrue(getDriver().findElement((By.xpath(
                        String.format("//tr[@id='job_%s']//span[@class='build-status-icon__wrapper icon-disabled icon-md']", PROJECT_NAME))))
                .isDisplayed());
    }

    @Test(dependsOnMethods = "testDisableMultiConfigurationProjectCheckIconDashboardPage")
    public void testEnableMultiConfigurationProjectCheckIconDashboardPage() {
        getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", PROJECT_NAME))).click();
        getDriver().findElement(ENABLE_PROJECT_BUTTON).click();
        getDriver().findElement(DASHBOARD).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//span/span/*[name()='svg' and @tooltip='Not built']"))
                .isDisplayed());
    }

    @Test
    public void testMultiConfigurationProjectRenameToInvalidNameViaSideMenu() {

        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(INPUT_NAME).sendKeys("MC Project");
        getDriver().findElement
                (By.xpath("//span[text()='Multi-configuration project']")).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'confirm-rename')]")).click();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys("&");
        getDriver().findElement(By.id("yui-gen1-button")).click();

        Assert.assertEquals(getDriver().findElement(By.id
                ("main-panel")).getText(), "Error\n‘&amp;’ is an unsafe character");

        deleteNewMCProject("MC Project");
    }

    @Ignore
    @Test(dependsOnMethods = "testMultiConfigurationProjectBuild")
    public void testMultiConfigurationProjectsRunJobInBuildHistory() {

        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();
        getDriver().findElement(By.xpath("//a[@href='/user/admin/my-views/view/all/builds']")).click();
        List<WebElement> buildsInTable = getDriver().findElements(By.xpath
                ("//div[contains(@id,'label-tl')]"));
        for (WebElement buildName : buildsInTable) {
            Assert.assertTrue(buildName.getText().contains(NEW_PROJECT_NAME));
        }

        Assert.assertEquals(getDriver().findElement(
                        By.xpath("//a[@href='/job/" + NEW_PROJECT_NAME + "/default/']")).getText(),
                NEW_PROJECT_NAME + " » default");
        Assert.assertEquals(getDriver().findElement(
                        By.xpath("//a[@href='/job/" + NEW_PROJECT_NAME + "/']")).getText(),
                NEW_PROJECT_NAME);
    }

    @Test
    public void testDisableMultiConfigurationProjectCheckIconProjectName() {
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(INPUT_NAME).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[contains(text(), 'Multi-configuration project')]")).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", PROJECT_NAME))).click();
        getDriver().findElement(DISABLE_PROJECT).click();

        Assert.assertTrue(getDriver().findElement(
                        By.xpath("//span[@class='build-status-icon__wrapper icon-disabled icon-md']"))
                .isDisplayed());
    }

    @Test
    public void testMultiConfigurationProjectConfigureParams() {
        String multiConfProjectName = TestUtils.getRandomStr(5);
        String multiConfProjectDescriptionText = TestUtils.getRandomStr(10);
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(INPUT_NAME).sendKeys(multiConfProjectName);
        getDriver().findElement(MULTI_CONFIGURATION_PROJECT).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath("//tr[@id='job_" + multiConfProjectName + "']//td[3]//a")).click();
        getDriver().findElement(By.linkText("Configure")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(multiConfProjectDescriptionText);
        getDriver().findElement(SAVE_BUTTON).click();
        String actualDescText = getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText();

        Assert.assertEquals(actualDescText, multiConfProjectDescriptionText);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectDisabled")
    public void testEnableDisabledMultiConfigurationProject() {
        getDriver().findElement(By.xpath("//span[text()='" + PROJECT_NAME + "']")).click();
        getDriver().findElement(By.xpath("//*[@id='yui-gen1-button']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='yui-gen1-button']")).getText(),
                "Disable Project");
    }

    @Test(dependsOnMethods = "testDisableMultiConfigurationProjectCheckIconProjectName")
    public void testEnableMultiConfigurationProjectCheckIconProjectName() {
        getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", PROJECT_NAME))).click();
        getDriver().findElement(ENABLE_PROJECT_BUTTON).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//span/span/*[name()='svg' and @tooltip='Not built']"))
                .isDisplayed());
    }

    @Test
    public void testDisableMultiConfigurationProject() {
        Boolean projectIconText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSave()
                .clickDisableButton()
                .goToDashboard()
                .getProjectIconText();

        Assert.assertTrue(projectIconText);
    }

    @Ignore
    @Test(dependsOnMethods = "testDisableMultiConfigurationProject")
    public void testEnableMultiConfigurationProject() {
        Boolean buildNowButton = new HomePage(getDriver())
                .clickProject(PROJECT_NAME)
                .clickEnableButton()
                .goToDashboard()
                .clickProjectDropdownMenu(PROJECT_NAME);

        Assert.assertTrue(buildNowButton);
    }
}
