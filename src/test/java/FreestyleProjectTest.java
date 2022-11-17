import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
    private Actions action;

    private static final String FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);

    private String getRandomName(){

        return RandomStringUtils.randomAlphanumeric(10);
    }
    private WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        }

        return wait;
    }

    private Actions getAction() {
        if (action == null) {
            action = new Actions(getDriver());
        }

        return action;
    }

    private List<String> getListExistingFreestyleProjectsNames() {
        List<WebElement> existingJobs = getDriver().findElements(By.xpath("//tr/td/a"));
        List<String> existingJobsNames = new ArrayList<>();
        for (int i = 0; i < existingJobs.size(); i++) {
            existingJobsNames.add(i, existingJobs.get(i).getText());
        }

        return existingJobsNames;
    }

    private void goToDashBoard() {
        getDriver().findElement(By.linkText("Dashboard")).click();
    }

    private void clickSubmitButton() {

        getDriver().findElement(By.xpath("//span[@name = 'Submit']")).click();
    }

    @Test
    public void testCreateNewFreestyleProjectWithCorrectName() {
        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("New Item"))).click();

        WebElement itemNameField = getDriver().findElement(By.id("name"));
        itemNameField.click();
        itemNameField.sendKeys(FREESTYLE_NAME);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ok-button"))).click();
        clickSubmitButton();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Project " + FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithCorrectName")
    public void testPresentationNewProjectOnDashboard() {
        goToDashBoard();

        Assert.assertTrue(getListExistingFreestyleProjectsNames().contains(FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithCorrectName")
    public void testRenameFreestyleProject() {
        final String newFreestyleProjectName = getRandomName();

        goToDashBoard();
        getAction()
                .moveToElement(getDriver().findElement(By.linkText(FREESTYLE_NAME)))
                .moveToElement(getDriver().findElement(By.xpath("//tr[@id = 'job_" + FREESTYLE_NAME + "']//td/a/button")))
                .click()
                .perform();

        List<WebElement> breadCrumbMenu = getDriver().findElements(By.cssSelector("#breadcrumb-menu li"));
        getWait().until(ExpectedConditions.visibilityOfAllElements(breadCrumbMenu));
        getAction().moveToElement(getDriver().findElement(By.cssSelector("#yui-gen6"))).click().perform();

        getAction()
                .moveToElement(getDriver().findElement(By.xpath("//div[@class = 'jenkins-form-item tr ']/div[2]")))
                .doubleClick().sendKeys(newFreestyleProjectName).perform();
        clickSubmitButton();
        goToDashBoard();

        Assert.assertFalse(getListExistingFreestyleProjectsNames().contains(FREESTYLE_NAME));
        Assert.assertTrue(getListExistingFreestyleProjectsNames().contains(newFreestyleProjectName));
    }
}
