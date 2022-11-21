import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class NewViewTest extends BaseTest {

    private static final String PIPELINE_NAME = "testpipelineproject";
    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By ITEM_NAME = By.id("name");
    private static final By PIPELINE = By.xpath("//*[text() = 'Pipeline']");
    private static final By BUTTON_OK = By.id("ok-button");
    private static final By BUTTON_SAVE = By.id("yui-gen6-button");
    private static final By JENKINS_ICON = By.id("jenkins-name-icon");
    private static final By MY_VIEWS = By.xpath("//a[@href='/me/my-views']");
    private static final By ADD_TAB = By.className("addTab");
    private static final By VIEW_NAME_FIELD = By.id("name");
    private static final String VIEW_NAME = "test_view";
    private static final By RADIO_BUTTON_MY_VIEW = By.xpath("//*[@id='createItemForm']/div[1]/div[2]/fieldset/div[3]/label");
    private static final By BUTTON_CREATE = By.id("ok");
    private static final By PROJECT =
            By.xpath(String.format("//td/a/span[contains(text(),'%s')]", PIPELINE_NAME));


    private void createPipelineProject() {
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(PIPELINE).click();
        getDriver().findElement(ITEM_NAME).sendKeys(PIPELINE_NAME);
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(BUTTON_SAVE).click();

        getDriver().findElement(JENKINS_ICON).click();
    }

    private void deletePipelineProject() {
        getDriver().findElement(PROJECT).click();
        getDriver().findElement(By.cssSelector("svg.icon-edit-delete")).click();
        getDriver().switchTo().alert().accept();
    }

    private void deleteNewView() {
        getDriver().findElement(By.xpath("//*[@id='projectstatus-tabBar']/div/div[1]/div[2]/a")).click();
        getDriver().findElement(By.cssSelector("svg.icon-edit-delete")).click();
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

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='projectstatus-tabBar']/div/div[1]/div[2]/a")).getText(),
                "test_view");

        deleteNewView();
        deletePipelineProject();
    }
}
