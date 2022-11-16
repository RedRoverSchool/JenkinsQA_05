import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FreestyleProjectTest extends BaseTest {

    private WebDriverWait wait;
    private final String freestyleName = RandomStringUtils.randomAlphanumeric(10);

    private WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        }

        return wait;
    }

    @Test
    public void testCreateNewFreestyleProject() {
        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("New Item"))).click();

        final WebElement itemNameField = getDriver().findElement(By.id("name"));
        itemNameField.click();
        itemNameField.sendKeys(freestyleName);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(By.xpath("//span[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Project " + freestyleName);
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProject")
    public void testPresentationNewProjectOnDashboard() {
        getDriver().findElement(By.xpath("//a[contains(text(), 'Dashboard')]")).click();

        List<WebElement> existingJobs = getDriver().findElements(By.xpath("//tr/td/a"));
        List<String> existingJobsNames = new ArrayList<>();
        for (int i = 0; i < existingJobs.size(); i++) {
            existingJobsNames.add(i, existingJobs.get(i).getText());
        }

        Assert.assertTrue(existingJobsNames.contains(freestyleName));
    }
}
