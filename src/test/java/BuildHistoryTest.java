import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.time.Duration;
import java.util.List;

public class BuildHistoryTest extends BaseTest {

    private static final By DASHBOARD = By.xpath("//a[contains(text(), 'Dashboard')]");
    private static final By BUILD_NOW_BTN = By.xpath("//body[1]/div[3]/div[1]/div[1]/div[5]/span[1]");
    private static final By ICON_SIZE = By.xpath("//a[@class='jenkins-table__button']//*[name()='svg']");
    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By INPUT_NAME = By.name("name");
    private static final By FREESTYLE_PROJECT = By.xpath("//li[@class='hudson_model_FreeStyleProject']");
    private static final By OK_BUTTON = By.xpath("//span[@class='yui-button primary large-button']");
    private static final By DESCRIPTION_FIELD = By.name("description");
    private static final By SAVE_BUTTON = By.xpath("//button[@type = 'submit']");
    private static final By BUILD_HISTORY = By.linkText("Build History");
    private static final By H1_HEADER_BUILD_HISTORY = By.xpath("//div[@class='jenkins-app-bar__content']/h1");
    private static final By BUILD_NOW = By.linkText("Build Now");
    private static final By TREND_BUILD = By.xpath("//div[@class='jenkins-pane__header--build-history']/a");
    private static final By BUTTON_S = By.xpath("//a[@href='/iconSize?16x16']");
    private static final By BUTTON_M = By.xpath("//div[@class='jenkins-icon-size__items jenkins-buttons-row']/ol/li/following-sibling::li[2]");
    private static final By BUTTON_L = By.xpath("//div[@class='jenkins-icon-size__items jenkins-buttons-row']/ol/li[last()]");
    private static final By ATOM_FEED_ALL = By.xpath("//a/span[contains(text(), 'Atom feed for all')]");
    private static final By ATOM_FEED_FAILURE = By.xpath("//a/span[contains(text(), 'Atom feed for failures')]");
    private static final By ATOM_FEED_LATEST = By.xpath("//a/span[contains(text(), 'Atom feed for just latest builds')]");
    private static final By ICON_LEGEND = By.xpath("//a[@href='/legend']");
    private static final By PROJECT_STATUS_TABLE = By.xpath("//table[@id='projectStatus']/thead/tr/th");
    private static final By DESCRIPTION_EDIT_PROJECT = By.name("description");

    private static String jobName = "";

    private WebDriverWait wait;

    private WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        }
        return wait;
    }

    private void inputName(By by) {
        jobName = TestUtils.getRandomStr(8);
        getDriver().findElement(by).sendKeys(jobName);
    }

    private void clickElement(By by) {
        getDriver().findElement(by).click();
    }

    private String getText(By by) {

        return getDriver().findElement(by).getText();
    }

    private void createProject() {
        clickElement(NEW_ITEM);
        inputName(INPUT_NAME);
        clickElement(FREESTYLE_PROJECT);
        clickElement(OK_BUTTON);
        inputName(DESCRIPTION_FIELD);
        clickElement(SAVE_BUTTON);
    }
    private void createProjectNoDescr() {
        clickElement(NEW_ITEM);
        inputName(INPUT_NAME);
        clickElement(FREESTYLE_PROJECT);
        clickElement(OK_BUTTON);
        clickElement(SAVE_BUTTON);
    }
    public List<WebElement> getListOfElements(By by) {

        return getDriver().findElements(by);
    }

    public int getListSize(By by) {

        return getListOfElements(by).size();
    }

    @Test
    public void testVerifyRedirectToMainPage() {
        getDriver().findElement(
                By.linkText("Build History")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description-link")).getText(),
                "Add description");
    }

    @Test
    public void testVerifyDefaultIconSize() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(TestUtils.getRandomStr(8));
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(BUILD_NOW_BTN).click();
        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        getDriver().findElement(By.linkText("Build History")).click();

        int width = getDriver().findElement(ICON_SIZE).getSize().getWidth();
        int height = getDriver().findElement(ICON_SIZE).getSize().getHeight();
        String size = getDriver().findElement(By.className("jenkins-icon-size__items-item")).getText();

        switch (size) {
            case "S\nmall":
                Assert.assertEquals(width, 16);
                Assert.assertEquals(height, 16);
                break;
            case "M\nedium":
                Assert.assertEquals(width, 20);
                Assert.assertEquals(height, 20);
                break;
            case "L\narge":
                Assert.assertEquals(width, 24);
                Assert.assertEquals(height, 24);
                break;
        }
    }

    @Test
    public void testH1Header_BuildHistory() {
        createProject();
        clickElement(DASHBOARD);
        clickElement(BUILD_HISTORY);

        Assert.assertEquals(getText(H1_HEADER_BUILD_HISTORY), "Build History of Jenkins");
    }

    @Test
    public void testValidityCreateBuildOnPage() {
        createProject();
        clickElement(BUILD_NOW);
        clickElement(TREND_BUILD);

        Assert.assertTrue(getDriver().findElement(By.id("map")).isDisplayed());
    }

    @Test
    public void testIfSMLIconsExist() {
        clickElement(BUILD_HISTORY);

        Assert.assertTrue(getDriver().findElement(BUTTON_S).isDisplayed());
        Assert.assertTrue(getDriver().findElement(BUTTON_M).isDisplayed());
        Assert.assertTrue(getDriver().findElement(BUTTON_L).isDisplayed());
    }

    @Test
    public void testRssItemsExist() {
        clickElement(BUILD_HISTORY);

        Assert.assertTrue(getDriver().findElement(ATOM_FEED_ALL).isDisplayed());
        Assert.assertTrue(getDriver().findElement(ATOM_FEED_FAILURE).isDisplayed());
        Assert.assertTrue(getDriver().findElement(ATOM_FEED_LATEST).isDisplayed());
    }

    @Test
    public void testIfTheIconLegendExist() {
        clickElement(BUILD_HISTORY);

        Assert.assertTrue(getDriver().findElement(ICON_LEGEND).isDisplayed());
    }

    @Test
    public void testNumberOfColumns_ProjectStatusTable() {
        clickElement(BUILD_HISTORY);

        Assert.assertEquals(getListSize(PROJECT_STATUS_TABLE), 5);
    }

    @Test
    public void testTimelineItemExist () {
        createProjectNoDescr();
        clickElement(BUILD_NOW);
        clickElement(DASHBOARD);
        clickElement(BUILD_HISTORY);

        while(!((getDriver().findElement(By.xpath("//table[@id='projectStatus']//td[4]")).getText().equals("stable")))) {
            getDriver().navigate().refresh();
        }
        getDriver().findElement(By.xpath("//div[contains(text(), \"" + jobName + "\")]")).click();
        String jobLink = "http://localhost:8080/job/" + jobName + "/1/";

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='timeline-event-bubble-title']/a")).getAttribute("href"), jobLink);
    }

    @Test(dependsOnMethods = "testTimelineItemExist")
    public void testDescriptionIsAdded() {
        getDriver().findElement(By.xpath("//table/tbody/tr/td/a[@class='jenkins-table__link jenkins-table__badge model-link inside']/button")).click();
        getDriver().findElement(By.xpath("//span[contains(text(),'Edit Build Information')]")).click();
        inputName(DESCRIPTION_EDIT_PROJECT);
        clickElement(SAVE_BUTTON);

        Assert.assertTrue(getDriver().findElement(By.id("description")).isDisplayed());
    }
}