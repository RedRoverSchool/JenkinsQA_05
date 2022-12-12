import model.HomePage;
import model.PipelineConfigPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.TestUtils;


public class Pipeline2Test extends BaseTest {

    private static final By OK_BUTTON = By.id("ok-button");
    private static final By SAVE_BUTTON = By.id("yui-gen6-button");
    private static final By GITHUB_CHECKBOX  = By.xpath("//label[text()='GitHub project']");
    private static final String RANDOM_STRING  = TestUtils.getRandomStr(7);
    private static final String ITEM_DESCRIPTION = "This is a sample description for item";

    private void createPipeline() {
        ProjectUtils.createNewItemFromDashboard(getDriver(),By.xpath("//span[text()='Pipeline']"), RANDOM_STRING);
    }

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
                .getNameErrorMessageText();

        Assert.assertEquals(newItemPageErrorMessage, String.format("» A job already exists with the name ‘%s’", projectName));
    }

    @Test
    public void testCreatePipelineOnBreadcrumbs () {
        createPipeline();

        Assert.assertTrue(getDriver().findElement(By.className("jenkins-breadcrumbs")).getAttribute("textContent").contains(RANDOM_STRING));
    }

    @Test
    public void testCreateNewPipeline() {
        createPipeline();
        new Actions(getDriver()).moveToElement(getDriver().findElement(SAVE_BUTTON)).click().perform();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                String.format("Pipeline %s", RANDOM_STRING));
    }

    @Test
    public void testCreatePipelineWithName() {
        createPipeline();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", RANDOM_STRING))).getText(),
                RANDOM_STRING);
    }

    @Test(dependsOnMethods = "testCreateNewPipeline")
    public void testAddingGitRepository() {
        final String gitHubRepo = "https://github.com/patriotby07/simple-maven-project-with-tests";

        PipelineConfigPage pipelineConfigPage = new HomePage(getDriver())
                .clickJobDropDownMenu(RANDOM_STRING)
                .clickConfigureDropDownMenu()
                .clickGitHubCheckbox()
                .setGitHubRepo(gitHubRepo)
                .saveConfigAndGoToProject();

        Assert.assertTrue(pipelineConfigPage.isDisplayedGitHubOnSideMenu());
        Assert.assertTrue(pipelineConfigPage.getAttributeGitHubSideMenu("href").contains(gitHubRepo));
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

    @Ignore
    @Test(dependsOnMethods = "testWarningMessageIsDisappeared")
    public void testBuildParametrizedProject() {
        getDriver().findElement((By.xpath(String.format(
                "//tr[@id='job_%s']//button[@class='jenkins-menu-dropdown-chevron']", RANDOM_STRING)))).click();
        getDriver().findElement(By.linkText("Configure")).click();

        getDriver().findElement(By.xpath("//label[text()='This project is parameterized']")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
        getDriver().findElement(By.id("yui-gen9")).click();
        TestUtils.scrollToElement(getDriver(), getDriver().findElement(GITHUB_CHECKBOX));
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(GITHUB_CHECKBOX));
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.name("parameter.name")))
                .click()
                .sendKeys("Select User")
                .moveToElement(getDriver().findElement(By.name("parameter.choices")))
                .click()
                .sendKeys("Admin" + Keys.ENTER, "Guest" + Keys.ENTER, "User" + Keys.ENTER)
                .perform();

        TestUtils.scrollToEnd(getDriver());
        new Select(getDriver().findElement(By.xpath("(//select[contains(@class,'jenkins-select__input dropdownList')])[2]")))
                .selectByVisibleText("Pipeline script from SCM");
        new Select(getDriver().findElement(By.xpath("(//select[contains(@class,'jenkins-select__input dropdownList')])[3]")))
                .selectByVisibleText("Git");
        getDriver().findElement(By.name("_.url")).sendKeys("https://github.com/patriotby07/simple-maven-project-with-tests");
        getDriver().findElement(SAVE_BUTTON).click();

        getDriver().findElement(By.linkText("Build with Parameters")).click();
        new Select(getDriver().findElement(By.xpath("//select[@name='value']"))).selectByVisibleText("Guest");
        getDriver().findElement(By.id("yui-gen1-button")).click();
        getWait(60).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@class='job SUCCESS']")));
        getDriver().navigate().refresh();
        getDriver().findElement(By.xpath("//a[@href='lastBuild/']")).click();
        getDriver().findElement(By.linkText("Console Output")).click();

        Assert.assertTrue(getDriver().findElement(By.className("console-output")).getText().contains("BUILD SUCCESS"));
        Assert.assertTrue(getDriver().findElement(By.className("console-output")).getText().contains("Finished: SUCCESS"));
    }

    @Test
    public void testCreateNewPipelineWithDescription() {
        createPipeline();
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(ITEM_DESCRIPTION);
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description >*:first-child")).getAttribute("textContent"),
                ITEM_DESCRIPTION);
    }

    @Ignore
    @Test (dependsOnMethods = "testCreateNewPipelineWithDescription")
    public void testCreateNewPipelineFromExisting() {
        final String jobName = TestUtils.getRandomStr(7);

        getDriver().findElement(By.linkText("New Item")).click();
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.id("name"))).sendKeys(jobName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        TestUtils.scrollToEnd(getDriver());
        new Actions(getDriver()).pause(300).moveToElement(getDriver().findElement(By.cssSelector("#from")))
                .click().sendKeys(RANDOM_STRING.substring(0,2)).pause(400)
                .sendKeys(Keys.ARROW_DOWN)
                .sendKeys(Keys.ENTER).perform();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".job-index-headline.page-headline")).getAttribute("textContent").substring(9),
                jobName);
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description >*:first-child")).getAttribute("textContent"),
                ITEM_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreateNewPipelineWithDescription")
    public void testEditPipelineDescription()  {
        final String newDescription = "new description";

        String actualDescription = new HomePage(getDriver())
                .clickJobDropDownMenu(RANDOM_STRING)
                .clickPipelineProjectName()
                .editDescription(newDescription)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualDescription, newDescription);
    }
}
