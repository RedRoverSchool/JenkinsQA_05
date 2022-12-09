import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.ProjectUtils;
import runner.TestUtils;

public class NewViewTest extends BaseTest {
    private static final By PIPELINE = By.xpath("//*[text() = 'Pipeline']");
    private static final By DASHBOARD = By.id("jenkins-name-icon");
    private static final By MY_VIEWS = By.xpath("//a[@href='/me/my-views']");
    private static final String VIEW_NAME = RandomStringUtils.randomAlphanumeric(5);
    private static final String LIST_VIEW_NAME = RandomStringUtils.randomAlphanumeric(6);
    private static final String PROJECT_NAME = RandomStringUtils.randomAlphanumeric(6);
    private static final By RADIO_BUTTON_MY_VIEW = By.xpath("//label[@for='hudson.model.MyView']");
    private static final By RADIO_BUTTON_LIST_VIEW = By.xpath("//label[@for='hudson.model.ListView']");
    private static final By BUTTON_DELETE = By.cssSelector("svg.icon-edit-delete");
    private static final By BUTTON_S = By.xpath("//div/ol/li/a[@href='/iconSize?16x16'][@class='yui-button link-button']");
    private static final By BUTTON_M = By.xpath("//div/ol/li/a[@href='/iconSize?24x24'][@class='yui-button link-button']");
    private static final By BUTTON_L = By.xpath("//div/ol/li/a[@href='/iconSize?32x32'][@class='yui-button link-button']");
    private static final By MY_VIEWS_TABLE = By.xpath("//table[@id='projectstatus']");
    private static final By MULTI_CONFIGURATION_PROJECT = By.cssSelector(".hudson_matrix_MatrixProject");

    private boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Test
    public void testCreateNewView() {
        ProjectUtils.createNewItemFromDashboard(getDriver(),PIPELINE, PROJECT_NAME);
        getDriver().findElement(DASHBOARD).click();
        ProjectUtils.createNewViewFromDashboard(getDriver(),RADIO_BUTTON_MY_VIEW, VIEW_NAME);

        Assert.assertTrue(getDriver().findElement(By.xpath(String.format("//div/a[contains(text(),'%s')]", VIEW_NAME)))
                .isDisplayed());
    }

    @Test(dependsOnMethods = "testClickOnIconLegend")
    public void testDeleteNewView() {
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(By.xpath(String.format("//div/a[contains(text(),'%s')]", VIEW_NAME))).click();
        getDriver().findElement(BUTTON_DELETE).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();

        Assert.assertFalse(isElementPresent(getDriver(), By.xpath(String.format("//div/a[contains(text(),'%s')]", VIEW_NAME))));
    }

    @Test(dependsOnMethods = "testCreateNewView")
    public void testLettersSMLClickableMyViews() {
        String expectedClassTableM = "jenkins-table jenkins-table--medium sortable";
        String expectedClassTableL = "jenkins-table  sortable";
        String expectedClassTableS = "jenkins-table jenkins-table--small sortable";

        getWait(1).until(ExpectedConditions.elementToBeClickable(BUTTON_M)).click();

        Assert.assertEquals(getDriver().findElement(MY_VIEWS_TABLE).getAttribute("class"), expectedClassTableM);

        getWait(1).until(ExpectedConditions.elementToBeClickable(BUTTON_L)).click();

        Assert.assertEquals(getDriver().findElement(MY_VIEWS_TABLE).getAttribute("class"), expectedClassTableL);

        getWait(1).until(ExpectedConditions.elementToBeClickable(BUTTON_S)).click();

        Assert.assertEquals(getDriver().findElement(MY_VIEWS_TABLE).getAttribute("class"), expectedClassTableS);
    }

    @Test(dependsOnMethods = "testLettersSMLClickableMyViews")
    public void testClickOnIconLegend() {

        getWait(1).until(ExpectedConditions.elementToBeClickable((By.xpath("//a[@href='/legend']")))).click();

        Assert.assertEquals(getDriver().findElements(By.xpath("//table[@id='legend-table']//tr")).size(), 17);
    }

    @Test()
    public void testCreateListViewWithExtraSettings() {
        ProjectUtils.createNewItemFromDashboard(getDriver(),MULTI_CONFIGURATION_PROJECT, PROJECT_NAME);
        getDriver().findElement(DASHBOARD).click();
        ProjectUtils.createNewViewFromDashboard(getDriver(),RADIO_BUTTON_LIST_VIEW, LIST_VIEW_NAME);

        getDriver().findElement(By.cssSelector(String.format("label[title='%s']", PROJECT_NAME))).click();
        int countColumnsBeforeAdd = getDriver().findElements(By.cssSelector(".repeated-chunk__header")).size();
        TestUtils.scrollToEnd(getDriver());
        getWait(5).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".bottom-sticker-inner--stuck")));
        getDriver().findElement(By.cssSelector("#yui-gen3-button")).click();
        getDriver().findElement(By.linkText("Build Button")).click();
        getDriver().findElement(By.cssSelector("#yui-gen5-button")).click();

        String TextConfirmAfterClickingApply = getWait(10).until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#notification-bar"))).getText();
        getDriver().findElement(By.cssSelector("#yui-gen6-button")).click();

        getDriver().findElement(By.linkText("My Views")).click();
        getDriver().findElement(By.linkText(LIST_VIEW_NAME)).click();
        getDriver().findElement(By.linkText("Edit View")).click();

        String actualMarkedProjectName = getDriver().findElement(By.cssSelector("input[checked='true']")).getAttribute("name");
        int countColumnsAfterAdd = getDriver().findElements(By.className("repeated-chunk__header")).size();
        Assert.assertEquals(actualMarkedProjectName, PROJECT_NAME);
        Assert.assertEquals(countColumnsAfterAdd, countColumnsBeforeAdd + 1);
        Assert.assertEquals(TextConfirmAfterClickingApply, "Saved");
    }
}