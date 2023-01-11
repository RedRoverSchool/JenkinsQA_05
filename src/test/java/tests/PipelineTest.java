package tests;

import model.*;
import model.pipeline.PipelineConfigPage;
import model.pipeline.PipelineStatusPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectMethodsUtils;
import runner.TestUtils;
import java.util.List;

public class PipelineTest extends BaseTest {
    private static final String RENAME_SUFFIX = "renamed";
    private static final String PIPELINE_NAME = TestUtils.getRandomStr();
    private static final String ITEM_NAME = TestUtils.getRandomStr();
    private static final String PIPELINE_DESCRIPTION = PIPELINE_NAME + " description";
    private static final String ITEM_NEW_DESCRIPTION = "New description";

    @Test
    public void testDisablePipelineProjectMessage() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);
        String actualMessageDisabledProject = new HomePage(getDriver())
                .clickPipelineJob(PIPELINE_NAME)
                .clickDisableProject()
                .getMessageDisabledProject();

        Assert.assertEquals(actualMessageDisabledProject, "This project is currently disabled");
    }

    @Test
    public void testCreatedPipelineDisplayedOnMyViews() {
        String pipelineNameInMyViewList = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .getBreadcrumbs()
                .clickDashboard()
                .clickMyViewsSideMenuLink().getListProjectsNamesAsString();

        Assert.assertTrue(pipelineNameInMyViewList.contains(PIPELINE_NAME), PIPELINE_NAME + " Pipeline not found");
    }

    @Test
    public void testNewPipelineItemDisplayedOnDashboard() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);

        Assert.assertTrue(new HomePage(getDriver()).getJobListAsString().contains(PIPELINE_NAME), PIPELINE_NAME + " Pipeline not found");
    }

    @Test
    public void testRenamePipelineWithValidName() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);
        new HomePage(getDriver())
                .clickJobDropDownMenu(PIPELINE_NAME)
                .clickRenamePipelineDropDownMenu()
                .clearFieldAndInputNewName(PIPELINE_NAME + RENAME_SUFFIX)
                .clickRenameButton();

        Assert.assertEquals(new PipelineStatusPage(getDriver()).getNameText(), "Pipeline " + PIPELINE_NAME + RENAME_SUFFIX);
    }

    @Test
    public void testRenamedPipelineIsDisplayedInMyViews() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);

        String actualJobListAsString = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(ITEM_NAME)
                .setMyViewTypeAndCLickCreate()
                .getBreadcrumbs()
                .clickDashboard()
                .clickJobDropDownMenu(PIPELINE_NAME)
                .clickRenamePipelineDropDownMenu()
                .clearFieldAndInputNewName(PIPELINE_NAME + RENAME_SUFFIX)
                .clickRenameButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickMyViewsSideMenuLink()
                .clickView(ITEM_NAME)
                .getJobListAsString();

        Assert.assertTrue(actualJobListAsString.contains(PIPELINE_NAME + RENAME_SUFFIX), PIPELINE_NAME + RENAME_SUFFIX + " Pipeline not found");
    }

    @Test
    public void testRenamePipelineWithoutChangingName() {
        RenameItemErrorPage renameItemErrorPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickJobDropDownMenu(PIPELINE_NAME)
                .clickRenamePipelineDropDownMenu()
                .clickSaveButtonAndGetError();

        Assert.assertEquals(renameItemErrorPage.getHeadErrorMessage(), "Error");
        Assert.assertEquals(renameItemErrorPage.getErrorMessage(), "The new name is the same as the current name.");
    }

    @DataProvider(name = "specialCharacters")
    public Object[][] specialCharactersList() {
        return new Object[][]{{'&', "&amp;"}, {'>', "&gt;"}, {'<', "&lt;"}, {'!', "!"}, {'@', "@"}, {'#', "#"},
                {'$', "$"}, {'%', "%"}, {'^', "^"}, {'*', "*"}, {'[', "["}, {']', "]"}, {'\\', "\\"}, {'|', "|"},
                {';', ";"}, {':', ":"}, {'/', "/"}, {'?', "?"},};
    }

    @Test(dataProvider = "specialCharacters")
    public void testRenamePipelineUsingSpecialCharacter(Character unsafeCharacter, String expectedUnsafeCharacterInErrorMessage) {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);

        String actualRenameErrorMessage = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .clickJobDropDownMenu(PIPELINE_NAME)
                .clickRenamePipelineDropDownMenu()
                .clearFieldAndInputNewName(PIPELINE_NAME + unsafeCharacter)
                .clickSaveButtonAndGetError()
                .getErrorMessage();

        Assert.assertEquals(actualRenameErrorMessage, String.format("‘%s’ is an unsafe character", expectedUnsafeCharacterInErrorMessage));
    }

    @Test
    public void testPipelinePreviewDescription() {
        PipelineConfigPage pipelineConfigPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .setDescriptionField(PIPELINE_DESCRIPTION)
                .clickPreviewLink();

        Assert.assertEquals(pipelineConfigPage.getTextareaPreview(), PIPELINE_DESCRIPTION);
    }

    @Test
    public void testPipelineHidePreviewDescription() {
        PipelineConfigPage pipelineConfigPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .setDescriptionField(PIPELINE_DESCRIPTION)
                .clickPreviewLink()
                .clickHidePreviewLink();

        Assert.assertFalse(pipelineConfigPage.isDisplayedPreviewTextDescription());
    }

    @Test
    public void testPipelineAEditDescription() {
        PipelineStatusPage pipelineProjectPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .setDescriptionField(PIPELINE_DESCRIPTION)
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickPipelineJob(PIPELINE_NAME)
                .clickEditDescriptionLink().editDescription(PIPELINE_NAME + "edit description")
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard().clickPipelineJob(PIPELINE_NAME);

        Assert.assertEquals(pipelineProjectPage.getDescriptionText(), PIPELINE_NAME + "edit description");
    }

    @Ignore
    @Test
    public void testDeletePipelineFromDashboard() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);
        String homePageHeaderText = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .clickPipelineJob(PIPELINE_NAME)
                .clickDeletePipelineButton()
                .getHeaderText();

        Assert.assertEquals(homePageHeaderText, "Welcome to Jenkins!");
    }

    @Test
    public void testCreatePipelineExistingNameError() {
        String newItemPageErrorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .getBreadcrumbs()
                .clickDashboard()
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipeline()
                .getItemNameInvalidMsg();

        Assert.assertEquals(newItemPageErrorMessage, (String.format("» A job already exists with the name ‘%s’", PIPELINE_NAME)));
    }

    @Test
    public void testCreatedPipelineIsDisplayedOnBreadcrumbs() {
        String actualTextOnBreadcrumbs = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .getBreadcrumbs()
                .getTextBreadcrumbs();

        Assert.assertTrue(actualTextOnBreadcrumbs.contains(PIPELINE_NAME), PIPELINE_NAME + " Pipeline Not Found On Breadcrumbs");
    }

    @Test
    public void testCreateNewPipeline() {
        String actualPipelineName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .getNameText();

        Assert.assertEquals(actualPipelineName, "Pipeline " + PIPELINE_NAME);
    }

    @Test
    public void testCreatePipelineWithName() {
        ProjectMethodsUtils.createNewPipelineProject(getDriver(), PIPELINE_NAME);
        String actualPipelineName = new HomePage(getDriver())
        .getJobName(PIPELINE_NAME);

        Assert.assertEquals(actualPipelineName, PIPELINE_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewPipeline")
    public void testAddingGitRepository() {
        final String gitHubRepo = "https://github.com/patriotby07/simple-maven-project-with-tests";

        PipelineStatusPage pipelineProjectPage = new HomePage(getDriver())
                .clickJobDropDownMenu(PIPELINE_NAME)
                .clickConfigureDropDownMenu()
                .clickGitHubCheckbox()
                .setGitHubRepo(gitHubRepo)
                .clickSaveButton();

        Assert.assertTrue(pipelineProjectPage.isDisplayedGitHubOnSideMenu());
        Assert.assertTrue(pipelineProjectPage.getAttributeGitHubSideMenu("href").contains(gitHubRepo));
    }

    @Ignore
    @Test(dependsOnMethods = "testAddingGitRepository")
    public void testWarningMessageIsDisappeared() {
        String emptyErrorArea = new HomePage(getDriver())
                .clickMenuManageJenkins()
                .clickConfigureTools()
                .clickFirstAddMavenButton()
                .setFirstMavenTitleField("Maven")
                .clickApplyButton()
                .getErrorAreaText();

        Assert.assertEquals(emptyErrorArea, "");
    }

    @Ignore
    @Test(dependsOnMethods = "testWarningMessageIsDisappeared")
    public void testBuildParametrizedProject() {
        String consoleOutputText = new HomePage(getDriver())
                .clickJobDropDownMenu(PIPELINE_NAME)
                .clickConfigureDropDownMenu()
                .clickParameterizationCheckbox()
                .clickAddParameter()
                .clickChoiceParameter()
                .setChoiceParameter("Select User", "Admin", "Guest", "User")
                .selectPipelineScriptFromScm()
                .selectScriptScm()
                .setGitHubUrl("https://github.com/patriotby07/simple-maven-project-with-tests")
                .clickSaveButton()
                .clickBuildWithParameters()
                .selectParameterByText("Guest")
                .clickBuildButton()
                .clickLastBuildLink()
                .clickConsoleOutput()
                .getConsoleOutputText();

        Assert.assertTrue(consoleOutputText.contains("BUILD SUCCESS"));
        Assert.assertTrue(consoleOutputText.contains("Finished: SUCCESS"));
    }

    @Test
    public void testPipelineAddDescription() {
        PipelineStatusPage pipelineProjectPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .editDescription(PIPELINE_DESCRIPTION)
                .clickSaveButton();

        Assert.assertEquals(pipelineProjectPage.getDescriptionText(), PIPELINE_DESCRIPTION);
    }

    @Test
    public void testCreateNewPipelineWithDescription() {
        String actualPipelineDescription = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .setDescriptionField(PIPELINE_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualPipelineDescription, PIPELINE_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreateNewPipelineWithDescription")
    public void testEditPipelineDescription() {

        String actualDescription = new HomePage(getDriver())
                .clickJobDropDownMenu(PIPELINE_NAME)
                .clickPipelineProjectName()
                .editDescription(ITEM_NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualDescription, ITEM_NEW_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testEditPipelineDescription")
    public void testCreateNewPipelineFromExisting() {
        String actualJobName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(ITEM_NAME)
                .setCopyFromItemName(PIPELINE_NAME)
                .clickOk()
                .clickSaveButton()
                .getPipelineName();

        String actualDescription = new PipelineStatusPage(getDriver()).getDescriptionText();

        Assert.assertEquals(actualJobName, ITEM_NAME);
        Assert.assertEquals(actualDescription, ITEM_NEW_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreatePipelineWithName")
    public void testEnablePipelineProject() {
        String jobStatusAfterEnable = new HomePage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .clickPipelineProjectName()
                .clickDisableProject()
                .clickEnableProject()
                .getBreadcrumbs()
                .clickDashboard()
                .getJobBuildStatus();

        Assert.assertNotEquals(jobStatusAfterEnable, "Disabled");
    }

    @Test(dependsOnMethods = "testCreateNewPipelineFromExisting")
    public void testPipelineSideMenuLinks() {
        List<String> expectedResult = List.of("Status", "Changes", "Build Now", "Configure", "Delete Pipeline",
                "Full Stage View", "Rename", "Pipeline Syntax");
        List<String> pipelineSideMenuOptionsLinks = new HomePage(getDriver())
                .clickPipelineProjectName()
                .getPipelineSideMenuLinks();

        Assert.assertEquals(pipelineSideMenuOptionsLinks, expectedResult);
    }

    @Test
    public void testBuildNewPipeline() {
        final String expectedLastSuccess = "N/A";

        String actualSuccessText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .scrollToEndPipelineConfigPage()
                .clickTrySamplePipelineDropDownMenu()
                .clickHelloWorld()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .getLastSuccessText(PIPELINE_NAME);

        Assert.assertEquals(actualSuccessText, expectedLastSuccess);
    }

    @Test(dependsOnMethods = "testBuildNewPipeline")
    public void testBuildNewPipelineSuccess() {
        final String expectedCheckIcon = "Success";

        String actualCheckIcon = new HomePage(getDriver())
                .clickPipelineJob(PIPELINE_NAME)
                .clickBuildNow(PIPELINE_NAME)
                .getBreadcrumbs()
                .clickDashboard()
                .movePointToCheckBox()
                .getStatusBuildText();

        Assert.assertEquals(actualCheckIcon, expectedCheckIcon);
    }

    @Test(dependsOnMethods = "testBuildNewPipeline")
    public void testAddDescriptionInExistPipeline() {
        String actualDescription = new HomePage(getDriver())
                .clickPipelineJob(PIPELINE_NAME)
                .clickConfigure()
                .setDescriptionField(ITEM_NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualDescription, ITEM_NEW_DESCRIPTION);
    }
}
