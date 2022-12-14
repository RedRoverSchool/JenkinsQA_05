import model.BuildWithParametersPage;
import model.ConsolePage;
import model.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class ConfigureFreestyleProjectTest extends BaseTest {

    @Test
    public void testConfigureJobAsParameterized() {
        final String freestyleName = "testConnectWithGIT";
        final String stringParameterName = "Held post";
        final String stringParameterDefaultValue = "Manager";
        final String choiceParameterName = "Employee_name";
        final String choiceParameterValues = "John Smith\nJane Dow";
        final String booleanParameterName = "Employed";
        final String pageNotification = "This build requires parameters:";

        BuildWithParametersPage page = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(freestyleName)
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

        Assert.assertEquals(page.getProjectName(), freestyleName);
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
        final String repositoryURL = "https://github.com/RedRoverSchool/course_frontend_05.git";
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
                .clickButtonBuildNowAndWaitBuildComplete()
                .clickDashboard();

        Assert.assertEquals(page.getJobBuildStatus(), "Success");
        Assert.assertNotEquals(page.getBuildDurationTime(), "N/A");
    }

    @Test(dependsOnMethods = "testConfigureSourceCodeByGIT")
    public void testConfigureJobByExecuteWindowsBatchCommand(){
        final String outputComment = "This is an example of a Windows batch script";
        final String executeCommand = "@echo off\necho " + outputComment;

        ConsolePage consolePage = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickSideMenuConfigureLink()
                .selectSourceCodeManagementNone()
                .clickLinkBuildSteps()
                .clickButtonAddBuildStep()
                .selectExecuteWindowsBatchCommand()
                .inputExecuteWindowsBatchCommand(executeCommand)
                .clickSaveBtn()
                .clickButtonBuildNowAndWaitBuildComplete()
                .clickBuildStatusIcon();

        Assert.assertEquals(consolePage.getPageHeader(), "Console Output");



        Assert.assertEquals(consolePage.getJobBuildStatus(), "Success");
        Assert.assertTrue(consolePage.getConsoleOutputText().contains("Finished: SUCCESS"));
    }
}
