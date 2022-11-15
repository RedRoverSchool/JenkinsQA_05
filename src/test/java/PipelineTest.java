import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;


public class PipelineTest extends BaseTest {

    @Test
    public void test_CreatePipelineValidNameApplyDefaultConfigure() {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("Job15");
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("yui-gen5-button")).click();

        Assert.assertEquals(getDriver().findElement(By.id("notification-bar")).getText(), "Saved");
    }
}
