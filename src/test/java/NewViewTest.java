import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class NewViewTest extends BaseTest {

    private static final String PIPELINE_NAME = RandomStringUtils.randomAlphanumeric(5);
    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By ITEM_NAME = By.id("name");
    private static final By PIPELINE = By.xpath("//*[text() = 'Pipeline']");
    private static final By BUTTON_OK = By.id("ok-button");
    private static final By BUTTON_SAVE = By.id("yui-gen6-button");
    private static final By JENKINS_ICON = By.id("jenkins-name-icon");
    private static final By MY_VIEWS = By.xpath("//a[@href='/me/my-views']");
    private static final By ADD_TAB = By.className("addTab");
    private static final By VIEW_NAME_FIELD = By.id("name");
    private static final String VIEW_NAME = RandomStringUtils.randomAlphanumeric(5);
    private static final By RADIO_BUTTON_MY_VIEW =
            By.xpath("//*[@id='createItemForm']/div[1]/div[2]/fieldset/div[3]/label");
    private static final By BUTTON_CREATE = By.id("ok");
    private static final By BUTTON_DELETE = By.cssSelector("svg.icon-edit-delete");
    private static final By VIEW =
            By.xpath(String.format("//div/a[contains(text(),'%s')]", VIEW_NAME));

    private static final By BUTTON_S = By.xpath("//div/ol/li/a[@href='/iconSize?16x16']");

    private static final By BUTTON_M = By.xpath("//div/ol/li/a[@href='/iconSize?24x24']");

    private static final By BUTTON_L = By.xpath("//div/ol/li/a[@href='/iconSize?32x32']");

    private static final By MY_VIEWS_TABLE = By.xpath("//table[@id='projectstatus']");


    private void createPipelineProject() {
        getDriver().findElement(JENKINS_ICON).click();
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(PIPELINE).click();
        getDriver().findElement(ITEM_NAME).sendKeys(PIPELINE_NAME);
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(BUTTON_SAVE).click();
        getDriver().findElement(JENKINS_ICON).click();
    }

    private void deletePipelineProject() {
        getDriver().findElement(JENKINS_ICON).click();
        getDriver().findElement(By.xpath(String.format("//a[@href = contains(., '%s')]/button", PIPELINE_NAME))).click();
        getDriver().findElement(BUTTON_DELETE).click();
        getDriver().switchTo().alert().accept();
    }

    private void deleteNewView() {
        getDriver().findElement(JENKINS_ICON).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(VIEW).click();
        getDriver().findElement(BUTTON_DELETE).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
    }

    @Test
    public void testCreateNewView() {
        createPipelineProject();

        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(ADD_TAB).click();
        getDriver().findElement(VIEW_NAME_FIELD).sendKeys(VIEW_NAME);
        getDriver().findElement(RADIO_BUTTON_MY_VIEW).click();
        getDriver().findElement(BUTTON_CREATE).click();

        Assert.assertTrue(getDriver().findElement(By.xpath(String.format("//div/a[contains(text(),'%s')]", VIEW_NAME))).isDisplayed());

        deleteNewView();
        deletePipelineProject();
    }

    @Test
    public void testLeterMClickableMyViews() {
        String expectedClassTable = "jenkins-table jenkins-table--medium sortable";

        createPipelineProject();

        getDriver().findElement(MY_VIEWS).click();
        WebElement ButtonM = getDriver().findElement(BUTTON_M);
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", ButtonM);

        Assert.assertEquals(getDriver().findElement(MY_VIEWS_TABLE).getAttribute("class"), expectedClassTable);
    }
}
