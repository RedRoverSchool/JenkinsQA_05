import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static runner.TestUtils.getRandomStr;
import static runner.TestUtils.scrollToElement;

public class FreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_NAME = getRandomStr(10);
    private static final String FREESTYLE_NAME_WITH_DESCRIPTION = getRandomStr(10);
    private static final String NEW_FREESTYLE_NAME = getRandomStr(10);
    private static final String FREESTYLE_DESCRIPTION = getRandomStr(10);
    private static final String VALID_FREESTYLE_PROJECT_NAME = "First_project";
    private static final String DESCRIPTION_INPUT = "Description Text";
    private static final Character INVALID_CHAR = '!';
    private static final String INVALID_FREESTYLE_PROJECT_NAME = INVALID_CHAR + VALID_FREESTYLE_PROJECT_NAME;
    private static final String SPACE_INSTEAD_OF_NAME = " ";
    private static final By BUTTON_ADD_NEW_ITEM = By.linkText("New Item");
    private static final By FIELD_ENTER_NAME = By.id("name");
    private static final By BUTTON_SELECT_FREESTYLE_PROJECT = By.cssSelector(".hudson_model_FreeStyleProject");
    private static final By BUTTON_OK = By.cssSelector("#ok-button");
    private static final By BUTTON_SELECT_MENU_CHANGES = By.linkText("Changes");
    private static final By BUTTON_SUBMIT = By.xpath("//button[@type = 'submit']");
    private static final By LIST_FREESTYLE_JOBS = By.xpath("//a[@class='jenkins-table__link model-link inside']");
    private static final By BUTTON_EDIT_DESCRIPTION = By.id("description-link");
    private static final By FIELD_ENTER_DESCRIPTION = By.xpath("//textarea[@name = 'description']");
    private static final By DESCRIPTION = By.xpath("//div[@id = 'description'] /div[1]");
    private static final By ITEM_NAME_INVALID = By.cssSelector("#itemname-invalid");
    private static final By JOB_HEADLINE_LOCATOR = By.xpath("//h1");
    private static final By MAIN_PANEL_LOCATOR = By.id("main-panel");
    private static final By BUILD_NOW_LOCATOR = By.linkText("Build Now");
    private static final By BUILDS_LOCATOR = By.xpath("//table[@class='pane jenkins-pane stripped']//tr[@page-entry-id]");
    private static final By BUTTON_ENABLE_DISABLE_PROJECT = By.id("yui-gen1-button");
    private static final By BUTTON_GO_TO_DASHBOARD = By.linkText("Dashboard");
    private static final By BUTTON_CONFIGURE = By.linkText("Configure");
    private static final By JENKINS_CURRENT_VERSION = By.xpath("//a [@rel = 'noopener noreferrer']");
    private static final By CONFIGURATION_PAGE_HEAD = By.xpath("//div[@class = 'jenkins-app-bar__content']/h1");
    private static final By DELETE_PROJECT_OPTION = By.xpath("//span[contains(text(),'Delete Project')]");

    private List<String> getListExistingFreestyleProjectsNames(By by) {
        return getDriver().findElements(by).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    private String getBuildStatus() {
        return getDriver().findElement(By.xpath("//span/span/*[name()='svg' and @class= 'svg-icon ']")).getAttribute("tooltip");
    }

    private void alertAccept() {
        getDriver().switchTo().alert().accept();
    }

    private void deleteFreestyleProject(String projectName) {
        getDriver().findElement(BUTTON_GO_TO_DASHBOARD).click();
        getDriver().findElement(By.xpath("//a[@href = 'job/" + projectName + "/']")).click();
        getDriver().findElement(DELETE_PROJECT_OPTION).click();
        alertAccept();
    }

    @Test
    public void testCreateNewFreestyleProject() {
        getWait(10).until(ExpectedConditions.elementToBeClickable(BUTTON_ADD_NEW_ITEM)).click();

        getDriver().findElement(FIELD_ENTER_NAME).sendKeys(FREESTYLE_NAME);
        getDriver().findElement(BUTTON_SELECT_FREESTYLE_PROJECT).click();
        getWait(10).until(ExpectedConditions.elementToBeClickable(BUTTON_OK)).click();
        getWait(10).until(ExpectedConditions.elementToBeClickable(BUTTON_SUBMIT)).click();

        Assert.assertEquals(getDriver().findElement(JOB_HEADLINE_LOCATOR).getText(), "Project " + FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithSpacesInsteadOfName")
    public void testCreateFreestyleProjectWithIncorrectCharacters() {
        final List<Character> incorrectNameCharacters = List.of('!', '@', '#', '$', '%', '^', '&', '*', '[', ']', '\\', '|', ';', ':', '/', '?', '<', '>');

        getDriver().findElement(BUTTON_ADD_NEW_ITEM).click();
        for (Character character : incorrectNameCharacters) {
            getDriver().findElement(FIELD_ENTER_NAME).click();
            getDriver().findElement(FIELD_ENTER_NAME).clear();
            getDriver().findElement(FIELD_ENTER_NAME).sendKeys(String.valueOf(character));
            getDriver().findElement(BUTTON_SELECT_FREESTYLE_PROJECT).click();

            Assert.assertEquals(getDriver().findElement(ITEM_NAME_INVALID).getText(), "» ‘" + character + "’ is an unsafe character");
        }
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithIncorrectCharacters")
    public void testDisableProject() {

        getDriver().findElement(By.xpath("//a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(BUTTON_ENABLE_DISABLE_PROJECT).click();

        Assert.assertEquals(getDriver().findElement(JOB_HEADLINE_LOCATOR).getText(), "Project " + FREESTYLE_NAME);
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'warning']")).getText().trim().substring(0, 34), "This project is currently disabled");

        getDriver().findElement(BUTTON_GO_TO_DASHBOARD).click();

        Assert.assertEquals(getBuildStatus(), "Disabled");
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testEnableProject() {

        getDriver().findElement(By.xpath("//a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(BUTTON_ENABLE_DISABLE_PROJECT).click();
        getDriver().findElement(BUTTON_GO_TO_DASHBOARD).click();

        Assert.assertEquals(getBuildStatus(), "Not built");
    }

    @Test(dependsOnMethods = "testEnableProject")
    public void testFreestyleProjectPageIsOpenedFromDashboard() {

        getDriver().findElement(BUTTON_GO_TO_DASHBOARD).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(), String.format("Project %s", FREESTYLE_NAME));
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h2")).getText(), "Permalinks");
        Assert.assertTrue(getDriver().findElement(BUTTON_ENABLE_DISABLE_PROJECT).isEnabled());
    }

    @Test(dependsOnMethods = "testFreestyleProjectPageIsOpenedFromDashboard")
    public void testPresentationNewProjectOnDashboard() {

        Assert.assertTrue(getListExistingFreestyleProjectsNames(LIST_FREESTYLE_JOBS).contains(FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testPresentationNewProjectOnDashboard")
    public void testAddDescriptionToFreestyleProject() {
        final String descriptionText = "This is job #" + FREESTYLE_NAME;

        getDriver().findElement(By.xpath("//a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(BUTTON_EDIT_DESCRIPTION).click();
        getDriver().findElement(FIELD_ENTER_DESCRIPTION).sendKeys("This is job #" + FREESTYLE_NAME);
        getDriver().findElement(By.xpath("//div[@class = 'textarea-preview-container']/a[1]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'textarea-preview']")).getText(), descriptionText);

        getDriver().findElement(BUTTON_SUBMIT).click();

        Assert.assertEquals(getDriver().findElement(DESCRIPTION).getText(), descriptionText);
    }

    @Test(dependsOnMethods = "testAddDescriptionToFreestyleProject")
    public void testNoBuildFreestyleProjectChanges() {
        getDriver().findElement(By.linkText(FREESTYLE_NAME)).click();
        getDriver().findElement(BUTTON_SELECT_MENU_CHANGES).click();

        final String actualChangesText = getDriver().findElement(MAIN_PANEL_LOCATOR).getText().replace("Changes\n", "");

        Assert.assertEquals(actualChangesText, "No builds.");
    }

    @Test(dependsOnMethods = "testNoBuildFreestyleProjectChanges")
    public void testRenameFreestyleProject() {

        getDriver().findElement(By.cssSelector("tr#job_" + FREESTYLE_NAME + " .jenkins-menu-dropdown-chevron")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + FREESTYLE_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.cssSelector("input[name='newName']")).clear();
        getDriver().findElement(By.cssSelector("input[name='newName']")).sendKeys(NEW_FREESTYLE_NAME);
        getDriver().findElement(BUTTON_ENABLE_DISABLE_PROJECT).click();
        getDriver().findElement(BUTTON_GO_TO_DASHBOARD).click();

        Assert.assertFalse(getListExistingFreestyleProjectsNames(LIST_FREESTYLE_JOBS).contains(FREESTYLE_NAME));
        Assert.assertTrue(getListExistingFreestyleProjectsNames(LIST_FREESTYLE_JOBS).contains(NEW_FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testRenameFreestyleProject")
    public void testViewFreestyleProjectPage() {
        getDriver().findElement(By.linkText(NEW_FREESTYLE_NAME)).click();

        Assert.assertEquals(getDriver().findElement(JOB_HEADLINE_LOCATOR).getText(), String.format("Project %s", NEW_FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testViewFreestyleProjectPage")
    public void testViewChangesNoBuildsSignAppears() {
        final String expectedText = "Changes\nNo builds.";

        getDriver().findElement(By.xpath("//span[contains(text(),'" + NEW_FREESTYLE_NAME + "')]")).click();
        getDriver().findElement(BUTTON_SELECT_MENU_CHANGES).click();

        final String actualText = getDriver().findElement(MAIN_PANEL_LOCATOR).getText();

        Assert.assertEquals(actualText, expectedText);
    }

    @Test(dependsOnMethods = "testViewChangesNoBuildsSignAppears")
    public void testFreestyleProjectConfigureByDropdown() {
        getDriver().findElement(By.cssSelector("#job_" + NEW_FREESTYLE_NAME + " .jenkins-menu-dropdown-chevron")).click();
        WebElement element = getDriver().findElement(BUTTON_CONFIGURE);
        scrollToElement(getDriver(), element);
        element.click();

        Assert.assertEquals(getDriver().getTitle(), NEW_FREESTYLE_NAME + " Config [Jenkins]");
    }

    @Test(dependsOnMethods = "testFreestyleProjectConfigureByDropdown")
    public void testFreestyleProjectConfigureMenu() {
        getDriver().findElement(By.xpath("//a[@href='job/" + NEW_FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(BUTTON_CONFIGURE).click();

        List<String> textConfigMenu = new ArrayList<>();
        List<WebElement> configMenu = getDriver().findElements(By.cssSelector("button.task-link"));
        for (WebElement element : configMenu) {
            textConfigMenu.add(element.getText());
        }

        List<String> actualConfigMenu = new ArrayList<>();
        actualConfigMenu.add("General");
        actualConfigMenu.add("Source Code Management");
        actualConfigMenu.add("Build Triggers");
        actualConfigMenu.add("Build Environment");
        actualConfigMenu.add("Build Steps");
        actualConfigMenu.add("Post-build Actions");

        Assert.assertEqualsNoOrder(textConfigMenu, actualConfigMenu);
    }

    @Test(dependsOnMethods = "testFreestyleProjectConfigureMenu")
    public void testCreateNewFreestyleProjectWithDupicateName() {
        getDriver().findElement(BUTTON_ADD_NEW_ITEM).click();

        getDriver().findElement(FIELD_ENTER_NAME).click();
        getDriver().findElement(FIELD_ENTER_NAME).sendKeys(NEW_FREESTYLE_NAME);
        getDriver().findElement(BUTTON_SELECT_FREESTYLE_PROJECT).click();

        Assert.assertEquals(getDriver().findElement(ITEM_NAME_INVALID).getText(), String.format("» A job already exists with the name ‘%s’", NEW_FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testFreestyleProjectBuild")
    public void testDeleteFreestyleProject() {

        getDriver().findElement(By.cssSelector("tr#job_" + NEW_FREESTYLE_NAME + " .jenkins-menu-dropdown-chevron")).click();
        getDriver().findElement(By.xpath("//span[contains(text(),'Delete Project')]")).click();

        getDriver().switchTo().alert().accept();

        Assert.assertEquals(getDriver().findElement(JOB_HEADLINE_LOCATOR).getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testCreateFreestyleProjectWithDescription() {
        final String description = "Some Description Text";

        getDriver().findElement(BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(FREESTYLE_NAME_WITH_DESCRIPTION);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
        getDriver().findElement(BUTTON_SUBMIT).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), description);
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(), String.format("Project %s", FREESTYLE_NAME_WITH_DESCRIPTION));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithDescription")
    public void testEditFreestyleProjectWithDescription() {
        getDriver().findElement(By.xpath("//span[contains(text(),'" + FREESTYLE_NAME_WITH_DESCRIPTION + "')]")).click();

        getDriver().findElement(BUTTON_EDIT_DESCRIPTION).click();
        getDriver().findElement(FIELD_ENTER_DESCRIPTION).clear();
        getDriver().findElement(FIELD_ENTER_DESCRIPTION).sendKeys(FREESTYLE_DESCRIPTION);
        getDriver().findElement(BUTTON_SUBMIT).click();

        Assert.assertEquals(getDriver().findElement(DESCRIPTION).getText(), FREESTYLE_DESCRIPTION);
    }

    @Test
    public void testCreateFreestyleProjectWithEngineerName() {

        final String expectedResult = "Engineer";

        getDriver().findElement(BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(By.id("name")).sendKeys(expectedResult);
        getDriver().findElement(BUTTON_SELECT_FREESTYLE_PROJECT).click();
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        WebElement registeredProject = getDriver().findElement(By.xpath("//h1[@class='job-index-" + "headline page-headline']"));

        final String actualResult = registeredProject.getText().substring(registeredProject.getText().length() - 8);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithDupicateName")
    public void testCreateBuildNowOnFreestyleProjectPage() {
        int countBuildsBeforeCreatingNewBuild = 0;

        getDriver().findElement(By.linkText(NEW_FREESTYLE_NAME)).click();

        if (getDriver().findElement(By.cssSelector(".collapse")).getAttribute("title").equals("expand")) {
            getDriver().findElement(By.cssSelector(".collapse")).click();
        }
        if (!getDriver().findElement(By.id("no-builds")).isDisplayed()) {
            countBuildsBeforeCreatingNewBuild = getDriver().findElements(BUILDS_LOCATOR).size();
        }
        getDriver().findElement(BUILD_NOW_LOCATOR).click();
        getWait(20).until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//span[@class='build-status-icon__outer']/*[@tooltip = 'In progress &gt; Console Output']")));
        int countBuildsAfterCreatingNewBuild = getDriver().findElements(BUILDS_LOCATOR).size();

        Assert.assertEquals(countBuildsAfterCreatingNewBuild, countBuildsBeforeCreatingNewBuild + 1);
    }

    @Test
    public void testCreateFreestyleProject() {
        String name = getRandomStr(5);
        getDriver().findElement(BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(FIELD_ENTER_NAME).sendKeys(name);
        getDriver().findElement(BUTTON_SELECT_FREESTYLE_PROJECT).click();
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(BUTTON_SUBMIT).click();
        getDriver().findElement(BUTTON_GO_TO_DASHBOARD).click();

        List<WebElement> lstWithElements = getDriver().findElements(By.xpath("//table[@id='projectstatus']//tbody//tr//td//a"));
        List<String> lstWithStrings = lstWithElements.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertTrue(lstWithStrings.contains(name));
    }

    @Test(dependsOnMethods = "testCreateBuildNowOnFreestyleProjectPage")
    public void testFreestyleProjectBuild() {
        getDriver().findElement(By.linkText(NEW_FREESTYLE_NAME)).click();

        final int initialBuildCount = getDriver().findElements(BUILDS_LOCATOR).size();
        getDriver().findElement(BUILD_NOW_LOCATOR).click();
        final int actualBuildCount = getWait(20).until(ExpectedConditions.numberOfElementsToBeMoreThan(BUILDS_LOCATOR, initialBuildCount)).size();

        Assert.assertEquals(actualBuildCount, initialBuildCount + 1);

    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithEngineerName")
    public void testRenameFreestyleProjectWithIncorrectName() {
        getDriver().findElement(By.xpath("//span[text()='Engineer']")).click();
        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys("!");
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']")).getText(), "Error\n‘!’ is an unsafe character");
    }

    @Test(dependsOnMethods = "testEditFreestyleProjectWithDescription")
    public void testFreestyleProjectSideMenu() {

        final Set<String> expectedFreestyleProjectSideMenu = new TreeSet<>(List.of("General", "Source Code Management", "Build Triggers", "Build Environment", "Build Steps", "Post-build Actions"));

        getDriver().findElements(By.xpath("//tr/td/a")).get(0).click();
        getDriver().findElement(By.linkText("Configure")).click();

        List<WebElement> freestyleProjectSideMenu = getDriver().findElements(By.cssSelector("button.task-link"));
        Set<String> actualFreestyleProjectSideMenu = new TreeSet<>();
        for (WebElement menu : freestyleProjectSideMenu) {
            actualFreestyleProjectSideMenu.add(menu.getText());
        }

        Assert.assertEquals(actualFreestyleProjectSideMenu, expectedFreestyleProjectSideMenu);
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProject")
    public void testCreateFreestyleProjectWithEmptyName() {
        getDriver().findElement(BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(BUTTON_SELECT_FREESTYLE_PROJECT).click();

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(), "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(getDriver().findElement(BUTTON_OK).isEnabled());
    }

    @Test
    public void testCreateFreestyleProjectWithValidNameAndDescription() {
        getDriver().findElement(BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(FIELD_ENTER_NAME).sendKeys(VALID_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(BUTTON_SELECT_FREESTYLE_PROJECT).click();
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(FIELD_ENTER_DESCRIPTION).sendKeys(DESCRIPTION_INPUT);
        getDriver().findElement(BUTTON_SUBMIT).click();

        Assert.assertEquals(getDriver().findElement(JOB_HEADLINE_LOCATOR).getText(), "Project " + VALID_FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(getDriver().findElement(DESCRIPTION).getText(), DESCRIPTION_INPUT);
    }

    @Test
    public void testCreateFreestyleProjectWithInvalidCharBeforeName() {
        getDriver().findElement(BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(FIELD_ENTER_NAME).sendKeys(INVALID_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(BUTTON_SELECT_FREESTYLE_PROJECT).click();

        Assert.assertEquals(getWait(20).until(ExpectedConditions.presenceOfElementLocated(ITEM_NAME_INVALID)).getText(),
                "» ‘" + INVALID_CHAR + "’ is an unsafe character");
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithEmptyName")
    public void testCreateFreestyleProjectWithSpacesInsteadOfName() {
        getDriver().findElement(BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(FIELD_ENTER_NAME).sendKeys(SPACE_INSTEAD_OF_NAME);
        getDriver().findElement(BUTTON_SELECT_FREESTYLE_PROJECT).click();
        getDriver().findElement(BUTTON_OK).click();

        Assert.assertEquals(getDriver().findElement(JOB_HEADLINE_LOCATOR).getText(), "Error");
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#main-panel > p")).getText(), "No name is specified");
    }

    @Test
    public void testAccessProjectConfigurationFromTheProjectPage() {
        final String NAME_FREESTYLE_PROJECT_TC010401 = NEW_FREESTYLE_NAME + "TC010401";
        final By FIND_NAME_FREESTYLE_PROJECT_TC010401 =
                By.xpath("//a[@href = 'job/" + NAME_FREESTYLE_PROJECT_TC010401 + "/']");
        final By CONFIG_NAME_FREESTYLE_PROJECT_TC010401 =
                By.xpath("//a[@href='/job/" + NAME_FREESTYLE_PROJECT_TC010401 + "/configure']");

        getDriver().findElement(BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(FIELD_ENTER_NAME).sendKeys(NAME_FREESTYLE_PROJECT_TC010401);
        getDriver().findElement(BUTTON_SELECT_FREESTYLE_PROJECT).click();
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(BUTTON_GO_TO_DASHBOARD).click();
        getDriver().findElement(FIND_NAME_FREESTYLE_PROJECT_TC010401).click();
        getDriver().findElement(CONFIG_NAME_FREESTYLE_PROJECT_TC010401).click();

        Assert.assertTrue(getDriver().findElement(CONFIGURATION_PAGE_HEAD).getText().contains("Configuration"));
        Assert.assertTrue(getDriver().findElement(JENKINS_CURRENT_VERSION).getText().contains("Jenkins 2.361.4"));

        deleteFreestyleProject(NAME_FREESTYLE_PROJECT_TC010401);
    }

    @Test
    public void testAddingDescription() {
        getDriver().findElement(BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(FIELD_ENTER_NAME).sendKeys("New Project");
        getDriver().findElement(BUTTON_SELECT_FREESTYLE_PROJECT).click();
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(FIELD_ENTER_DESCRIPTION).sendKeys("SSS");
        getDriver().findElement(BUTTON_SUBMIT).click();

        Assert.assertEquals(getDriver().findElement(DESCRIPTION).getText(), "SSS");
    }

    @Test
    public void testCreateNewFreestyleProjectWithLongNameFrom256Characters() {
        final String expectedURL = getDriver().getCurrentUrl()+"view/all/createItem";
        final String errorPictureName = "rage.svg";
        final String expectedTextOfError = "A problem occurred while processing the request.";
        final String longNameWith256Characters = getRandomStr(256);

        getDriver().findElement(BUTTON_ADD_NEW_ITEM).click();
        getWait(5).until(ExpectedConditions.elementToBeClickable(FIELD_ENTER_NAME))
                .sendKeys(longNameWith256Characters);
        getWait(5).until(ExpectedConditions.attributeContains(FIELD_ENTER_NAME,
                "value", longNameWith256Characters));
        getDriver().findElement(BUTTON_SELECT_FREESTYLE_PROJECT).click();
        TestUtils.scrollToElement(getDriver(), getDriver().findElement(BUTTON_OK));
        getWait(5).until(ExpectedConditions.elementToBeClickable(BUTTON_OK)).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), expectedURL);
        Assert.assertTrue(getDriver().findElement(
                By.xpath("//img[contains(@src,'"+errorPictureName+"')]")).isDisplayed());
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='error-description']//h2")).getText(),
                expectedTextOfError);
    }
}
