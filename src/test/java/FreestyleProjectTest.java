import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class FreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final String FREESTYLE_NAME_WITH_DESCRIPTION = RandomStringUtils.randomAlphanumeric(10);
    private static final String NEW_FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final String FREESTYLE_DESCRIPTION = RandomStringUtils.randomAlphanumeric(10);
    private static final By LINK_NEW_ITEM = By.linkText("New Item");
    private static final By FIELD_ENTER_AN_ITEM_NAME = By.id("name");
    private static final By LINK_FREESTYLE_PROJECT = By.cssSelector(".hudson_model_FreeStyleProject");
    private static final By BUTTON_OK_IN_NEW_ITEM = By.cssSelector("#ok-button");
    private static final By LINK_CHANGES = By.linkText("Changes");
    private static final By BUTTON_SAVE = By.xpath("//button[@type = 'submit']");
    private static final By LIST_FREESTYLE_JOBS = By
            .xpath("//a[@class='jenkins-table__link model-link inside']");
    private static final By EDIT_DESCRIPTION_BUTTON = By.id("description-link");
    private static final By DESCRIPTION_TEXT_FIELD = By.xpath("//textarea[@name = 'description']");
    private static final By DESCRIPTION_SAVE_BUTTON = By.id("yui-gen2-button");
    private static final By DESCRIPTION_TEXT = By.xpath("//div[@id = 'description'] /div[1]");
    private static final By ITEM_NAME_INVALID = By.cssSelector("#itemname-invalid");
    private static final By JOB_HEADLINE_LOCATOR = By.xpath("//h1");
    private static final By MAIN_PANEL_LOCATOR = By.id("main-panel");
    private static final By BUILD_NOW_LOCATOR = By.linkText("Build Now");
    private static final By BUILDS_LOCATOR =
            By.xpath("//table[@class='pane jenkins-pane stripped']//tr[@page-entry-id]");
    private static final By DISABLE_PROJECT_BUTTON = By.id("yui-gen1-button");
    private static final By ENABLE_PROJECT_BUTTON = By.xpath("//button[@type = 'submit']");
    private static final By GO_TO_DASHBOARD_BUTTON = By.linkText("Dashboard");

    private WebDriverWait wait;

    private WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        }
        return wait;
    }

    private List<String> getListExistingFreestyleProjectsNames(By by) {
        return getDriver().findElements(by).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    private List<WebElement> findJobParam(String nameJob) {
        return getDriver().findElements(By.xpath("//tr[@id = 'job_" + nameJob + "']"));
    }

    @Test
    public void testCreateNewFreestyleProjectWithCorrectName() {
        getWait().until(ExpectedConditions.elementToBeClickable(LINK_NEW_ITEM)).click();

        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).click();
        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).sendKeys(FREESTYLE_NAME);
        getDriver().findElement(LINK_FREESTYLE_PROJECT).click();
        getWait().until(ExpectedConditions.elementToBeClickable(BUTTON_OK_IN_NEW_ITEM)).click();
        getWait().until(ExpectedConditions.elementToBeClickable(BUTTON_SAVE)).click();

        Assert.assertEquals(getDriver()
                .findElement(JOB_HEADLINE_LOCATOR).getText(), "Project " + FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithCorrectName")
    public void testDisableProject() {

        getDriver().findElement(By.xpath("//a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(DISABLE_PROJECT_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Project " + FREESTYLE_NAME);
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'warning']")).getText().trim().substring(0, 34),
                "This project is currently disabled");

        getDriver().findElement(GO_TO_DASHBOARD_BUTTON).click();

        Assert.assertTrue(findJobParam(FREESTYLE_NAME).get(0).getAttribute("class").contains("job-status-disabled"));
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testEnableProject() {

        getDriver().findElement(By.xpath("//a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(ENABLE_PROJECT_BUTTON).click();
        getDriver().findElement(GO_TO_DASHBOARD_BUTTON).click();

        Assert.assertTrue(findJobParam(FREESTYLE_NAME).get(0).getAttribute("class").contains(" job-status-nobuilt"));
    }

    @Test(dependsOnMethods = "testEnableProject")
    public void testFreestyleProjectPageIsOpenedFromDashboard() {

        getDriver().findElement(GO_TO_DASHBOARD_BUTTON).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                String.format("Project %s", FREESTYLE_NAME));
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='main-panel']/h2")).getText(),
                "Permalinks");
        Assert.assertTrue(getDriver().findElement(By.cssSelector("#yui-gen1")).isEnabled());
    }

    @Test(dependsOnMethods = "testFreestyleProjectPageIsOpenedFromDashboard")
    public void testPresentationNewProjectOnDashboard() {

        Assert.assertTrue(getListExistingFreestyleProjectsNames(LIST_FREESTYLE_JOBS).contains(FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testPresentationNewProjectOnDashboard")
    public void testAddDescriptionToFreestyleProject() {
        final String descriptionText = "This is job #" + FREESTYLE_NAME;

        getDriver().findElement(By.xpath("//a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(EDIT_DESCRIPTION_BUTTON).click();
        getDriver().findElement(DESCRIPTION_TEXT_FIELD).sendKeys("This is job #" + FREESTYLE_NAME);
        getDriver().findElement(By.xpath("//div[@class = 'textarea-preview-container']/a[1]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'textarea-preview']")).getText(), descriptionText);

        getDriver().findElement(DESCRIPTION_SAVE_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(DESCRIPTION_TEXT).getText(), descriptionText);
    }

    @Test(dependsOnMethods = "testAddDescriptionToFreestyleProject")
    public void testNoBuildFreestyleProjectChanges() {
        getDriver().findElement(By.linkText(FREESTYLE_NAME)).click();
        getDriver().findElement(LINK_CHANGES).click();

        final String actualChangesText = getDriver().findElement(MAIN_PANEL_LOCATOR)
                .getText().replace("Changes\n", "");

        Assert.assertEquals(actualChangesText, "No builds.");
    }

    @Test(dependsOnMethods = "testNoBuildFreestyleProjectChanges")
    public void testRenameFreestyleProject() {

        getDriver().findElement(By.cssSelector("tr#job_" + FREESTYLE_NAME + " .jenkins-menu-dropdown-chevron")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + FREESTYLE_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.cssSelector("input[name='newName']")).clear();
        getDriver().findElement(By.cssSelector("input[name='newName']")).sendKeys(NEW_FREESTYLE_NAME);
        getDriver().findElement(By.cssSelector("#yui-gen1-button")).click();
        getDriver().findElement(GO_TO_DASHBOARD_BUTTON).click();

        Assert.assertFalse(getListExistingFreestyleProjectsNames(LIST_FREESTYLE_JOBS).contains(FREESTYLE_NAME));
        Assert.assertTrue(getListExistingFreestyleProjectsNames(LIST_FREESTYLE_JOBS).contains(NEW_FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testRenameFreestyleProject")
    public void testViewFreestyleProjectPage() {
        getDriver().findElement(By.linkText(NEW_FREESTYLE_NAME)).click();

        Assert.assertEquals(getDriver().findElement(JOB_HEADLINE_LOCATOR).getText()
                , String.format("Project %s", NEW_FREESTYLE_NAME));
    }


    @Test(dependsOnMethods = "testViewFreestyleProjectPage")
    public void testViewChangesNoBuildsSignAppears() {
        String expectedText = "Changes\nNo builds.";

        getDriver().findElement(By.xpath("//span[contains(text(),'" + NEW_FREESTYLE_NAME + "')]")).click();
        getDriver().findElement(LINK_CHANGES).click();

        String actualText = getDriver().findElement(MAIN_PANEL_LOCATOR).getText();

        Assert.assertEquals(actualText, expectedText);
    }

    @Test(dependsOnMethods = "testViewChangesNoBuildsSignAppears")
    public void testFreestyleProjectConfigureByDropdown() {
        getDriver().findElement(By.cssSelector("#job_" + NEW_FREESTYLE_NAME + " .jenkins-menu-dropdown-chevron")).click();
        WebElement element = getDriver().findElement(By.xpath("//a[@href='/job/" + NEW_FREESTYLE_NAME + "/configure']"));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);

        Assert.assertEquals(getDriver().getTitle(), NEW_FREESTYLE_NAME + " Config [Jenkins]");
    }

    @Test(dependsOnMethods = "testFreestyleProjectConfigureByDropdown")
    public void testCreateNewFreestyleProjectWithDupicateName() {
        getDriver().findElement(GO_TO_DASHBOARD_BUTTON).click();

        getDriver().findElement(LINK_NEW_ITEM).click();

        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).click();
        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).sendKeys(NEW_FREESTYLE_NAME);
        getDriver().findElement(LINK_FREESTYLE_PROJECT).click();

        Assert.assertEquals(getDriver().findElement(ITEM_NAME_INVALID).getText(),
                String.format("» A job already exists with the name ‘%s’", NEW_FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testFreestyleProjectBuild")
    public void testDeleteFreestyleProject() {

        getDriver().findElement(By.cssSelector("tr#job_" + NEW_FREESTYLE_NAME + " .jenkins-menu-dropdown-chevron")).click();
        getDriver().findElement(By.xpath("//span[contains(text(),'Delete Project')]")).click();

        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        Assert.assertFalse(getListExistingFreestyleProjectsNames(LIST_FREESTYLE_JOBS).contains(NEW_FREESTYLE_NAME));
    }

    @Test
    public void testCreateFreestyleProjectWithDescription() {
        final String description = "Some Description Text";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(FREESTYLE_NAME_WITH_DESCRIPTION);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
        getDriver().findElement(BUTTON_SAVE).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(),
                description);
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                String.format("Project %s", FREESTYLE_NAME_WITH_DESCRIPTION));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithDescription")
    public void testEditFreestyleProjectWithDescription() {
        getDriver().findElement(By.xpath("//span[contains(text(),'" + FREESTYLE_NAME_WITH_DESCRIPTION + "')]")).click();

        getDriver().findElement(EDIT_DESCRIPTION_BUTTON).click();
        getDriver().findElement(DESCRIPTION_TEXT_FIELD).clear();
        getDriver().findElement(DESCRIPTION_TEXT_FIELD).sendKeys(FREESTYLE_DESCRIPTION);
        getDriver().findElement(DESCRIPTION_SAVE_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(DESCRIPTION_TEXT).getText(), FREESTYLE_DESCRIPTION);
    }

    @Test
    public void testCreateFreestyleProjectWithIncorrectCharacters() {
        final List<Character> incorrectNameCharacters = List.of(
                '!', '@', '#', '$', '%', '^', '&', '*', '[', ']', '\\', '|', ';', ':', '/', '?', '<', '>');

        getDriver().findElement(LINK_NEW_ITEM).click();
        for (Character character : incorrectNameCharacters) {
            getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).click();
            getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).clear();
            getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).sendKeys(String.valueOf(character));
            getDriver().findElement(LINK_FREESTYLE_PROJECT).click();

            Assert.assertEquals(getDriver().findElement(ITEM_NAME_INVALID).getText(),
                    "» ‘" + character + "’ is an unsafe character");
        }
    }

    @Test
    public void testCreateFreestyleProjectWithEngineerName() {

        final String expectedResult = "Engineer";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(expectedResult);
        getDriver().findElement(LINK_FREESTYLE_PROJECT).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        WebElement registeredProject = getDriver().findElement(By.xpath("//h1[@class='job-index-" +
                "headline page-headline']"));

        final String actualResult = registeredProject.getText().substring(registeredProject.getText().length() - 8);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithDupicateName")
    public void testCreateBuildNowOnFreestyleProjectPage() {
        final By countBuilds = By.xpath("//a[@class = 'model-link inside build-link display-name']");
        int countBuildsBeforeCreatingNewBuild = 0;
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));

        getDriver().findElement(By.linkText(NEW_FREESTYLE_NAME)).click();

        if (getDriver().findElement(By.id("no-builds")).isEnabled()) {
            countBuildsBeforeCreatingNewBuild = getDriver().findElements(countBuilds).size();
        }

        getDriver().findElement(BUILD_NOW_LOCATOR).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//span[@class='build-status-icon__outer']/*[@tooltip = 'In progress &gt; Console Output']")));
        int countBuildsAfterCreatingNewBuild = getDriver().findElements(countBuilds).size();

        Assert.assertEquals(countBuildsAfterCreatingNewBuild, countBuildsBeforeCreatingNewBuild + 1);
    }

    @Test(dependsOnMethods = "testCreateBuildNowOnFreestyleProjectPage")
    public void testFreestyleProjectBuild() {
        getDriver().findElement(By.linkText(NEW_FREESTYLE_NAME)).click();

        final int initialBuildCount = getDriver().findElements(BUILDS_LOCATOR).size();
        getDriver().findElement(BUILD_NOW_LOCATOR).click();
        final int actualBuildCount = getWait()
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(BUILDS_LOCATOR, initialBuildCount)).size();

        Assert.assertEquals(actualBuildCount, initialBuildCount + 1);
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithEngineerName")
    public void testRenameFreestyleProjectWithIncorrectName() {
        getDriver().findElement(By.xpath("//span[text()='Engineer']")).click();
        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys("!");
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']")).getText(),
                "Error\n‘!’ is an unsafe character");
    }
}