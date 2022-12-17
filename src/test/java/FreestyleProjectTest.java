import model.*;
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
        String freestyleName = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .getHeadlineText();

        Assert.assertEquals(freestyleName, String.format("Project %s", NEW_FREESTYLE_NAME));
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

    @Test(dependsOnMethods = "testCreateBuildNowOnFreestyleProjectPage")
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
        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItem()
                .selectFreestyleProject();

        Assert.assertEquals(newItemPage.getItemNameRequiredMsg(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(newItemPage.isOkButtonEnabled());
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithEmptyName")
    public void testCreateFreestyleProjectWithSpacesInsteadOfName() {
        FreestyleProjectConfigPage freestyleProjectConfigPage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(" ")
                .selectFreestyleProjectAndClickOk();

        Assert.assertEquals(freestyleProjectConfigPage.getHeadlineText(), "Error");
        Assert.assertEquals(freestyleProjectConfigPage.getErrorMsg(), "No name is specified");
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

    @Test
    public void testConfigureJobAsParameterized() {
        final String stringParameterName = "Held post";
        final String stringParameterDefaultValue = "Manager";
        final String choiceParameterName = "Employee_name";
        final String choiceParameterValues = "John Smith\nJane Dow";
        final String booleanParameterName = "Employed";
        final String pageNotification = "This build requires parameters:";

        BuildWithParametersPage page = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(NEW_FREESTYLE_NAME)
                .selectFreestyleProjectAndClickOk()
                .switchONCheckBoxThisProjectIsParametrized()
                .clickButtonAddParameter()
                .selectStringParameter()
                .inputStringParameterName(stringParameterName)
                .inputStringParameterDefaultValue(stringParameterDefaultValue)
                .scrollAndClickAddParameterButton()
                .selectChoiceParameter()
                .inputChoiceParameterName(choiceParameterName)
                .inputChoiceParameterValue(choiceParameterValues)
                .scrollAndClickAddParameterButton()
                .selectBooleanParameter()
                .inputBooleanParameterName(booleanParameterName)
                .switchONBooleanParameterAsDefault()
                .clickSaveButton()
                .clickButtonBuildWithParameters();

        Assert.assertEquals(page.getProjectName(), NEW_FREESTYLE_NAME);
        Assert.assertEquals(page.getPageNotificationText(), pageNotification);
        Assert.assertEquals(page.getFirstParamName(), stringParameterName);
        Assert.assertEquals(page.getFirstParamValue(), stringParameterDefaultValue);
        Assert.assertEquals(page.getSecondParamName(), choiceParameterName);
        Assert.assertEquals(page.selectedParametersValues(), choiceParameterValues);
        Assert.assertEquals(page.getThirdParamName(), booleanParameterName);
        Assert.assertTrue(page.isBooleanParameterDefaultOn());
    }

    @Test(dependsOnMethods = "testConfigureJobAsParameterized")
    public void testConfigureSourceCodeByGIT() {
        final String repositoryURL = "https://github.com/RedRoverSchool/JenkinsQA_05.git";
        final String branchSpecifier = "main";

        HomePage page = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickSideMenuConfigureLink()
                .switchOFFCheckBoxThisProjectIsParametrized()
                .clickLinkSourceCodeManagement()
                .selectSourceCodeManagementGIT()
                .inputGITRepositoryURL(repositoryURL)
                .inputBranchSpecifier(branchSpecifier)
                .clickSaveBtn()
                .clickButtonBuildNowAndRedirectToDashboardAfterBuildCompleted();

        Assert.assertEquals(page.getJobBuildStatus(), "Success");
        Assert.assertNotEquals(page.getBuildDurationTime(), "N/A");
    }
}
