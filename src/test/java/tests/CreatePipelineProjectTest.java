package tests;

import model.HomePage;
import model.pipeline.PipelineConfigPage;
import model.pipeline.PipelineStatusPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

public class CreatePipelineProjectTest extends BaseTest {

    private static final String PIPELINE_NAME = TestUtils.getRandomStr(10);

    @Test
    public void testCreatePipelineProject() {

        String actualResult = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PIPELINE_NAME)
                .selectPipeline()
                .clickOk()
                .clickSaveBtn(PipelineStatusPage.class)
                .getPipelineName();

        Assert.assertEquals(actualResult, PIPELINE_NAME);
    }
}
