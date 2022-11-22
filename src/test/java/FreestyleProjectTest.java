import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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

    private String getRandomName() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private void createProjectFromDashboard(By type, String name) {
        getDriver().findElement(LINK_NEW_ITEM).click();
        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).sendKeys(name);
        getDriver().findElement(type).click();
        getDriver().findElement(BUTTON_OK_IN_NEW_ITEM).click();
        getDriver().findElement(BUTTON_SAVE).click();
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
    public void testPresentationNewProjectOnDashboard() {

        Assert.assertTrue(getListExistingFreestyleProjectsNames(LIST_FREESTYLE_JOBS).contains(FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithCorrectName", priority = 3)
    public void testRenameFreestyleProject() {

        getDriver().findElement(By.cssSelector("tr#job_" + FREESTYLE_NAME + " .jenkins-menu-dropdown-chevron")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + FREESTYLE_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.cssSelector("input[name='newName']")).clear();
        getDriver().findElement(By.cssSelector("input[name='newName']")).sendKeys(NEW_FREESTYLE_NAME);
        getDriver().findElement(By.cssSelector("#yui-gen1-button")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertFalse(getListExistingFreestyleProjectsNames(LIST_FREESTYLE_JOBS).contains(FREESTYLE_NAME));
        Assert.assertTrue(getListExistingFreestyleProjectsNames(LIST_FREESTYLE_JOBS).contains(NEW_FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testRenameFreestyleProject")
    public void testViewChangesNoBuildsSignAppears() {
        String expectedText = "Changes\nNo builds.";

        getDriver().findElement(By.xpath("//span[contains(text(),'" + NEW_FREESTYLE_NAME + "')]")).click();
        getDriver().findElement(LINK_CHANGES).click();

        String actualText = getDriver().findElement(MAIN_PANEL_LOCATOR).getText();

        Assert.assertEquals(actualText, expectedText);
    }

    @Test(dependsOnMethods = {"testViewChangesNoBuildsSignAppears", "testAddDescriptionToFreestyleProject"}, priority = 4)
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

    @Ignore
    @Test
    public void testCreateFreestyleProjectWithIncorrectCharacters() {
        final List<Character> incorrectNameCharacters = List.of(
                '!', '@', '#', '$', '%', '^', '&', '*', '[', ']', '\\', '|', ';', ':', '/', '?', '<', '>');

        getDriver().findElement(LINK_NEW_ITEM).click();
        for (Character character : incorrectNameCharacters) {
            getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).click();
            getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).clear();
            getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).sendKeys(String.valueOf(character));

            Assert.assertEquals(getDriver().findElement(ITEM_NAME_INVALID).getText(),
                    "» ‘" + character + "’ is an unsafe character");
        }
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithCorrectName", priority = 1)
    public void testViewFreestyleProjectPage() {
        getDriver().findElement(By.linkText(FREESTYLE_NAME)).click();

        Assert.assertEquals(getDriver().findElement(JOB_HEADLINE_LOCATOR).getText()
                , String.format("Project %s", FREESTYLE_NAME));
     }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithCorrectName", priority = 2)
    public void testNoBuildFreestyleProjectChanges() {
        getDriver().findElement(By.linkText(FREESTYLE_NAME)).click();
        getDriver().findElement(LINK_CHANGES).click();

        final String actualChangesText = getDriver().findElement(MAIN_PANEL_LOCATOR)
                .getText().replace("Changes\n", "");

        Assert.assertEquals(actualChangesText, "No builds.");
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithCorrectName")
    public void testAddDescriptionToFreestyleProject(){
        final String descriptionText = "This is job #" + FREESTYLE_NAME;

        getDriver().findElement(By.xpath("//a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(EDIT_DESCRIPTION_BUTTON).click();
        getDriver().findElement(DESCRIPTION_TEXT_FIELD).sendKeys("This is job #" + FREESTYLE_NAME);
        getDriver().findElement(By.xpath("//div[@class = 'textarea-preview-container']/a[1]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'textarea-preview']")).getText(), descriptionText);

        getDriver().findElement(DESCRIPTION_SAVE_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(DESCRIPTION_TEXT).getText(), descriptionText);
    }

    @Test
    public void testCreateFreestyleProjectWithEngineerName() {

        final String expectedResult = "Engineer";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(expectedResult);
        getDriver().findElement(By.className("label")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        WebElement registeredProject = getDriver().findElement(By.xpath("//h1[@class='job-index-" +
                "headline page-headline']"));

        final String actualResult = registeredProject.getText().substring(registeredProject.getText().length()-8);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testCreateBuildNowOnFreestyleProjectPage() {
        final String freestyleProjectName = getRandomName();
        final By countBuilds = By.xpath("//a[@class = 'model-link inside build-link display-name']");
        int countBuildsBeforeCreatingNewBuild = 0;
        WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(20));

        createProjectFromDashboard(LINK_FREESTYLE_PROJECT, freestyleProjectName);
        getDriver().findElement(By.linkText(freestyleProjectName)).click();

        if (getDriver().findElement(By.id("no-builds")).isEnabled()) {
            countBuildsBeforeCreatingNewBuild = getDriver().findElements(countBuilds).size();
        }

        getDriver().findElement(By.linkText("Build Now")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//span[@class='build-status-icon__outer']/*[@tooltip = 'In progress &gt; Console Output']")));
        int countBuildsAfterCreatingNewBuild = getDriver().findElements(countBuilds).size();

        Assert.assertEquals(countBuildsAfterCreatingNewBuild, countBuildsBeforeCreatingNewBuild + 1);
    }
}