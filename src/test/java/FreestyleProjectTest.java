import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final String FREESTYLE_NAME_WITH_DESCRIPTION = RandomStringUtils.randomAlphanumeric(10);
    private static final String NEW_FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final String FREESTYLE_DESCRIPTION = RandomStringUtils.randomAlphanumeric(10);
    private static final String VALID_FREESTYLE_PROJECT_NAME = "First project";
    private static final String DESCRIPTION_INPUT = "Description Text";
    private static final Character INVALID_CHAR = '!';
    private static final String INVALID_FREESTYLE_PROJECT_NAME = INVALID_CHAR + VALID_FREESTYLE_PROJECT_NAME;
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
    private static final By ENABLE_DISABLE_PROJECT_BUTTON = By.id("yui-gen1-button");
    private static final By GO_TO_DASHBOARD_BUTTON = By.linkText("Dashboard");
    private static final By CONFIGURE_BUTTON = By.linkText("Configure");

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

    private List<WebElement> getJobSpecifications(String nameJob) {
        return getDriver().findElements(By.xpath("//tr[@id = 'job_" + nameJob + "']/td"));
    }

    private String getBuildStatus(){
        return getDriver().findElement(By.xpath("//span/span/*[name()='svg' and @class= 'svg-icon ']")).getAttribute("tooltip");
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
        getDriver().findElement(ENABLE_DISABLE_PROJECT_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Project " + FREESTYLE_NAME);
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'warning']")).getText().trim().substring(0, 34),
                "This project is currently disabled");

        getDriver().findElement(GO_TO_DASHBOARD_BUTTON).click();

        Assert.assertEquals(getBuildStatus(), "Disabled");
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testEnableProject() {

        getDriver().findElement(By.xpath("//a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(ENABLE_DISABLE_PROJECT_BUTTON).click();
        getDriver().findElement(GO_TO_DASHBOARD_BUTTON).click();

        Assert.assertEquals(getBuildStatus(), "Not built");
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

    @Test(dependsOnMethods = "testConfigureSourceCodeGIT")
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
        String expectedText = "Changes\nNo changes in any of the builds.";

        getDriver().findElement(By.xpath("//span[contains(text(),'" + NEW_FREESTYLE_NAME + "')]")).click();
        getDriver().findElement(LINK_CHANGES).click();

        String actualText = getDriver().findElement(MAIN_PANEL_LOCATOR).getText();

        Assert.assertEquals(actualText, expectedText);
    }

    @Test(dependsOnMethods = "testViewChangesNoBuildsSignAppears")
    public void testFreestyleProjectConfigureByDropdown() {
        getDriver().findElement(By.cssSelector("#job_" + NEW_FREESTYLE_NAME + " .jenkins-menu-dropdown-chevron")).click();
        WebElement element = getDriver().findElement(CONFIGURE_BUTTON);
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);

        Assert.assertEquals(getDriver().getTitle(), NEW_FREESTYLE_NAME + " Config [Jenkins]");
    }

    @Test(dependsOnMethods = "testFreestyleProjectConfigureByDropdown")
    public void testFreestyleProjectConfigureMenu() {
        getDriver().findElement(By.xpath("//a[@href='job/" + NEW_FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(CONFIGURE_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//button[@data-section-id='general']"))
                .getText(), "General");
        Assert.assertEquals(getDriver().findElement(By.xpath("//button[@data-section-id='source-code-management']"))
                .getText(), "Source Code Management");
        Assert.assertEquals(getDriver().findElement(By.xpath("//button[@data-section-id='build-triggers']"))
                .getText(), "Build Triggers");
        Assert.assertEquals(getDriver().findElement(By.xpath("//button[@data-section-id='build-environment']"))
                .getText(), "Build Environment");
        Assert.assertEquals(getDriver().findElement(By.xpath("//button[@data-section-id='build-steps']"))
                .getText(), "Build Steps");
        Assert.assertEquals(getDriver().findElement(By.xpath("//button[@data-section-id='post-build-actions']"))
                .getText(), "Post-build Actions");
    }

    @Test(dependsOnMethods = "testFreestyleProjectConfigureMenu")
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

    @Test
    public void testCreateFreestyleProject() {
        String name = TestUtils.getRandomStr(5);
        getDriver().findElement(LINK_NEW_ITEM).click();
        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).sendKeys(name);
        getDriver().findElement(LINK_FREESTYLE_PROJECT).click();
        getDriver().findElement(BUTTON_OK_IN_NEW_ITEM).click();
        getDriver().findElement(BUTTON_SAVE).click();
        getDriver().findElement(GO_TO_DASHBOARD_BUTTON).click();

        List<WebElement> lstWithElements = getDriver().findElements(By.xpath("//table[@id='projectstatus']//tbody//tr//td//a"));
        List<String> lstWithStrings = lstWithElements.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertTrue(lstWithStrings.contains(name));
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

    @Test(dependsOnMethods = "testEditFreestyleProjectWithDescription")
    public void testFreestyleProjectSideMenu() {

        final Set<String> expectedFreestyleProjectSideMenu = new TreeSet<>(List.of("General", "Source Code Management", "Build Triggers", "Build Environment", "Build Steps", "Post-build Actions"));

        getDriver().findElements(By.xpath("//tr/td/a")).get(0).click();
        getDriver().findElement(By.linkText("Configure")).click();

        List<WebElement> freestyleProjectSideMenu = getDriver().findElements(By.cssSelector("button.task-link"));
        Set<String> actualFreestyleProjectSideMenu = new TreeSet<>();
        for(WebElement menu : freestyleProjectSideMenu) {
            actualFreestyleProjectSideMenu.add(menu.getText());
        }

        Assert.assertEquals(actualFreestyleProjectSideMenu, expectedFreestyleProjectSideMenu);
    }

    @Test
    public void testCreateFreestyleProjectWithEmptyName() {
        getDriver().findElement(LINK_NEW_ITEM).click();
        getDriver().findElement(LINK_FREESTYLE_PROJECT).click();

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(getDriver().findElement(BUTTON_OK_IN_NEW_ITEM).isEnabled());
    }

    @Test
    public void testCreateFreestyleProjectWithValidNameAndDescription() {
        getDriver().findElement(LINK_NEW_ITEM).click();
        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).sendKeys(VALID_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(LINK_FREESTYLE_PROJECT).click();
        getDriver().findElement(BUTTON_OK_IN_NEW_ITEM).click();
        getDriver().findElement(DESCRIPTION_TEXT_FIELD).sendKeys(DESCRIPTION_INPUT);
        getDriver().findElement(BUTTON_SAVE).click();

        Assert.assertEquals(getDriver().findElement(JOB_HEADLINE_LOCATOR).getText(),
                "Project " + VALID_FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(getDriver().findElement(DESCRIPTION_TEXT).getText(), DESCRIPTION_INPUT);
    }

    @Test
    public void testCreateFreestyleProjectWithInvalidCharBeforeName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));

        getDriver().findElement(LINK_NEW_ITEM).click();
        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).sendKeys(INVALID_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(LINK_FREESTYLE_PROJECT).click();

        Assert.assertEquals(wait.until(ExpectedConditions.presenceOfElementLocated(ITEM_NAME_INVALID)).getText(),
                "» ‘" + INVALID_CHAR + "’ is an unsafe character");
    }

    @Test(dependsOnMethods = "testNoBuildFreestyleProjectChanges")
    public void testConfigureSourceCodeGIT() {
        final String repositoryURL = "https://github.com/AlekseiChapaev/TestingJenkinsRepo.git";
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));

        getDriver().findElement(By.xpath("//a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(CONFIGURE_BUTTON).click();
        getDriver().findElement(By.xpath("//button[@data-section-id= 'source-code-management']")).click();
        getDriver().findElement(By.xpath("//label[text() = 'Git']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@name= 'userRemoteConfigs']/div[@style]/div[1][@class = 'jenkins-form-item tr  has-help']/div[2]"))).click();
        getDriver().findElement(By.xpath("//div[@name= 'userRemoteConfigs']/div[@style]/div[1][@class = 'jenkins-form-item tr  has-help']/div[2]/input")).sendKeys(repositoryURL);
        getDriver().findElement(BUTTON_SAVE).click();

        getDriver().findElement(GO_TO_DASHBOARD_BUTTON).click();
        getJobSpecifications(FREESTYLE_NAME).get(6).click();

        while(getJobSpecifications(FREESTYLE_NAME).get(5).getText().equals("N/A")){
            getDriver().navigate().refresh();
        }

        Assert.assertEquals(getBuildStatus(), "Success");
        Assert.assertTrue(getJobSpecifications(FREESTYLE_NAME).get(3).getText().contains("#1"));
    }
}