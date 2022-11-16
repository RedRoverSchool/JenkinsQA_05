import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class NewItemCreatePipelineTest extends BaseTest {

    private final String BASE_URL = "http://localhost:8080/";

    @Test
    public void test_CreatePipelineExistingNameError() {

        final String JOBNAME = "Job15";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(JOBNAME);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().get(BASE_URL);
        getDriver().findElement(By.linkText("New Item")).click();

        new Actions(getDriver()).moveToElement(getDriver().findElement(By.id("name"))).click()
                .sendKeys(JOBNAME).build().perform();

        final WebElement notificationError = getDriver()
                .findElement(By.xpath("//div[@class='input-validation-message' and not(contains(@class, 'disabled')) and  @id='itemname-invalid']"));

        Assert.assertEquals(notificationError.getText(), String.format("» A job already exists with the name ‘%s’", JOBNAME));
    }
}
