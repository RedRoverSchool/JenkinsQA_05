package tests;

import model.HomePage;
import model.NewItemPage;
import model.RenameItemErrorPage;
import model.multiconfiguration.ConsoleOutputMultiConfigurationProjectPage;
import model.multiconfiguration.MultiConfigurationProjectStatusPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.List;

public class MulticonfigurationProjectTest extends BaseTest {
    private static final String PROJECT_NAME = TestUtils.getRandomStr(8);
    private static final String NEW_PROJECT_NAME = TestUtils.getRandomStr(8);
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By DASHBOARD = By.xpath("//img[@id='jenkins-head-icon']");
    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By SAVE_BUTTON = By.xpath("//button[@type='submit']");
    private static final By INPUT_NAME = By.id("name");
    private static final By DISABLE_PROJECT = By.id("yui-gen1-button");
    private static final By ENABLE_PROJECT_BUTTON = By.xpath("//button[normalize-space()='Enable'][1]");
    private static final By MULTI_CONFIGURATION_PROJECT = By.cssSelector(".hudson_matrix_MatrixProject");

    @Test
    public void testCreateMultiConfigurationProjectWithValidName() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveBtn(MultiConfigurationProjectStatusPage.class)
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectWithValidName")
    public void testMulticonfigurationProjectAddDescription() {
        MultiConfigurationProjectStatusPage multConfPage = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .clickAddDescription()
                .fillDescription("Description")
                .clickSave();

        Assert.assertEquals(multConfPage.getProjectDescriptionText(), "Description");
    }

    @Test(dependsOnMethods = "testMulticonfigurationProjectAddDescription")
    public void testMultiConfigurationProjectRenameProjectViaDropDownMenu() {
        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickJobDropdownMenu(PROJECT_NAME)
                .clickRenameMultiConfigurationDropDownMenu()
                .clearFieldAndInputNewName(NEW_PROJECT_NAME)
                .clickRenameButton();

        Assert.assertEquals(multiConfigPrStatusPage.getNameMultiConfigProject(NEW_PROJECT_NAME), NEW_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectRenameProjectViaDropDownMenu")
    public void testMultiConfigurationProjectRenameProjectViaSideMenu() {
        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickMultConfJobName(NEW_PROJECT_NAME)
                .clickRenameSideMenu()
                .clearFieldAndInputNewName(PROJECT_NAME)
                .clickRenameButton();

        Assert.assertEquals(multiConfigPrStatusPage.getNameMultiConfigProject(PROJECT_NAME), PROJECT_NAME);
    }

    @Test
    public void testMultiConfigurationProjectDelete() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveBtn(MultiConfigurationProjectStatusPage.class)
                .clickDashboard()
                .clickMultConfJobName(PROJECT_NAME)
                .deleteMultiConfigProject();

        Assert.assertFalse(homePage.getJobNamesList().contains(PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectRenameProjectViaSideMenu")
    public void testMultiConfigurationProjectDisable() {
        MultiConfigurationProjectStatusPage multiConfigurationProjectStatusPage = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .clickConfiguration(PROJECT_NAME)
                .clickEnableOrDisableButton()
                .clickSaveBtn(MultiConfigurationProjectStatusPage.class);

        Assert.assertTrue(multiConfigurationProjectStatusPage.getTextDisabledWarning().contains("This project is currently disabled"));
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectDisable")
    public void testMultiConfigurationProjectEnable() {
        MultiConfigurationProjectStatusPage multiConfigurationProjectStatusPage = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .clickConfiguration(PROJECT_NAME)
                .clickEnableOrDisableButton()
                .clickSaveBtn(MultiConfigurationProjectStatusPage.class);

        Assert.assertTrue(multiConfigurationProjectStatusPage.disableButtonIsDisplayed());
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
                .clickSaveBtn(MultiConfigurationProjectStatusPage.class)
                .clickDashboard()
                .clickMultConfJobName(nameMCP);

        MultiConfigurationProjectStatusPage multiConfigProjectPreview = new MultiConfigurationProjectStatusPage(getDriver());

        Assert.assertEquals(multiConfigProject.getNameMultiConfigProject(nameMCP), nameMCP);
        Assert.assertEquals(multiConfigProject.getProjectDescriptionText(), descriptionMCP);
        Assert.assertEquals(multiConfigProjectPreview.getProjectDescriptionText(), descriptionMCP);

    }

    @Test(dependsOnMethods = {"testCreateMultiConfigurationProjectWithValidName",
            "testMulticonfigurationProjectAddDescription",
            "testMultiConfigurationProjectRenameProjectViaDropDownMenu",
            "testMultiConfigurationProjectRenameProjectViaSideMenu"})
    public void testMultiConfigurationProjectBuild() {

        int countBuildsBeforeNewBuild = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .countBuildsOnSidePanel();

        new MultiConfigurationProjectStatusPage(getDriver()).clickBuildNowOnSideMenu(PROJECT_NAME);
        getDriver().navigate().refresh();

        int countBuildsAfterNewBuild = new MultiConfigurationProjectStatusPage(getDriver())
                .countBuildsOnSidePanel();

        Assert.assertNotEquals(countBuildsAfterNewBuild, countBuildsBeforeNewBuild);
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectBuild")
    public void testCreateNewMCProjectAsCopyFromExistingProject() {
        String actualProjectName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(NEW_PROJECT_NAME)
                .setCopyFromItemName(PROJECT_NAME)
                .clickOK()
                .clickSaveBtn(MultiConfigurationProjectStatusPage.class)
                .clickDashboard()
                .getJobName(NEW_PROJECT_NAME);

        Assert.assertEquals(actualProjectName, NEW_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewMCProjectAsCopyFromExistingProject")
    public void testFindMultiConfigurationProject() {
        MultiConfigurationProjectStatusPage multiConfigurationProjectStatusPage = new HomePage(getDriver())
                .setSearchAndClickEnter(NEW_PROJECT_NAME);

        Assert.assertEquals(multiConfigurationProjectStatusPage.getNameMultiConfigProject(NEW_PROJECT_NAME), NEW_PROJECT_NAME);
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

    @Ignore
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
        RenameItemErrorPage renameItemErrorPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveBtn(MultiConfigurationProjectStatusPage.class)
                .clickDashboard()
                .clickMultConfJobName(PROJECT_NAME)
                .clickRenameSideMenu()
                .clearFieldAndInputNewName("&")
                .clickSaveButtonAndGetError();

        Assert.assertEquals(renameItemErrorPage.getErrorMessage(), "‘&amp;’ is an unsafe character");
    }

    @Test(dependsOnMethods = {"testCreateMultiConfigurationProjectWithValidName",
            "testMultiConfigurationProjectBuild"})
    public void testMultiConfigurationProjectsRunJobInBuildHistory() {
        List<String> listNameOfLabels = new HomePage(getDriver())
                .clickDashboard()
                .clickMyViewsSideMenuLink()
                .clickBuildHistory()
                .getNameOfLabelsOnTimeLineBuildHistory();

        Assert.assertTrue(listNameOfLabels.contains(PROJECT_NAME + " #1"));
    }

    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectWithDescription")
    public void testMultiConfigurationProjectDisableCheckIconProjectName() {
        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .clickDisableButton();
        Assert.assertTrue(multiConfigPrStatusPage.iconProjectDisabledIsDisplayed());
    }


    @Test(dependsOnMethods = {"testCreateMultiConfigurationProjectWithDescription",
            "testMultiConfigurationProjectDisableCheckIconProjectName"})
    public void testMultiConfigurationProjectEnableCheckIconProjectName() {
        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .clickEnableButton();

        Assert.assertTrue(multiConfigPrStatusPage.iconProjectEnabledIsDisplayed());
    }

    @Ignore
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

    @Test
    public void testDisableMultiConfigurationProject() {
        Boolean projectIconText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveBtn(MultiConfigurationProjectStatusPage.class)
                .clickDisableButton()
                .clickDashboard()
                .getProjectIconText();

        Assert.assertTrue(projectIconText);
    }

    @Test(dependsOnMethods = "testDisableMultiConfigurationProject")
    public void testEnableMultiConfigurationProject() {
        HomePage buildNowButton = new HomePage(getDriver())
                .clickProject(PROJECT_NAME)
                .clickEnableButton()
                .clickDashboard()
                .clickProjectDropdownMenu(PROJECT_NAME);

        Assert.assertTrue(buildNowButton.buildNowButtonIsDisplayed());
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectWithValidName")
    public void testMultiConfigurationProjectCheckConsoleOutput() {
        ConsoleOutputMultiConfigurationProjectPage multiConfigProjectConsole = new HomePage(getDriver())
                .clickProject(PROJECT_NAME)
                .clickConfiguration(PROJECT_NAME)
                .scrollAndClickBuildSteps()
                .selectionAndClickExecuteWindowsFromBuildSteps().enterCommandInBuildSteps("echo Hello world!")
                .clickSaveBtn(MultiConfigurationProjectStatusPage.class)
                .clickBuildNowButton()
                .clickDropDownBuildIcon()
                .selectAndClickConsoleOutput();

        Assert.assertEquals(multiConfigProjectConsole.getTextConsoleOutputUserName(), "admin");
        Assert.assertTrue(multiConfigProjectConsole.getTextConsoleOutput().contains("Finished: SUCCESS"));
    }

    @Ignore
    @Test
    public void testNewestBuildsButton() {
        new HomePage(getDriver()).clickNewItem();
        MultiConfigurationProjectStatusPage newMultiConfigItem = new NewItemPage(getDriver())
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveBtn(MultiConfigurationProjectStatusPage.class);
        MultiConfigurationProjectStatusPage mcpStatusPage = new MultiConfigurationProjectStatusPage(getDriver());
        mcpStatusPage.multiConfigurationProjectBuildNow(getDriver());
        mcpStatusPage.multiConfigurationProjectNewestBuilds(getDriver());

        Assert.assertTrue(getDriver().
                findElement(By.xpath("//*[@id=/'buildHistory/']/div[2]/table/tbody/tr[2]")).isDisplayed());
    }
}
