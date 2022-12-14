import model.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class HomeTest extends BaseTest {

    @Test
    public void testBuildNewPipeline() {
        final String namePipeline = "Pipeline1";
        final String expectedLastSuccess = "N/A";

        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(namePipeline)
                .selectPipelineAndClickOk()
                .scrollToEndPipelineConfigPage()
                .clickTrySamplePipelineDropDownMenu()
                .clickHelloWorld()
                .clickSaveButton()
                .clickDashboard();

        Assert.assertEquals(homePage.getLastSuccessText(), expectedLastSuccess);
    }

    @Test(dependsOnMethods = "testBuildNewPipeline")
    public void testBuildNewPipelineSuccess() {
        final String expectedCheckIcon = "Success";

        String actualCheckIcon = new HomePage(getDriver())
                .clickPipeline1()
                .clickBuildNow()
                .clickDashboard()
                .movePointToCheckBox()
                .getStatusBuildText();

        Assert.assertEquals(actualCheckIcon, expectedCheckIcon);
    }
}
