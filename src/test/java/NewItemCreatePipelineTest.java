import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
    private static final By ADD_MAVEN_BUTTON  = By.id("yui-gen9-button");

    private static final String RANDOM_STRING  = TestUtils.getRandomStr(7);
    private static final String ITEM_DESCRIPTION = "This is a sample " +
            "description for item";
    
    public static ExpectedCondition<WebElement> steadinessOfElementLocated(final By locator) {
        return new ExpectedCondition<>() {
            private WebElement element = null;
            private Point location = null;

            @Override
            public WebElement apply(WebDriver driver) {
                try {
                    element = driver.findElement(locator);
                } catch (NoSuchElementException e) {
                    return null;
                }

                if (element.isDisplayed()) {
                    Point location = element.getLocation();
                    if (location.equals(this.location)) {
                        return element;
                    }
                    this.location = location;
                }

                return null;
            }
        };
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
        final String jobName = TestUtils.getRandomStr(7);

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
        final String itemName = TestUtils.getRandomStr(7);

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
        final String name = TestUtils.getRandomStr(7);

        createPipeline(name);
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(LINK_TO_DASHBOARD).click();

        Assert.assertEquals(getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", name))).getText(), name);
    }

    @Test
    public void testDeletePipelineFromDashboard() {
        final String jobName = TestUtils.getRandomStr(7);

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
    public void testCheckingDisappearanceOfWarningMessage() {
        getDriver().findElement(By.linkText("Manage Jenkins")).click();
        getDriver().findElement(By.xpath("//a[@href='configureTools']")).click();
        scrollPageDown();

        getWait(5).until(steadinessOfElementLocated(ADD_MAVEN_BUTTON));
        getDriver().findElement(ADD_MAVEN_BUTTON).click();
        scrollPageDown();
        getWait(5).until(steadinessOfElementLocated(ADD_MAVEN_BUTTON));
        WebElement fieldName = getDriver().findElement(By.cssSelector("input[checkurl$='MavenInstallation/checkName']"));
        fieldName.click();
        fieldName.sendKeys("Maven");
        getDriver().findElement(By.id("yui-gen5-button")).click();

        Assert.assertFalse(getDriver().findElement(
                By.xpath("//input[contains(@checkurl,'MavenInstallation/checkName')]/parent::div/following-sibling::div"))
                    .getText().contains("Required"));
    }

    @Test(dependsOnMethods = "testCheckingDisappearanceOfWarningMessage")
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
        final  String jobName = RANDOM_STRING;

        setJobPipeline(jobName);
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(ITEM_DESCRIPTION);
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description >*:first-child"))
                .getAttribute("textContent"),ITEM_DESCRIPTION);
    }

    @Test (dependsOnMethods = "testCreateNewPipelineWithDescription")
    public void testCreateNewPipelineFromExisting() {
        final String jobName = TestUtils.getRandomStr(7);

        setJobPipeline(jobName);
        scrollPageDown();
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
}
