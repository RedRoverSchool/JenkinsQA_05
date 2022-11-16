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

    private final String freestyleName = RandomStringUtils.randomAlphanumeric(10);

    private void clickMenuSelector() {
        getDriver().findElement(By.id("menuSelector")).click();
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
        getDriver().findElement(By.xpath("//a[contains(text(), 'Dashboard')]")).click();
    }

    private void clickSubmitButton() {

        getDriver().findElement(By.xpath("//span[@name = 'Submit']")).click();
    }

    @Test
    public void testCreateNewFreestyleProject() {
        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("New Item"))).click();

        final WebElement itemNameField = getDriver().findElement(By.id("name"));
        itemNameField.click();
        itemNameField.sendKeys(freestyleName);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        clickSubmitButton();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Project " + freestyleName);
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProject")
    public void testPresentationNewProjectOnDashboard() {
        goToDashBoard();

        Assert.assertTrue(getListExistingFreestyleProjectsNames().contains(freestyleName));
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProject")
    public void testRenameFreestyleProject() {
        final String newFreestyleName = RandomStringUtils.randomAlphanumeric(10);

        goToDashBoard();
        getAction()
                .moveToElement(getDriver().findElement(By.linkText(freestyleName)))
                .perform();
        clickMenuSelector();

        final List<WebElement> breadCrumbMenu = getDriver().findElements(By.cssSelector("#breadcrumb-menu li"));
        getWait().until(ExpectedConditions.visibilityOfAllElements(breadCrumbMenu));
        getAction().moveToElement(getDriver().findElement(By.cssSelector("#yui-gen6"))).click().perform();

        final WebElement newNameRow = getDriver().findElement(By.xpath("//input[@name = 'newName']"));
        getAction().moveToElement(newNameRow).doubleClick().sendKeys(newFreestyleName).perform();
        clickSubmitButton();
        goToDashBoard();

        Assert.assertFalse(getListExistingFreestyleProjectsNames().contains(freestyleName));
        Assert.assertTrue(getListExistingFreestyleProjectsNames().contains(newFreestyleName));
    }
}
