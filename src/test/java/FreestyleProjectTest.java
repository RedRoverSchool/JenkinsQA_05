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


    private void click(By by) {getDriver().findElement(by).click();}
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

    @Test
    public void createFreestyleProjectWithEngineerName() {

        final String expectedResult = "Engineer";

        click(By.linkText("New Item"));
        getDriver().findElement(By.id("name")).sendKeys(expectedResult);
        click(By.className("label"));
        click(By.id("ok-button"));
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        WebElement registeredProject = getDriver().findElement(By.xpath("//h1[@class='job-index-" +
                "headline page-headline']"));

        String actualResult = registeredProject.getText().substring(registeredProject.getText().length()-8);

        Assert.assertEquals(actualResult, expectedResult);
    }
}
