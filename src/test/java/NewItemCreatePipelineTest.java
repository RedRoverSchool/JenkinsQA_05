import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
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

    private static final String RANDOM_STRING  = TestUtils.getRandomStr(7);

    private static String getRandomStr() {
        return RandomStringUtils.random(7, true,true);
    }

    private void createPipeline(String jobName) {
        setJobPipeline(jobName);
        getDriver().findElement(OK_BUTTON).click();
    }

    private void setJobPipeline(String jobName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(jobName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
    }

    private void scrollPageDown() {
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    @Test
    public void testCreatePipelineExistingNameError() {
        final String jobName = getRandomStr();

        createPipeline(jobName);
        getDriver().findElement(LINK_TO_DASHBOARD).click();
        setJobPipeline(jobName);

        final WebElement notificationError = getDriver().findElement(By.id("itemname-invalid"));

        Assert.assertEquals(notificationError.getText(), String.format("» A job already exists with the name ‘%s’", jobName));
    }

    @DataProvider(name = "new-item-unsafe-names")
    public Object[][] dpMethod() {
        return new Object[][]{{"!Pipeline1"}, {"pipel@ne2"}, {"PipeLine3#"},
                {"PIPL$N@4"}, {"5%^PiPl$^Ne)"}};
    }

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
        final String itemName = getRandomStr();

        createPipeline(itemName);

        Assert.assertTrue(getDriver().findElement(By.className("jenkins-breadcrumbs"))
                .getAttribute("textContent").contains(itemName));
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
        final String name = getRandomStr();

        createPipeline(name);
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(LINK_TO_DASHBOARD).click();

        Assert.assertEquals(getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", name))).getText(), name);
    }

    @Test
    public void testDeletePipelineFromDashboard() {
        final String jobName = getRandomStr();

        createPipeline(jobName);
        getDriver().findElement(LINK_TO_DASHBOARD).click();
        getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", jobName))).click();
        getDriver().findElement(By.xpath("//span[text()='Delete Pipeline']")).click();
        getDriver().switchTo().alert().accept();

        List<WebElement> allJobsInDashboard = getDriver().findElements(By.xpath(
                "//a[@class='jenkins-table__link model-link inside']"));
        for (WebElement element : allJobsInDashboard) {
            if (element.getText().contains(jobName)) {
                Assert.fail();
                break;
            } else {
                Assert.assertTrue(true);
            }
        }
    }

    @Test(dependsOnMethods = "testCreateNewPipeline")
    public void testAddingGitRepository() {
        final String gitHubRepo = "https://github.com/patriotby07/simple-maven-project-with-tests";

        getDriver().findElement((By.xpath(String.format(
                "//tr[@id='job_%s']//button[@class='jenkins-menu-dropdown-chevron']", RANDOM_STRING)))).click();
        getDriver().findElement(By.linkText("Configure")).click();
        getDriver().findElement(By.xpath("//label[text()='GitHub project']")).click();
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.name("_.projectUrlStr"))).click()
                .sendKeys(gitHubRepo).perform();
        getDriver().findElement(SAVE_BUTTON).click();

        final WebElement sideMenuGitHub = getDriver().findElement(By.xpath("(//a[contains(@class,'task-link')])[7]"));

        Assert.assertTrue(sideMenuGitHub.isDisplayed());
        Assert.assertTrue(sideMenuGitHub.getAttribute("href").contains(gitHubRepo));
    }

    @Test(dependsOnMethods = "testAddingGitRepository")
    public void testCreateNewItemFromOtherNonExistingName() {
        final String jobName = getRandomStr();

        setJobPipeline(jobName);
        scrollPageDown();
        new Actions(getDriver()).pause(1500).moveToElement(getDriver().findElement(By.id("from"))).click()
                .sendKeys(jobName).perform();
        getDriver().findElement(OK_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/p")).getText(),
                "No such job: " + jobName);
    }
}
