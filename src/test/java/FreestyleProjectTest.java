import model.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static runner.TestUtils.getRandomStr;

public class FreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_NAME = getRandomStr(10);
    private static final String NEW_FREESTYLE_NAME = getRandomStr(10);
    private static final String VALID_FREESTYLE_PROJECT_NAME = "First project";
    private static final Character INVALID_CHAR = '!';
    private static final String INVALID_FREESTYLE_PROJECT_NAME = INVALID_CHAR + VALID_FREESTYLE_PROJECT_NAME;
    private static final String SPACE_INSTEAD_OF_NAME = " ";
    private static final By BY_BUTTON_ADD_NEW_ITEM = By.linkText("New Item");
    private static final By BY_FIELD_ENTER_NAME = By.id("name");
    private static final By BY_BUTTON_SELECT_FREESTYLE_PROJECT = By.cssSelector(".hudson_model_FreeStyleProject");
    private static final By BY_BUTTON_OK = By.cssSelector("#ok-button");
    private static final By BY_ITEM_NAME_INVALID_MESSAGE = By.cssSelector("#itemname-invalid");
    private static final By BY_NEW_ITEM_NAME_HEADLINE = By.xpath("//h1");
    private static final By BY_SIDE_PANEL_BUILD_NOW = By.linkText("Build Now");
    private static final By BY_BUILD_ROW = By.xpath("//table[@class='pane jenkins-pane stripped']//tr[@page-entry-id]");
    private static final By BY_GO_TO_DASHBOARD_LINK = By.linkText("Dashboard");
    private static final By BY_JENKINS_CURRENT_VERSION = By.xpath("//a [@rel = 'noopener noreferrer']");
    private static final By BY_CONFIGURATION_PAGE_HEADLINE = By.xpath("//div[@class = 'jenkins-app-bar__content']/h1");
    private static final By BY_DROPDOWN_DELETE_PROJECT = By.xpath("//span[contains(text(),'Delete Project')]");

    private void alertAccept() {
        getDriver().switchTo().alert().accept();
    }

    private void deleteFreestyleProject(String projectName) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(BY_GO_TO_DASHBOARD_LINK)).click();
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href = 'job/" + projectName + "/']"))).click();
        getDriver().findElement(BY_DROPDOWN_DELETE_PROJECT).click();
        alertAccept();
    }

    @Test
    public void testCreateNewFreestyleProject() {
        final String freestyleProjectTitle = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(FREESTYLE_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn()
                .getHeadlineText();

        Assert.assertEquals(freestyleProjectTitle, String.format("Project %s", FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithSpacesInsteadOfName")
    public void testCreateFreestyleProjectWithIncorrectCharacters() {
        final List<Character> incorrectNameCharacters = List.of('!', '@', '#', '$', '%', '^', '&', '*', '[', ']', '\\', '|', ';', ':', '/', '?', '<', '>');
        NewItemPage newItemPage = new HomePage(getDriver()).clickNewItem();

        for (Character character : incorrectNameCharacters) {
            newItemPage.clearItemName()
                    .setProjectName(String.valueOf(character))
                    .selectFreestyleProject();

            Assert.assertEquals(newItemPage.getItemNameInvalidMsg(), String.format("» ‘%s’ is an unsafe character", character));
        }
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithIncorrectCharacters")
    public void testDisableProject() {
        FreestyleProjectStatusPage freestyleProjectStatusPage = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_NAME)
                .clickDisableProjectBtn();

        Assert.assertEquals(freestyleProjectStatusPage.getHeadlineText(), String.format("Project %s", FREESTYLE_NAME));
        Assert.assertEquals(freestyleProjectStatusPage.getWarningMsg(), "This project is currently disabled");

        HomePage homePage = freestyleProjectStatusPage.clickDashboard();
        Assert.assertEquals(homePage.getJobBuildStatus(FREESTYLE_NAME), "Disabled");
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testEnableProject() {
        final String jobStatusIconTooltip = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_NAME)
                .clickDisableProjectBtn()
                .clickDashboard()
                .getJobBuildStatus(FREESTYLE_NAME);

        Assert.assertEquals(jobStatusIconTooltip, "Not built");
    }

    @Test(dependsOnMethods = "testEnableProject")
    public void testFreestyleProjectPageIsOpenedFromDashboard() {
        final FreestyleProjectStatusPage freestyleProjectStatusPage = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_NAME);

        Assert.assertEquals(freestyleProjectStatusPage.getHeadlineText(), String.format("Project %s", FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testFreestyleProjectPageIsOpenedFromDashboard")
    public void testAddDescriptionToFreestyleProject() {
        final String descriptionText = "This is job #" + FREESTYLE_NAME;

        String freestyleProjectDescription = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickButtonAddDescription()
                .inputAndSaveDescriptionText(descriptionText)
                .getDescriptionText();

        Assert.assertEquals(freestyleProjectDescription, descriptionText);
    }

    @Test(dependsOnMethods = "testAddDescriptionToFreestyleProject")
    public void testEditFreestyleProjectWithDescription() {
        final String newDescription = "It's new description to job";

        FreestyleProjectStatusPage page = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickButtonEditDescription()
                .inputAndSaveDescriptionText(newDescription);

        Assert.assertEquals(page.getDescriptionText(), newDescription);
    }

    @Test(dependsOnMethods = "testEditFreestyleProjectWithDescription")
    public void testNoBuildFreestyleProjectChanges() {
        ChangesBuildsPage page = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickLinkChanges();

        Assert.assertEquals(page.getPageText(), "Changes\nNo builds.");
    }

    @Test(dependsOnMethods = "testNoBuildFreestyleProjectChanges")
    public void testRenameFreestyleProject() {

        List<String> jobsList = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickRenameButton()
                .clearFieldAndInputNewName(NEW_FREESTYLE_NAME)
                .clickSubmitButton()
                .clickDashboard()
                .getJobList();

        Assert.assertFalse(jobsList.contains(FREESTYLE_NAME));
        Assert.assertTrue(jobsList.contains(NEW_FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testRenameFreestyleProject")
    public void testViewFreestyleProjectPage() {
        getDriver().findElement(By.linkText(NEW_FREESTYLE_NAME)).click();

        Assert.assertEquals(getDriver().findElement(BY_NEW_ITEM_NAME_HEADLINE).getText(), String.format("Project %s", NEW_FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testViewFreestyleProjectPage")
    public void testFreestyleProjectConfigureByDropdown() {
        FreestyleProjectConfigPage freestyleProjectConfigPage = new HomePage(getDriver())
                .clickJobDropDownMenu(NEW_FREESTYLE_NAME)
                .clickConfigDropDownMenuFreestyle();

        Assert.assertEquals(freestyleProjectConfigPage.getHeadlineText(), "Configuration");
    }

    @Test(dependsOnMethods = "testFreestyleProjectConfigureByDropdown")
    public void testCreateNewFreestyleProjectWithDuplicateName() {

        String actualResult = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(NEW_FREESTYLE_NAME)
                .selectFreestyleProject()
                .getItemNameInvalidMsg();

        Assert.assertEquals(actualResult, String.format("» A job already exists with the name ‘%s’", NEW_FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testFreestyleProjectBuild")
    public void testDeleteFreestyleProject() {

        String pageHeaderText = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickButtonDeleteProject()
                .confirmAlertAndDeleteProject()
                .getHeaderText();

        Assert.assertEquals(pageHeaderText, "Welcome to Jenkins!");
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithDuplicateName")
    public void testCreateBuildNowOnFreestyleProjectPage() {
        int countBuildsBeforeCreatingNewBuild = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .openBuildHistoryOnSidePanel()
                .countBuildsOnSidePanel();
        int countBuildsAfterCreatingNewBuild = new FreestyleProjectStatusPage(getDriver())
                .clickBuildNowOnSidePanel()
                .countBuildsOnSidePanel();

        Assert.assertEquals(countBuildsAfterCreatingNewBuild, countBuildsBeforeCreatingNewBuild + 1);
    }

    @Test(dependsOnMethods = "testCreateBuildNowOnFreestyleProjectPage")
    public void testFreestyleProjectBuild() {
        getDriver().findElement(By.linkText(NEW_FREESTYLE_NAME)).click();

        final int initialBuildCount = getDriver().findElements(BY_BUILD_ROW).size();
        getDriver().findElement(BY_SIDE_PANEL_BUILD_NOW).click();
        final int actualBuildCount = getWait(20).until(ExpectedConditions.numberOfElementsToBeMoreThan(BY_BUILD_ROW, initialBuildCount)).size();

        Assert.assertEquals(actualBuildCount, initialBuildCount + 1);

    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProject")
    public void testFreestyleConfigSideMenu() {

        final Set<String> expectedFreestyleConfigSideMenu = new TreeSet<>(List.of("General", "Source Code Management", "Build Triggers", "Build Environment", "Build Steps", "Post-build Actions"));

        Set<String> actualFreestyleConfigSideMenu = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickSideMenuConfigure()
                .collectFreestyleConfigSideMenu();

        Assert.assertEquals(actualFreestyleConfigSideMenu, expectedFreestyleConfigSideMenu);
    }

    @Test(dependsOnMethods = "testFreestyleConfigSideMenu")
    public void testCreateFreestyleProjectWithEmptyName() {
        NewItemPage newItemPage = new HomePage(getDriver()).clickNewItem().selectFreestyleProject();

        Assert.assertEquals(newItemPage.getItemNameRequiredMsg(), "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void testCreateFreestyleProjectWithInvalidCharBeforeName() {
        getDriver().findElement(BY_BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(BY_FIELD_ENTER_NAME).sendKeys(INVALID_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(BY_BUTTON_SELECT_FREESTYLE_PROJECT).click();

        Assert.assertEquals(getWait(20).until(ExpectedConditions.presenceOfElementLocated(BY_ITEM_NAME_INVALID_MESSAGE)).getText(),
                "» ‘" + INVALID_CHAR + "’ is an unsafe character");
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithEmptyName")
    public void testCreateFreestyleProjectWithSpacesInsteadOfName() {
        FreestyleProjectConfigPage freestyleProjectConfigPage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(SPACE_INSTEAD_OF_NAME)
                .selectFreestyleProjectAndClickOk();

        Assert.assertEquals(freestyleProjectConfigPage.getHeadlineText(), "Error");
        Assert.assertEquals(freestyleProjectConfigPage.getErrorMsg(), "No name is specified");
    }

    @Test
    public void testAccessProjectConfigurationFromTheProjectPage() {
        final String NAME_FREESTYLE_PROJECT_TC010401 = NEW_FREESTYLE_NAME + "TC010401";
        final By FIND_NAME_FREESTYLE_PROJECT_TC010401 =
                By.xpath("//a[@href = 'job/" + NAME_FREESTYLE_PROJECT_TC010401 + "/']");
        final By CONFIG_NAME_FREESTYLE_PROJECT_TC010401 =
                By.xpath("//a[@href='/job/" + NAME_FREESTYLE_PROJECT_TC010401 + "/configure']");

        getWait(10).until(ExpectedConditions.elementToBeClickable(BY_BUTTON_ADD_NEW_ITEM)).click();
        getWait(10).until(ExpectedConditions.presenceOfElementLocated(BY_FIELD_ENTER_NAME)).
                sendKeys(NAME_FREESTYLE_PROJECT_TC010401);
        getDriver().findElement(BY_BUTTON_SELECT_FREESTYLE_PROJECT).click();
        getDriver().findElement(BY_BUTTON_OK).click();
        getDriver().findElement(BY_GO_TO_DASHBOARD_LINK).click();
        getWait(5).until(ExpectedConditions.elementToBeClickable(FIND_NAME_FREESTYLE_PROJECT_TC010401)).click();
        getDriver().findElement(CONFIG_NAME_FREESTYLE_PROJECT_TC010401).click();

        Assert.assertTrue(getDriver().findElement(BY_CONFIGURATION_PAGE_HEADLINE).getText().contains("Configuration"));
        Assert.assertTrue(getDriver().findElement(BY_JENKINS_CURRENT_VERSION).getText().contains("Jenkins 2.361.4"));

        deleteFreestyleProject(NAME_FREESTYLE_PROJECT_TC010401);
    }

    @Test
    public void testCreateNewFreestyleProjectWithLongNameFrom256Characters() {
        final String expectedURL = "view/all/createItem";
        final String expectedTextOfError = "A problem occurred while processing the request.";

        CreateItemErrorPage errorPage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(getRandomStr(256))
                .selectFreestyleProjectAndClickOkWithError();

        Assert.assertTrue(errorPage.getPageUrl().endsWith(expectedURL));
        Assert.assertTrue(errorPage.isErrorPictureDisplayed());
        Assert.assertEquals(errorPage.getErrorDescription(), expectedTextOfError);
    }
}
