import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class NewItemCreatePipelineTest extends BaseTest {

    private void click(By by) {
        getDriver().findElement(by).click();
    }

    @Test
    public void testCreatePipelineExistingNameError() {

        final String jobname = "Job15";

        click(By.linkText("New Item"));
        getDriver().findElement(By.id("name")).sendKeys(jobname);
        click(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob"));
        click(By.id("ok-button"));
        click(By.id("jenkins-home-link"));
        click(By.linkText("New Item"));

        new Actions(getDriver()).moveToElement(getDriver().findElement(By.id("name"))).click()
                .sendKeys(jobname).build().perform();

        final WebElement notificationError = getDriver()
                .findElement(By.xpath("//div[@class='input-validation-message' and not(contains(@class, 'disabled')) and  @id='itemname-invalid']"));

        Assert.assertEquals(notificationError.getText(), String.format("» A job already exists with the name ‘%s’", jobname));
    }
}
