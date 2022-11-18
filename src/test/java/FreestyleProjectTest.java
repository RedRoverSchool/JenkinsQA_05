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

    private static final String FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final String NEW_FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final By LINK_NEW_ITEM = By.linkText("New Item");
    private static final By FIELD_ENTER_AN_ITEM_NAME = By.id("name");
    private static final By LINK_FREESTYLE_PROJECT = By.cssSelector(".hudson_model_FreeStyleProject");
    private static final By BUTTON_OK_IN_NEW_ITEM = By.cssSelector("#ok-button");
    private static final By LINK_CHANGES = By.linkText("Changes");
    private static final By BUTTON_SAVE = By.xpath("//span[@name = 'Submit']");
    private WebDriverWait wait;

    private WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        }
        return wait;
    }

    private List<String> getListExistingFreestyleProjectsNames() {
        List<WebElement> existingJobs =
                getDriver().findElements(By.xpath("//a[@class='jenkins-table__link model-link inside']"));
        List<String> existingJobsNames = new ArrayList<>();
        for (int i = 0; i < existingJobs.size(); i++) {
            existingJobsNames.add(i, existingJobs.get(i).getText());
        }
        return existingJobsNames;
    }

    private void goToDashBoard() {
        getDriver().findElement(By.linkText("Dashboard")).click();
    }

    @Test
    public void testCreateNewFreestyleProjectWithCorrectName() {
        getWait().until(ExpectedConditions.elementToBeClickable(LINK_NEW_ITEM)).click();

        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).click();
        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).sendKeys(FREESTYLE_NAME);
        getDriver().findElement(LINK_FREESTYLE_PROJECT).click();
        getWait().until(ExpectedConditions.elementToBeClickable(BUTTON_OK_IN_NEW_ITEM)).click();
        getDriver().findElement(BUTTON_SAVE).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//h1")).getText(), "Project " + FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithCorrectName")
    public void testPresentationNewProjectOnDashboard() {
        goToDashBoard();

        Assert.assertTrue(getListExistingFreestyleProjectsNames().contains(FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithCorrectName")
    public void testRenameFreestyleProject() {

        goToDashBoard();
        getDriver().findElement(By.cssSelector("tr#job_" + FREESTYLE_NAME + " .jenkins-menu-dropdown-chevron")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + FREESTYLE_NAME +"/confirm-rename']")).click();
        getDriver().findElement(By.cssSelector("input[name='newName']")).clear();
        getDriver().findElement(By.cssSelector("input[name='newName']")).sendKeys(NEW_FREESTYLE_NAME);
        getDriver().findElement(By.cssSelector("#yui-gen1-button")).click();
        goToDashBoard();

        Assert.assertFalse(getListExistingFreestyleProjectsNames().contains(FREESTYLE_NAME));
        Assert.assertTrue(getListExistingFreestyleProjectsNames().contains(NEW_FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testRenameFreestyleProject")
    public void testViewChangesNoBuildsSignAppears() {
        String expectedText = "Changes\nNo builds.";

        getDriver().findElement(LINK_NEW_ITEM).click();
        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).sendKeys(FREESTYLE_NAME);
        getDriver().findElement(LINK_FREESTYLE_PROJECT).click();
        getDriver().findElement(BUTTON_OK_IN_NEW_ITEM).click();
        getDriver().findElement(BUTTON_SAVE).click();
        getDriver().findElement(LINK_CHANGES).click();

        String actualText = getDriver().findElement(By.xpath("//div[@id= 'main-panel']")).getText();

        Assert.assertEquals(actualText, expectedText);
    }
}
