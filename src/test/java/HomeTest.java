import model.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class HomeTest extends BaseTest {

    @Test
    public void testBuildNewPipeline() {
        final String namePipeline = "Pipeline1";
        final String expectedLastSuccess = "N/A";

        HomePage homePage =new HomePage(getDriver())
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
}
