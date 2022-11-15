import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;

public class PipelineTest extends BaseTest {

    @Test
    public void test_CreatePipelineValidNameApplyDefaultConfigure() {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("Job12");
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.cssSelector("#yui-gen5-button")).click();
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.id("notification-bar"))).perform();
        WebElement notificationSave = new WebDriverWait(getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(getDriver().findElement(By.id("notification-bar"))));

        Assert.assertEquals(notificationSave.getText(), "Saved");
    }
}
