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

    private static final String FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final By LINK_NEW_ITEM = By.linkText("New Item");
    private static final By FIELD_ENTER_AN_ITEM_NAME = By.id("name");
    private static final By LINK_FREESTYLE_PROJECT = By.cssSelector(".hudson_model_FreeStyleProject");
    private static final By BUTTON_OK_IN_NEW_ITEM = By.cssSelector("#ok-button");
    private static final By LINK_CHANGES = By.linkText("Changes");

    private static final By BUTTON_SAVE = By.xpath("//span[@name = 'Submit']");
    private WebDriverWait wait;

    private Actions action;

    private String getRandomName() {

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
    public void testCreateNewFreestyleProject() {
        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("New Item"))).click();

        WebElement itemNameField = getDriver().findElement(By.id("name"));
        itemNameField.click();
        itemNameField.sendKeys(FREESTYLE_NAME);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        clickSubmitButton();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Project " + FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProject")
    public void testPresentationNewProjectOnDashboard() {
        goToDashBoard();

        Assert.assertTrue(getListExistingFreestyleProjectsNames().contains(FREESTYLE_NAME));
    }

    @Test
    public void testRenameFreestyleProject() {
        final String freestyleProjectName = getRandomName();
        final String newFreestyleProjectName = getRandomName();

        getDriver().findElement(By.linkText("New Item")).click();

        WebElement itemNameField = getDriver().findElement(By.id("name"));
        itemNameField.click();
        itemNameField.sendKeys(freestyleProjectName);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        clickSubmitButton();

        goToDashBoard();
        getDriver().findElement(By.linkText(freestyleProjectName)).click();
        getDriver().findElement(By.linkText("Rename")).click();

        WebElement newNameRow = getDriver().findElement(By.xpath("//input[@name = 'newName']"));
        getAction().moveToElement(newNameRow).doubleClick().sendKeys(newFreestyleProjectName).perform();
        clickSubmitButton();
        goToDashBoard();

        Assert.assertFalse(getListExistingFreestyleProjectsNames().contains(freestyleProjectName));
        Assert.assertTrue(getListExistingFreestyleProjectsNames().contains(newFreestyleProjectName));
    }

    @Test
    public void testViewChangesNoBuildsSignAppears() {
        String expectedText = "Changes\nNo builds.";

        getDriver().findElement(LINK_NEW_ITEM).click();
        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).sendKeys(getRandomName());
        getDriver().findElement(LINK_FREESTYLE_PROJECT).click();
        getDriver().findElement(BUTTON_OK_IN_NEW_ITEM).click();
        getDriver().findElement(BUTTON_SAVE).click();
        getDriver().findElement(LINK_CHANGES).click();

        String actualText = getDriver().findElement(By.xpath("//div[@id= 'main-panel']")).getText();

        Assert.assertEquals(actualText, expectedText);
    }
}
