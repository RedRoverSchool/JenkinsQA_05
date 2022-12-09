import model.HomePage;
import model.PipelineConfigPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewItemCreatePipelineTest extends BaseTest {

    private static final By OK_BUTTON = By.id("ok-button");
    private static final By SAVE_BUTTON = By.id("yui-gen6-button");
    private static final By LINK_TO_DASHBOARD  = By.id("jenkins-name-icon");
    private static final By GITHUB_CHECKBOX  = By.xpath("//label[text()='GitHub project']");
    private static final By CONFIGURE_BUTTON  = By.linkText("Configure");

    private static final String RANDOM_STRING  = TestUtils.getRandomStr(7);
    private static final String ITEM_DESCRIPTION = "This is a sample " +
            "description for item";

    private void createPipeline(String jobName) {
        setJobPipeline(jobName);
        getDriver().findElement(OK_BUTTON).click();
    }

    private void setJobPipeline(String jobName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.id("name"))).sendKeys(jobName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
    }

    @Test
    public void testCreatePipelineExistingNameError() {
        createPipeline(RANDOM_STRING);
        getDriver().findElement(LINK_TO_DASHBOARD).click();
        setJobPipeline(RANDOM_STRING);

        WebElement notificationError = getDriver().findElement(By.id("itemname-invalid"));

        Assert.assertEquals(notificationError.getText(), String.format("» A job already exists with the name ‘%s’", RANDOM_STRING));
    }

    @DataProvider(name = "new-item-unsafe-names")
    public Object[][] dpMethod() {
        return new Object[][]{{"!Pipeline1"}, {"pipel@ne2"}, {"PipeLine3#"},
                {"PIPL$N@4"}, {"5%^PiPl$^Ne)"}};
    }
    @Ignore
    @Test(dataProvider = "new-item-unsafe-names")
    public void testCreateNewItemWithUnsafeCharactersName(String name) {
        Matcher matcher = Pattern.compile("[!@#$%^&*|:?></.']").matcher(name);
        matcher.find();

        setJobPipeline(name);

        Assert.assertEquals(getDriver().findElement(By.cssSelector("div#itemname-invalid")).getAttribute("textContent"),
                String.format("» ‘%s’ is an unsafe character", name.charAt(matcher.start())));
    }

    @Test
    public void testCreatePipelineWithoutName() {
        setJobPipeline("");

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateNewItemWithoutChooseAnyFolder(){
        setJobPipeline("");

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreatePipelineOnBreadcrumbs () {
        createPipeline(RANDOM_STRING);

        Assert.assertTrue(getDriver().findElement(By.className("jenkins-breadcrumbs"))
                .getAttribute("textContent").contains(RANDOM_STRING));
    }

    @Test
    public void testCreateNewPipeline() {
        createPipeline(RANDOM_STRING);
        new Actions(getDriver()).moveToElement(getDriver().findElement(SAVE_BUTTON)).click().perform();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                String.format("Pipeline %s", RANDOM_STRING));
    }

    @Test
    public void testCreatePipelineWithName() {
        createPipeline(RANDOM_STRING);
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(LINK_TO_DASHBOARD).click();

        Assert.assertEquals(getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", RANDOM_STRING))).getText(),
                RANDOM_STRING);
    }


    @Test(dependsOnMethods = "testAddingGitRepository")
    public void testDeletePipelineFromDashboard() {
        getDriver().findElement(LINK_TO_DASHBOARD).click();
        getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", RANDOM_STRING))).click();
        getDriver().findElement(By.xpath("//span[text()='Delete Pipeline']")).click();
        getDriver().switchTo().alert().accept();

        Assert.assertNotNull(getDriver().findElement(By.className("empty-state-block")));
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
        getDriver().findElement(CONFIGURE_BUTTON).click();

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

    @Ignore
    @Test(dependsOnMethods = "testWarningMessageIsDisappeared")
    public void testCreateNewItemFromOtherNonExistingName() {
        final String jobName = TestUtils.getRandomStr(7);

        setJobPipeline(jobName);
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.id("from"))).click()
                .sendKeys(jobName).perform();
        getDriver().findElement(OK_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/p")).getText(),
                "No such job: " + jobName);
    }

    @Test
    public void testCreateNewPipelineWithDescription() {
        setJobPipeline(RANDOM_STRING);
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(ITEM_DESCRIPTION);
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description >*:first-child")).getAttribute("textContent"),
                ITEM_DESCRIPTION);
    }

    @Test (dependsOnMethods = "testCreateNewPipelineWithDescription")
    public void testCreateNewPipelineFromExisting() {
        final String jobName = TestUtils.getRandomStr(7);

        setJobPipeline(jobName);
        TestUtils.scrollToEnd(getDriver());
        new Actions(getDriver()).pause(300).moveToElement(getDriver().findElement(By.cssSelector("#from")))
                .click().sendKeys(RANDOM_STRING.substring(0,2)).pause(400)
                .sendKeys(Keys.ARROW_DOWN)
                .sendKeys(Keys.ENTER).perform();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".job-index-headline.page-headline"))
                .getAttribute("textContent").substring(9),jobName);
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description >*:first-child"))
                .getAttribute("textContent"),ITEM_DESCRIPTION);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreatePipelineWithName")
    public void testPipelineStepFromSCMConfiguration() {

        getDriver().findElements(By.xpath("//tr/td/a")).get(0).click();
        getDriver().findElement(CONFIGURE_BUTTON).click();

        List<WebElement> selectDropDownList = getDriver().findElements(By.className("dropdownList"));
        new Select(selectDropDownList.get(1)).selectByVisibleText("Pipeline script from SCM");

        new Select(getDriver().findElement(By.cssSelector(".dropdownList-container.tr .dropdownList"))).selectByValue("1");
        getDriver().findElement(By.xpath("//input[@checkdependson='credentialsId']")).sendKeys("https://github.com/olpolezhaeva/MyAppium");

        WebElement branchField = getDriver().findElement(By.xpath("//div[@name='branches']//input[@default='*/master']"));
        branchField.clear();
        branchField.sendKeys("*/main");

        WebElement jenkinsFilePathField = getDriver().findElement(By.xpath("//input[@default='Jenkinsfile']"));
        jenkinsFilePathField.clear();
        jenkinsFilePathField.sendKeys("jenkins/first.jenkins");

        getDriver().findElement(SAVE_BUTTON).click();

        getDriver().findElement(By.linkText("Build Now")).click();
        getWait(20).until(ExpectedConditions.presenceOfElementLocated(By.className("build-icon"))).click();

        String stepEchoTotalText = getDriver().findElement(By.xpath("//span[@class='pipeline-node-17']")).getText();

        Assert.assertEquals(
                stepEchoTotalText.replace(getDriver().findElement(By.xpath("//span[@class='timestamp']")).getText(), "").trim(),
                "This is MyPipelineJob!");
    }

    @Test(dependsOnMethods = "testCreateNewPipelineWithDescription")
    public void testEditPipelineDescription()  {
        final String newDescription = "new description";

        String actualDescription = new HomePage(getDriver())
                .clickJobDropDownMenu(RANDOM_STRING)
                .clickPipelineProjectName()
                .editDescription(newDescription)
                .clickSaveButton()
                .getDescription("textContent");

        Assert.assertEquals(actualDescription, newDescription);
    }
}
