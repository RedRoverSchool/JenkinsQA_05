import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
    private static final List<String> BREAD_CRUMB_MENU = List.of(
            "Changes",
            "Workspace",
            "Build Now",
            "Configure",
            "Delete Project",
            "Rename");
    private static final String FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final By LINK_NEW_ITEM = By.linkText("New Item");
    private static final By FIELD_ENTER_AN_ITEM_NAME = By.id("name");
    private static final By LINK_FREESTYLE_PROJECT = By.cssSelector(".hudson_model_FreeStyleProject");
    private static final By BUTTON_OK_IN_NEW_ITEM = By.cssSelector("#ok-button");
    private static final By LINK_CHANGES = By.linkText("Changes");
    private static final By BUTTON_SAVE = By.xpath("//span[@name = 'Submit']");
    private static final By NEW_NAME_INPUT_ROW = By.xpath("//input[@name = 'newName']");
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

    private void findFreestyleProjectOnDashBoard(String nameFreestyleProject){
        getWait().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.linkText(nameFreestyleProject))));
        getAction()
                .moveToElement(getDriver().findElement(By.linkText(nameFreestyleProject)))
                .moveToElement(getDriver().findElement(By.xpath("//tr[@id = 'job_" + nameFreestyleProject + "']//td/a/button")))
                .click()
                .build()
                .perform();
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

    private void findAndClickBreadCrumbsMenu(String menu){
        getWait().until(ExpectedConditions.visibilityOfAllElements(getDriver().findElements(By.cssSelector("#breadcrumb-menu div ul li"))));
        WebElement breadCrumbsMenu = getDriver().findElement(By.linkText(menu));
        getAction().moveToElement(breadCrumbsMenu).click().perform();
    }

    private void createNewFreestyleProject(String freestyleName){
        getDriver().findElement(By.linkText("New Item")).click();

        WebElement itemNameField = getDriver().findElement(By.id("name"));
        itemNameField.click();
        itemNameField.sendKeys(freestyleName);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        JavascriptExecutor js = (JavascriptExecutor)getDriver();
        js.executeScript("document.getElementById('ok-button').click();");
        clickSubmitButton();
    }

    @Test
    public void testCreateNewFreestyleProjectWithCorrectName() {
        createNewFreestyleProject(FREESTYLE_NAME);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Project " + FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithCorrectName")
    public void testPresentationNewProjectOnDashboard() {
        goToDashBoard();

        Assert.assertTrue(getListExistingFreestyleProjectsNames().contains(FREESTYLE_NAME));
    }

    @Test
    public void testRenameFreestyleProject() {
        final String startFreestyleProjectName = getRandomName();
        final String finishFreestyleProjectName = getRandomName();

        createNewFreestyleProject(startFreestyleProjectName);
        goToDashBoard();
        getDriver().findElement(By.xpath("//tr[@id = 'job_" + startFreestyleProjectName + "']//td/a/button")).click();
        getDriver().findElement(By.linkText(BREAD_CRUMB_MENU.get(5))).click();
        getDriver().findElement(NEW_NAME_INPUT_ROW).clear();
        getDriver().findElement(NEW_NAME_INPUT_ROW).sendKeys(finishFreestyleProjectName);
        clickSubmitButton();
        goToDashBoard();

        Assert.assertFalse(getListExistingFreestyleProjectsNames().contains(startFreestyleProjectName));
        Assert.assertTrue(getListExistingFreestyleProjectsNames().contains(finishFreestyleProjectName));
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

    @Test
    public void testDeleteFreestyleProject() {
        final String testFreestyleProjectName = getRandomName();

        createNewFreestyleProject(testFreestyleProjectName);
        goToDashBoard();
        final List<String> startListExistingFreestyleProjects = getListExistingFreestyleProjectsNames();

        findFreestyleProjectOnDashBoard(testFreestyleProjectName);
        findAndClickBreadCrumbsMenu(BREAD_CRUMB_MENU.get(4));

        Alert alert = getDriver().switchTo().alert();
        alert.accept();
        final List<String> finishListExistingFreestyleProjects = getListExistingFreestyleProjectsNames();

        Assert.assertEquals(startListExistingFreestyleProjects.size() - finishListExistingFreestyleProjects.size(), 1);
        Assert.assertFalse(finishListExistingFreestyleProjects.contains(testFreestyleProjectName));
    }
}
