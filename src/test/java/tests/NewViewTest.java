package tests;

import model.views.EditListViewPage;
import model.views.EditViewPage;
import model.HomePage;
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
    private static final String VIEW_NAME = TestUtils.getRandomStr(6);
    private static final String PROJECT_RANDOM_NAME = TestUtils.getRandomStr(6);
    private static final By RADIO_BUTTON_MY_VIEW = By.xpath("//label[@for='hudson.model.MyView']");
    private static final By BUTTON_DELETE = By.cssSelector("svg.icon-edit-delete");
    private static final By BUTTON_S = By.xpath("//div/ol/li/a[@href='/iconSize?16x16'][@class='yui-button link-button']");
    private static final By BUTTON_M = By.xpath("//div/ol/li/a[@href='/iconSize?24x24'][@class='yui-button link-button']");
    private static final By BUTTON_L = By.xpath("//div/ol/li/a[@href='/iconSize?32x32'][@class='yui-button link-button']");
    private static final By MY_VIEWS_TABLE = By.xpath("//table[@id='projectstatus']");

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
        ProjectUtils.createNewItemFromDashboard(getDriver(), PIPELINE, TestUtils.getRandomStr(6));
        getDriver().findElement(DASHBOARD).click();
        ProjectUtils.createNewViewFromDashboard(getDriver(), RADIO_BUTTON_MY_VIEW, VIEW_NAME);

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

    @Test
    public void testCreateListViewWithAddSettings() {
        int countColumnsBeforeAdd = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(PROJECT_RANDOM_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn()
                .clickDashboard()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestUtils.getRandomStr(6))
                .setListViewTypeAndClickCreate()
                .addJobToView(PROJECT_RANDOM_NAME)
                .getCountColumns();

        String textConfirmAfterClickingApply = new EditListViewPage(getDriver())
                .addColumn("Build Button")
                .clickApplyButton()
                .getTextConfirmAfterClickingApply();

        String actualMarkedProjectName = new EditViewPage(getDriver())
                .clickGlobalViewOkButton()
                .clickEditViewButton()
                .getSelectedJobName();

        int countColumnsAfterAdd = new EditListViewPage(getDriver())
                .getCountColumns();

        Assert.assertEquals(actualMarkedProjectName, PROJECT_RANDOM_NAME);
        Assert.assertEquals(countColumnsAfterAdd, countColumnsBeforeAdd + 1);
        Assert.assertEquals(textConfirmAfterClickingApply, "Saved");
    }
}
