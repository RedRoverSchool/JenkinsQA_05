import model.HomePage;
import model.MyViewsPage;
import model.PipelineConfigPage;
import model.PipelineProjectPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.TestUtils;

import java.util.List;

public class PipelineTest extends BaseTest {
    private static final String RENAME_SUFFIX = "renamed";
    private static final String PIPELINE_NAME = generatePipelineProjectName();
    private static final String pipeline_name = TestUtils.getRandomStr();
    private static final String VIEW_NAME = RandomStringUtils.randomAlphanumeric(5);
    private static final String RANDOM_STRING  = TestUtils.getRandomStr(7);
    private static final String ITEM_DESCRIPTION = "This is a sample description for item";
    private static final String ITEM_NEW_DESCRIPTION = "New description";

    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By ITEM_NAME = By.id("name");
    private static final By PIPELINE = By.xpath("//span[text() = 'Pipeline']");
    private static final By BUTTON_OK = By.id("ok-button");
    private static final By BUTTON_SAVE = By.id("yui-gen6-button");
    private static final By BUTTON_DISABLE_PROJECT = By.id("yui-gen1-button");
    private static final By BUTTON_DELETE = By.cssSelector("svg.icon-edit-delete");
    private static final By DASHBOARD = By.xpath("//a[text()='Dashboard']");
    private static final By JOB_PIPELINE = By.xpath(String.format("//span[text()[contains(.,'%s')]]", PIPELINE_NAME));
    private static final By JOB_PIPELINE_MENU_DROPDOWN_CHEVRON = By.xpath(String.format("//span[text()[contains(.,'%s')]]/../button", PIPELINE_NAME));
    private static final By JOB_MENU_RENAME = By.xpath("//div[@id='breadcrumb-menu']//span[contains(text(),'Rename')]");
    private static final By TEXTFIELD_NEW_NAME = By.name("newName");
    private static final By BUTTON_RENAME = By.id("yui-gen1-button");
    private static final By MY_VIEWS = By.xpath("//a[@href='/me/my-views']");
    private static final By ADD_TAB = By.className("addTab");
    private static final By VIEW_NAME_FIELD = By.id("name");
    private static final By RADIO_BUTTON_MY_VIEW = By.xpath("//input[@id='hudson.model.MyView']/..//label[@class='jenkins-radio__label']");
    private static final By BUTTON_CREATE = By.id("ok");
    private static final By VIEW = By.xpath(String.format("//div/a[contains(text(),'%s')]", VIEW_NAME));
    private static final By TEXTAREA_DESCRIPTION = By.xpath("//textarea[@name='description']");

    private static String generatePipelineProjectName() {

        return RandomStringUtils.randomAlphanumeric(10);
    }

    private void createPipelineProject(String projectName) {
        Actions actions = new Actions(getDriver());
        actions.sendKeys(Keys.F5);
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(NEW_ITEM).click();
        getWait(3).until(ExpectedConditions.elementToBeClickable(PIPELINE)).click();
        getDriver().findElement(ITEM_NAME).sendKeys(projectName);
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(BUTTON_SAVE).click();
        getDriver().findElement(DASHBOARD).click();
    }

    private void deletePipelineProject(String name) {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath(String.format("//a[@href = contains(., '%s')]/button", name))).click();
        getDriver().findElement(BUTTON_DELETE).click();
        getDriver().switchTo().alert().accept();
    }

    private void renamePipelineProject(String name, String rename) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(JOB_PIPELINE))
                .moveToElement(getDriver().findElement(JOB_PIPELINE_MENU_DROPDOWN_CHEVRON)).click().build().perform();
        getDriver().findElement(JOB_MENU_RENAME).click();
        getDriver().findElement(TEXTFIELD_NEW_NAME).clear();
        getDriver().findElement(TEXTFIELD_NEW_NAME).sendKeys(name + rename);
        getDriver().findElement(BUTTON_RENAME).click();
    }

    private void createNewViewOfTypeMyView() {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(ADD_TAB).click();
        getDriver().findElement(VIEW_NAME_FIELD).sendKeys(VIEW_NAME);
        getDriver().findElement(RADIO_BUTTON_MY_VIEW).click();
        getDriver().findElement(BUTTON_CREATE).click();
    }

    private void deleteNewView() {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(VIEW).click();
        getDriver().findElement(BUTTON_DELETE).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
    }

    private PipelineConfigPage createPipelineProjectCuttedVersion(String projectName) {
        return new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(pipeline_name)
                .selectPipelineAndClickOk();
    }

    @Ignore
    @Test
    public void testDisablePipelineProjectMessage() {

        String pipelinePojectName = generatePipelineProjectName();
        createPipelineProject(pipelinePojectName);
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath(String.format("//td/a/span[contains(text(),'%s')]", pipelinePojectName))).click();
        getDriver().findElement(BUTTON_DISABLE_PROJECT).click();

        Assert.assertTrue(getDriver().findElement(By.id("enable-project")).getText()
                .contains("This project is currently disabled"));
    }

    @Test
    public void testCreatedPipelineDisplayedOnMyViews() {

        final String pipelineName = TestUtils.getRandomStr(5);

        MyViewsPage pipelineNameInMyViewList = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(pipelineName)
                .selectPipelineAndClickOk()
                .clickDashboard()
                .clickMyViews();

        Assert.assertTrue(pipelineNameInMyViewList.getListProjectsNames().contains(pipelineName));
    }

    @Test
    public void testPipelineAddDescription() {

        PipelineProjectPage pipelineProjectPage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(pipeline_name)
                .selectPipelineAndClickOk()
                .saveConfigAndGoToProjectPage()
                .editDescription(pipeline_name + "description")
                .clickSaveButton();

        Assert.assertEquals(pipelineProjectPage.getDescription(), pipeline_name + "description");
    }

    @Ignore
    @Test
    public void testNewPipelineItemAppearedInTheList() {

        String pipelineProjectName = generatePipelineProjectName();
        createPipelineProject(pipelineProjectName);

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//a[@href='job/" + pipelineProjectName + "/']")).getText(), pipelineProjectName);
    }

    @Ignore
    @Test
    public void testRenamePipelineWithValidName() {
        createPipelineProject(PIPELINE_NAME);
        renamePipelineProject(PIPELINE_NAME, RENAME_SUFFIX);

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//h1[@class='job-index-headline page-headline']"))
                .getText(), "Pipeline " + PIPELINE_NAME + RENAME_SUFFIX);

        deletePipelineProject(PIPELINE_NAME);
    }

    @Ignore
    @Test
    public void testRenamedPipelineIsDisplayedInMyViews() {
        createPipelineProject(PIPELINE_NAME);
        createNewViewOfTypeMyView();
        renamePipelineProject(PIPELINE_NAME, RENAME_SUFFIX);
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(VIEW).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath(String.format("//tbody//a[@href = contains(., '%s%s')]", PIPELINE_NAME, RENAME_SUFFIX)))
                .getText(), PIPELINE_NAME + RENAME_SUFFIX);

        deleteNewView();
        deletePipelineProject(PIPELINE_NAME);
    }

    @Ignore
    @Test
    public void testRenamePipelineWithoutChangingName() {
        createPipelineProject(PIPELINE_NAME);
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(JOB_PIPELINE))
                .moveToElement(getDriver().findElement(JOB_PIPELINE_MENU_DROPDOWN_CHEVRON)).click().build().perform();
        getDriver().findElement(JOB_MENU_RENAME).click();
        getDriver().findElement(BUTTON_RENAME).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//div[@id='main-panel']//h1[contains(text(),'Error')]"))
                .getText(), "Error");
        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//div[@id='main-panel']//p"))
                .getText(), "The new name is the same as the current name.");

        deletePipelineProject(PIPELINE_NAME);
    }

    @Ignore
    @Test
    public void testRenamePipelineUsingSpecialCharacter() {
        String specialCharactersString = "!@#$%*/:;?[]^|";
        for (int i = 0; i < specialCharactersString.length(); i++) {
            createPipelineProject(PIPELINE_NAME);
            Actions actions = new Actions(getDriver());
            actions.moveToElement(getDriver().findElement(JOB_PIPELINE))
                    .moveToElement(getDriver().findElement(JOB_PIPELINE_MENU_DROPDOWN_CHEVRON)).click().build().perform();
            getDriver().findElement(JOB_MENU_RENAME).click();
            getDriver().findElement(TEXTFIELD_NEW_NAME).clear();
            getDriver().findElement(TEXTFIELD_NEW_NAME).sendKeys(PIPELINE_NAME + specialCharactersString.charAt(i));
            getDriver().findElement(BUTTON_RENAME).click();

            Assert.assertEquals(getDriver()
                    .findElement(By.xpath("//div[@id='main-panel']//h1[contains(text(),'Error')]"))
                    .getText(), "Error");
            Assert.assertEquals(getDriver()
                    .findElement(By.xpath("//div[@id='main-panel']//p"))
                    .getText(), String.format("‘%s’ is an unsafe character", specialCharactersString.charAt(i)));

            deletePipelineProject(PIPELINE_NAME);
        }
    }

    @Test
    public void testPipelinePreviewDescription() {

        PipelineConfigPage pipelineConfigPage = createPipelineProjectCuttedVersion(pipeline_name)
                .setDescriptionField(pipeline_name + "description")
                .clickPreviewLink();

        Assert.assertEquals(pipelineConfigPage.getTextareaPreview(), pipeline_name + "description");
    }

    @Ignore
    @Test
    public void testPipelineHidePreviewDescription() {

        String pipelinePojectName = TestUtils.getRandomStr();
        createPipelineProjectCuttedVersion(pipelinePojectName);

        getDriver().findElement(TEXTAREA_DESCRIPTION).sendKeys(pipelinePojectName + "description");
        getDriver().findElement(By.className("textarea-show-preview")).click();

        getDriver().findElement(By.className("textarea-hide-preview")).click();

        Assert.assertFalse(getDriver().findElement(By.className("textarea-preview")).isDisplayed());

        getDriver().findElement(BUTTON_SAVE).click();
    }

    @Ignore
    @Test
    public void testPipelineAEditDescription() {

        String pipelinePojectName = TestUtils.getRandomStr();
        createPipelineProjectCuttedVersion(pipelinePojectName);
        getDriver().findElement(TEXTAREA_DESCRIPTION).sendKeys(pipelinePojectName + "description");
        getDriver().findElement(BUTTON_SAVE).click();

        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath(String.format("//a[@href = contains(., '%s')]/button", pipelinePojectName))).click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(TEXTAREA_DESCRIPTION).clear();
        getDriver().findElement(TEXTAREA_DESCRIPTION).sendKeys(pipelinePojectName + "edit description");
        getDriver().findElement(By.cssSelector("button[type='Submit']")).click();

        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath(String.format("//a[@href = contains(., '%s')]/button", pipelinePojectName))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), pipelinePojectName + "edit description");
    }

    @Test(dependsOnMethods = "testEnablePipelineProject")
    public void testDeletePipelineFromDashboard() {
        String homePageHeaderText = new HomePage(getDriver())
                .clickDashboard()
                .clickPipelineProjectName()
                .clickDeletePipelineButton()
                .getHeaderText();

        Assert.assertEquals(homePageHeaderText, "Welcome to Jenkins!");
    }
    
    @Ignore
    @Test
    public void testCreatePipelineExistingNameError() {
        final String projectName = "AnyUnusualName1";

        String newItemPageErrorMessage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(projectName)
                .selectPipelineAndClickOk()
                .clickDashboard()
                .clickNewItem()
                .setProjectName(projectName)
                .getItemNameInvalidMsg();

        Assert.assertEquals(newItemPageErrorMessage, String.format("» A job already exists with the name ‘%s’", projectName));
    }

    @Test
    public void testCreatePipelineOnBreadcrumbs () {
        ProjectUtils.createNewItemFromDashboard(getDriver(),By.xpath("//span[text()='Pipeline']"), RANDOM_STRING);

        Assert.assertTrue(getDriver().findElement(By.className("jenkins-breadcrumbs")).getAttribute("textContent").contains(RANDOM_STRING));
    }

    @Test
    public void testCreateNewPipeline() {
        ProjectUtils.createNewItemFromDashboard(getDriver(),By.xpath("//span[text()='Pipeline']"), RANDOM_STRING);
        new Actions(getDriver()).moveToElement(getDriver().findElement(BUTTON_SAVE)).click().perform();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                String.format("Pipeline %s", RANDOM_STRING));
    }

    @Test
    public void testCreatePipelineWithName() {
        ProjectUtils.createNewItemFromDashboard(getDriver(),By.xpath("//span[text()='Pipeline']"), RANDOM_STRING);
        getDriver().findElement(BUTTON_SAVE).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", RANDOM_STRING))).getText(),
                RANDOM_STRING);
    }

    @Test(dependsOnMethods = "testCreateNewPipeline")
    public void testAddingGitRepository() {
        final String gitHubRepo = "https://github.com/patriotby07/simple-maven-project-with-tests";

        PipelineProjectPage pipelineProjectPage = new HomePage(getDriver())
                .clickJobDropDownMenu(RANDOM_STRING)
                .clickConfigureDropDownMenu()
                .clickGitHubCheckbox()
                .setGitHubRepo(gitHubRepo)
                .saveConfigAndGoToProjectPage();

        Assert.assertTrue(pipelineProjectPage.isDisplayedGitHubOnSideMenu());
        Assert.assertTrue(pipelineProjectPage.getAttributeGitHubSideMenu("href").contains(gitHubRepo));
    }

    @Test(dependsOnMethods = "testAddingGitRepository")
    public void testWarningMessageIsDisappeared() {

        String emptyErrorArea = new HomePage(getDriver())
                .clickMenuManageJenkins()
                .clickConfigureTools()
                .clickAddMavenButton()
                .setMavenTitleField("Maven")
                .clickApplyButton()
                .getErrorAreaText();

        Assert.assertEquals(emptyErrorArea, "");
    }

    @Test(dependsOnMethods = "testWarningMessageIsDisappeared")
    public void testBuildParametrizedProject() {

        String consoleOutputText = new HomePage(getDriver())
                .clickJobDropDownMenu(RANDOM_STRING)
                .clickConfigureDropDownMenu()
                .clickParameterizationCheckbox()
                .clickAddParameter()
                .clickChoiceParameter()
                .setChoiceParameter("Select User", "Admin", "Guest", "User")
                .selectPipelineScriptFromScm()
                .selectScriptScm()
                .setGitHubUrl("https://github.com/patriotby07/simple-maven-project-with-tests")
                .saveConfigAndGoToProjectPage()
                .clickBuildWithParameters()
                .selectParametersBuild()
                .clickBuildButton()
                .clickLastBuildLink()
                .clickConsoleOutput()
                .getConsoleOutputText();

        Assert.assertTrue(consoleOutputText.contains("BUILD SUCCESS"));
        Assert.assertTrue(consoleOutputText.contains("Finished: SUCCESS"));
    }

    @Test
    public void testCreateNewPipelineWithDescription() {
        ProjectUtils.createNewItemFromDashboard(getDriver(),By.xpath("//span[text()='Pipeline']"), RANDOM_STRING);
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(ITEM_DESCRIPTION);
        getDriver().findElement(BUTTON_SAVE).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description >*:first-child")).getAttribute("textContent"),
                ITEM_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreateNewPipelineWithDescription")
    public void testEditPipelineDescription()  {

        String actualDescription = new HomePage(getDriver())
                .clickJobDropDownMenu(RANDOM_STRING)
                .clickPipelineProjectName()
                .editDescription(ITEM_NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualDescription, ITEM_NEW_DESCRIPTION);
    }

    @Test (dependsOnMethods = "testEditPipelineDescription")
    public void testCreateNewPipelineFromExisting() {
        final String jobName =TestUtils.getRandomStr(7);

        String actualJobName = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(jobName)
                .setCopyFromItemName(RANDOM_STRING)
                .clickOk()
                .saveConfigAndGoToProjectPage()
                .getPipelineName();

        String actualDescription = new PipelineProjectPage(getDriver()).getDescription();

        Assert.assertEquals(actualJobName,jobName);
        Assert.assertEquals(actualDescription,ITEM_NEW_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreatePipelineWithName")
    public void testEnablePipelineProject() {
        String jobStatusAfterEnable = new HomePage(getDriver())
                .clickDashboard()
                .clickPipelineProjectName()
                .clickDisableProject()
                .clickEnableProject()
                .clickDashboard()
                .getJobBuildStatus();

        Assert.assertNotEquals(jobStatusAfterEnable, "Disabled");
    }

    @Test(dependsOnMethods = "testCreateNewPipelineFromExisting")
    public void testPipelineSideMenuLinks() {
        List<String> pipelineSideMenuOptionsLinks = new HomePage(getDriver())
                .clickPipelineProjectName()
                .getPipelineSideMenuLinks();

        Assert.assertEquals(pipelineSideMenuOptionsLinks,
                List.of("Status", "Changes", "Build Now", "Configure", "Delete Pipeline", "Full Stage View", "Rename", "Pipeline Syntax"));
    }
}
