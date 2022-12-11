import model.FreestyleProjectConfigPage;
import model.HomePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
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
    private static final String VALID_FREESTYLE_PROJECT_NAME = "First project";
    private static final String DESCRIPTION_INPUT = "Description Text";
    private static final Character INVALID_CHAR = '!';
    private static final String INVALID_FREESTYLE_PROJECT_NAME = INVALID_CHAR + VALID_FREESTYLE_PROJECT_NAME;
    private static final String SPACE_INSTEAD_OF_NAME = " ";
    private static final By BY_BUTTON_ADD_NEW_ITEM = By.linkText("New Item");
    private static final By BY_FIELD_ENTER_NAME = By.id("name");
    private static final By BY_BUTTON_SELECT_FREESTYLE_PROJECT = By.cssSelector(".hudson_model_FreeStyleProject");
    private static final By BY_BUTTON_OK = By.cssSelector("#ok-button");
    private static final By BY_BUTTON_SELECT_MENU_CHANGES = By.linkText("Changes");
    private static final By BY_BUTTON_SUBMIT = By.xpath("//button[@type = 'submit']");
    private static final By BY_LIST_FREESTYLE_JOBS = By.xpath("//a[@class='jenkins-table__link model-link inside']");
    private static final By BY_DESCRIPTION_LINK = By.id("description-link");
    private static final By BY_DESCRIPTION_TEXT_FIELD = By.xpath("//textarea[@name = 'description']");
    private static final By BY_BUTTON_SAVE = By.id("yui-gen2-button");
    private static final By BY_DESCRIPTION_TEXT = By.xpath("//div[@id = 'description'] /div[1]");
    private static final By BY_ITEM_NAME_INVALID_MESSAGE = By.cssSelector("#itemname-invalid");
    private static final By BY_NEW_ITEM_NAME_HEADLINE = By.xpath("//h1");
    private static final By BY_MAIN_PANEL_AREA = By.id("main-panel");
    private static final By BY_SIDE_PANEL_BUILD_NOW = By.linkText("Build Now");
    private static final By BY_BUILD_ROW = By.xpath("//table[@class='pane jenkins-pane stripped']//tr[@page-entry-id]");
    private static final By BY_BUTTON_ENABLE_DISABLE_PROJECT = By.id("yui-gen1-button");
    private static final By BY_GO_TO_DASHBOARD_LINK = By.linkText("Dashboard");
    private static final By BY_BUTTON_DROPDOWN_CONFIGURE = By.linkText("Configure");
    private static final By BY_DROPDOWN_DELETE_PROJECT = By.xpath("//span[contains(text(),'Delete Project')]");

    private List<String> getListExistingFreestyleProjectsNames(By by) {
        return getDriver().findElements(by).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    private String getBuildStatus() {
        return getDriver().findElement(By.xpath("//span/span/*[name()='svg' and @class= 'svg-icon ']")).getAttribute("tooltip");
    }

    @Test
    public void testCreateNewFreestyleProject() {
        final String freestyleProjectTitle = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(FREESTYLE_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn()
                .getHeadlineText();

        Assert.assertEquals(freestyleProjectTitle, String.format("Project %s", FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithSpacesInsteadOfName")
    public void testCreateFreestyleProjectWithIncorrectCharacters() {
        final List<Character> incorrectNameCharacters = List.of('!', '@', '#', '$', '%', '^', '&', '*', '[', ']', '\\', '|', ';', ':', '/', '?', '<', '>');

        getDriver().findElement(BY_BUTTON_ADD_NEW_ITEM).click();
        for (Character character : incorrectNameCharacters) {
            getDriver().findElement(BY_FIELD_ENTER_NAME).click();
            getDriver().findElement(BY_FIELD_ENTER_NAME).clear();
            getDriver().findElement(BY_FIELD_ENTER_NAME).sendKeys(String.valueOf(character));
            getDriver().findElement(BY_BUTTON_SELECT_FREESTYLE_PROJECT).click();

            Assert.assertEquals(getWait(1).until(ExpectedConditions.presenceOfElementLocated(BY_ITEM_NAME_INVALID_MESSAGE)).getText(), "» ‘" + character + "’ is an unsafe character");
        }
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithIncorrectCharacters")
    public void testDisableProject() {

        getDriver().findElement(By.xpath("//a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(BY_BUTTON_ENABLE_DISABLE_PROJECT).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Project " + FREESTYLE_NAME);
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'warning']")).getText().trim().substring(0, 34), "This project is currently disabled");

        getDriver().findElement(BY_GO_TO_DASHBOARD_LINK).click();

        Assert.assertEquals(getBuildStatus(), "Disabled");
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testEnableProject() {
        final String jobStatusIconTooltip = new HomePage(getDriver())
                .clickFreestyleProjectName(FREESTYLE_NAME)
                .clickDisableOrEnableSwitchBtn()
                .clickDashboard()
                .getJobBuildStatus(FREESTYLE_NAME);

        Assert.assertEquals(jobStatusIconTooltip, "Not built");
    }

    @Test(dependsOnMethods = "testEnableProject")
    public void testFreestyleProjectPageIsOpenedFromDashboard() {

        getDriver().findElement(BY_GO_TO_DASHBOARD_LINK).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + FREESTYLE_NAME + "/']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(), String.format("Project %s", FREESTYLE_NAME));
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h2")).getText(), "Permalinks");
        Assert.assertTrue(getDriver().findElement(BY_BUTTON_ENABLE_DISABLE_PROJECT).isEnabled());
    }

    @Test(dependsOnMethods = "testFreestyleProjectPageIsOpenedFromDashboard")
    public void testPresentationNewProjectOnDashboard() {

        Assert.assertTrue(getListExistingFreestyleProjectsNames(BY_LIST_FREESTYLE_JOBS).contains(FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testPresentationNewProjectOnDashboard")
    public void testAddDescriptionToFreestyleProject() {
        final String descriptionText = "This is job #" + FREESTYLE_NAME;

        String freestyleProjectDescription = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickButtonAddDescription()
                .inputAndSaveDescriptionText(descriptionText)
                .getDescriptionText();

        Assert.assertEquals(freestyleProjectDescription, descriptionText);
    }

    @Test(dependsOnMethods = "testAddDescriptionToFreestyleProject")
    public void testNoBuildFreestyleProjectChanges() {
        getDriver().findElement(By.linkText(FREESTYLE_NAME)).click();
        getDriver().findElement(BY_BUTTON_SELECT_MENU_CHANGES).click();

        final String actualChangesText = getDriver().findElement(BY_MAIN_PANEL_AREA).getText().replace("Changes\n", "");

        Assert.assertEquals(actualChangesText, "No builds.");
    }

    @Test(dependsOnMethods = "testNoBuildFreestyleProjectChanges")
    public void testRenameFreestyleProject() {

        List<String> jobsList = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickRenameButton()
                .clearFieldAndInputNewName(NEW_FREESTYLE_NAME)
                .clickSubmitButton()
                .clickDashboard()
                .getJobList();

        Assert.assertFalse(jobsList.contains(FREESTYLE_NAME));
        Assert.assertTrue(jobsList.contains(NEW_FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testRenameFreestyleProject")
    public void testViewFreestyleProjectPage() {
        getDriver().findElement(By.linkText(NEW_FREESTYLE_NAME)).click();

        Assert.assertEquals(getDriver().findElement(BY_NEW_ITEM_NAME_HEADLINE).getText(), String.format("Project %s", NEW_FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testViewFreestyleProjectPage")
    public void testViewChangesNoBuildsSignAppears() {
        final String expectedText = "Changes\nNo builds.";

        getDriver().findElement(By.xpath("//span[contains(text(),'" + NEW_FREESTYLE_NAME + "')]")).click();
        getDriver().findElement(BY_BUTTON_SELECT_MENU_CHANGES).click();

        final String actualText = getDriver().findElement(BY_MAIN_PANEL_AREA).getText();

        Assert.assertEquals(actualText, expectedText);
    }

    @Test(dependsOnMethods = "testViewChangesNoBuildsSignAppears")
    public void testFreestyleProjectConfigureByDropdown() {
        getDriver().findElement(By.cssSelector("#job_" + NEW_FREESTYLE_NAME + " .jenkins-menu-dropdown-chevron")).click();
        WebElement element = getWait(3).until(ExpectedConditions.presenceOfElementLocated(BY_BUTTON_DROPDOWN_CONFIGURE));
        scrollToElement(getDriver(), element);
        element.click();

        Assert.assertEquals(getDriver().getTitle(), NEW_FREESTYLE_NAME + " Config [Jenkins]");
    }

    @Test(dependsOnMethods = "testFreestyleProjectConfigureByDropdown")
    public void testFreestyleProjectConfigureMenu() {
        getDriver().findElement(By.xpath("//a[@href='job/" + NEW_FREESTYLE_NAME + "/']")).click();
        getDriver().findElement(BY_BUTTON_DROPDOWN_CONFIGURE).click();

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
        getDriver().findElement(BY_BUTTON_ADD_NEW_ITEM).click();

        getDriver().findElement(BY_FIELD_ENTER_NAME).click();
        getDriver().findElement(BY_FIELD_ENTER_NAME).sendKeys(NEW_FREESTYLE_NAME);
        getDriver().findElement(BY_BUTTON_SELECT_FREESTYLE_PROJECT).click();

        Assert.assertEquals(getDriver().findElement(BY_ITEM_NAME_INVALID_MESSAGE).getText(), String.format("» A job already exists with the name ‘%s’", NEW_FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testFreestyleProjectBuild")
    public void testDeleteFreestyleProject() {

        String pageHeaderText = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickButtonDeleteProject()
                .confirmAlertAndDeleteProject()
                .getHeaderText();

        Assert.assertEquals(pageHeaderText, "Welcome to Jenkins!");
    }

    @Test
    public void testCreateFreestyleProjectWithDescription() {
        final String description = "Some Description Text";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(FREESTYLE_NAME_WITH_DESCRIPTION);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
        getDriver().findElement(BY_BUTTON_SUBMIT).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), description);
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(), String.format("Project %s", FREESTYLE_NAME_WITH_DESCRIPTION));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithDescription")
    public void testEditFreestyleProjectWithDescription() {
        getDriver().findElement(By.xpath("//span[contains(text(),'" + FREESTYLE_NAME_WITH_DESCRIPTION + "')]")).click();

        getDriver().findElement(BY_DESCRIPTION_LINK).click();
        getWait(10).until(ExpectedConditions.presenceOfElementLocated(BY_DESCRIPTION_TEXT_FIELD)).clear();
        getDriver().findElement(BY_DESCRIPTION_TEXT_FIELD).sendKeys(FREESTYLE_DESCRIPTION);
        getDriver().findElement(BY_BUTTON_SAVE).click();

        Assert.assertEquals(getDriver().findElement(BY_DESCRIPTION_TEXT).getText(), FREESTYLE_DESCRIPTION);
    }

    @Test
    public void testCreateFreestyleProjectWithEngineerName() {

        final String expectedResult = "Engineer";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(expectedResult);
        getDriver().findElement(BY_BUTTON_SELECT_FREESTYLE_PROJECT).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        WebElement registeredProject = getDriver().findElement(By.xpath("//h1[@class='job-index-" + "headline page-headline']"));

        final String actualResult = registeredProject.getText().substring(registeredProject.getText().length() - 8);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithDupicateName")
    public void testCreateBuildNowOnFreestyleProjectPage() {
        int countBuildsBeforeCreatingNewBuild = 0;

        getDriver().findElement(By.linkText(NEW_FREESTYLE_NAME)).click();

        if (getDriver().findElement(By.cssSelector(".collapse")).getAttribute("title").equals("expand")) {
            getDriver().findElement(By.cssSelector(".collapse")).click();
        }
        if (!getDriver().findElement(By.id("no-builds")).isDisplayed()) {
            countBuildsBeforeCreatingNewBuild = getDriver().findElements(BY_BUILD_ROW).size();
        }
        getDriver().findElement(BY_SIDE_PANEL_BUILD_NOW).click();
        getWait(20).until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//span[@class='build-status-icon__outer']/*[@tooltip = 'In progress &gt; Console Output']")));
        int countBuildsAfterCreatingNewBuild = getDriver().findElements(BY_BUILD_ROW).size();

        Assert.assertEquals(countBuildsAfterCreatingNewBuild, countBuildsBeforeCreatingNewBuild + 1);
    }

    @Test
    public void testCreateFreestyleProject() {
        String name = getRandomStr(5);
        getDriver().findElement(BY_BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(BY_FIELD_ENTER_NAME).sendKeys(name);
        getDriver().findElement(BY_BUTTON_SELECT_FREESTYLE_PROJECT).click();
        getDriver().findElement(BY_BUTTON_OK).click();
        getDriver().findElement(BY_BUTTON_SUBMIT).click();
        getDriver().findElement(BY_GO_TO_DASHBOARD_LINK).click();

        List<WebElement> lstWithElements = getDriver().findElements(By.xpath("//table[@id='projectstatus']//tbody//tr//td//a"));
        List<String> lstWithStrings = lstWithElements.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertTrue(lstWithStrings.contains(name));
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithDupicateName")
    public void testFreestyleProjectBuild() {
        getDriver().findElement(By.linkText(NEW_FREESTYLE_NAME)).click();

        final int initialBuildCount = getDriver().findElements(BY_BUILD_ROW).size();
        getDriver().findElement(BY_SIDE_PANEL_BUILD_NOW).click();
        final int actualBuildCount = getWait(20).until(ExpectedConditions.numberOfElementsToBeMoreThan(BY_BUILD_ROW, initialBuildCount)).size();

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
    public void testFreestyleConfigSideMenu() {

        final Set<String> expectedFreestyleConfigSideMenu = new TreeSet<>(List.of("General", "Source Code Management", "Build Triggers", "Build Environment", "Build Steps", "Post-build Actions"));

        Set<String> actualFreestyleConfigSideMenu = new HomePage(getDriver())
                .clickFreestyleProjectName()
                .clickSideMenuConfigure()
                .collectFreestyleConfigSideMenu();

        Assert.assertEquals(actualFreestyleConfigSideMenu, expectedFreestyleConfigSideMenu);
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProject")
    public void testCreateFreestyleProjectWithEmptyName() {
        getDriver().findElement(BY_BUTTON_ADD_NEW_ITEM).click();
        getWait(10).until(ExpectedConditions.presenceOfElementLocated(BY_BUTTON_SELECT_FREESTYLE_PROJECT)).click();

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(), "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(getDriver().findElement(BY_BUTTON_OK).isEnabled());
    }

    @Test
    public void testCreateFreestyleProjectWithValidNameAndDescription() {
        getDriver().findElement(BY_BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(BY_FIELD_ENTER_NAME).sendKeys(VALID_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(BY_BUTTON_SELECT_FREESTYLE_PROJECT).click();
        getDriver().findElement(BY_BUTTON_OK).click();
        getDriver().findElement(BY_DESCRIPTION_TEXT_FIELD).sendKeys(DESCRIPTION_INPUT);
        getDriver().findElement(BY_BUTTON_SUBMIT).click();

        Assert.assertEquals(getDriver().findElement(BY_NEW_ITEM_NAME_HEADLINE).getText(), "Project " + VALID_FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(getDriver().findElement(BY_DESCRIPTION_TEXT).getText(), DESCRIPTION_INPUT);
    }

    @Test
    public void testCreateFreestyleProjectWithInvalidCharBeforeName() {
        getDriver().findElement(BY_BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(BY_FIELD_ENTER_NAME).sendKeys(INVALID_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(BY_BUTTON_SELECT_FREESTYLE_PROJECT).click();

        Assert.assertEquals(getWait(20).until(ExpectedConditions.presenceOfElementLocated(BY_ITEM_NAME_INVALID_MESSAGE)).getText(),
                "» ‘" + INVALID_CHAR + "’ is an unsafe character");
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithEmptyName")
    public void testCreateFreestyleProjectWithSpacesInsteadOfName() {
        getDriver().findElement(BY_BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(BY_FIELD_ENTER_NAME).sendKeys(SPACE_INSTEAD_OF_NAME);
        getDriver().findElement(BY_BUTTON_SELECT_FREESTYLE_PROJECT).click();
        getDriver().findElement(BY_BUTTON_OK).click();

        Assert.assertEquals(getDriver().findElement(BY_NEW_ITEM_NAME_HEADLINE).getText(), "Error");
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#main-panel > p")).getText(), "No name is specified");
    }

    @Test
    public void testAccessProjectConfigurationFromTheProjectPage() {
            final String NAME_FREESTYLE_PROJECT_TC010401 = TestUtils.getRandomStr(5) + "TC010401";

            FreestyleProjectConfigPage FreeStylePoject010401 = new HomePage(getDriver())
                    .clickNewItem()
                    .setProjectName(NAME_FREESTYLE_PROJECT_TC010401)
                    .selectFreestyleProjectAndClickOk()
                    .goDashboard()
                    .clickFreestyleProjectName(NAME_FREESTYLE_PROJECT_TC010401)
                    .clickSideMenuConfigureLink();

            Assert.assertTrue(FreeStylePoject010401.getHeadTextFreeStyleConfigPage().contains("Configuration"));
            Assert.assertTrue(FreeStylePoject010401.getJenkinsCurrentVersion().contains("Jenkins 2.361.4"));

            new FreestyleProjectConfigPage(getDriver())
                    .goDashboard()
                    .clickFreestyleProjectName(NAME_FREESTYLE_PROJECT_TC010401)
                    .clickButtonDeleteProject()
                    .confirmAlertAndDeleteProject();
    }

    @Ignore
    @Test
    public void testAddingDescription() {
        getDriver().findElement(BY_BUTTON_ADD_NEW_ITEM).click();
        getDriver().findElement(BY_FIELD_ENTER_NAME).sendKeys("New Project");
        getDriver().findElement(BY_BUTTON_SELECT_FREESTYLE_PROJECT).click();
        getDriver().findElement(BY_BUTTON_OK).click();
        getDriver().findElement(BY_DESCRIPTION_TEXT_FIELD).sendKeys("SSS");
        getDriver().findElement(BY_BUTTON_SUBMIT).click();

        Assert.assertEquals(getDriver().findElement(BY_DESCRIPTION_TEXT).getText(), "SSS");
    }

    @Test
    public void testCreateNewFreestyleProjectWithLongNameFrom256Characters() {
        final String expectedURL = getDriver().getCurrentUrl()+"view/all/createItem";
        final String errorPictureName = "rage.svg";
        final String expectedTextOfError = "A problem occurred while processing the request.";
        final String longNameWith256Characters = getRandomStr(256);

        getDriver().findElement(BY_BUTTON_ADD_NEW_ITEM).click();
        getWait(5).until(ExpectedConditions.elementToBeClickable(BY_FIELD_ENTER_NAME))
                .sendKeys(longNameWith256Characters);
        getWait(5).until(ExpectedConditions.attributeContains(BY_FIELD_ENTER_NAME,
                "value", longNameWith256Characters));
        getDriver().findElement(BY_BUTTON_SELECT_FREESTYLE_PROJECT).click();
        scrollToElement(getDriver(), getDriver().findElement(BY_BUTTON_OK));
        getWait(5).until(ExpectedConditions.elementToBeClickable(BY_BUTTON_OK)).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), expectedURL);
        Assert.assertTrue(getDriver().findElement(
                By.xpath("//img[contains(@src,'"+errorPictureName+"')]")).isDisplayed());
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='error-description']//h2")).getText(),
                expectedTextOfError);
    }
}
