import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class PipelineTest extends BaseTest {

    private static final String PIPELINE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By ITEM_NAME = By.id("name");
    private static final By PIPELINE = By.xpath("//*[text() = 'Pipeline']");
    private static final By BUTTON_OK = By.id("ok-button");
    private static final By BUTTON_SAVE = By.id("yui-gen6-button");
    private static final By JENKINS_ICON = By.id("jenkins-name-icon");
    private static final By BUTTON_DISABLE_PROJECT = By.id("yui-gen1-button");
    private static final By PROJECT =
            By.xpath(String.format("//td/a/span[contains(text(),'%s')]", PIPELINE_NAME));

    private static final By DASHBOARD = By.xpath("//a[text()='Dashboard']");

    private void createPipelineProject() {
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(PIPELINE).click();
        getDriver().findElement(ITEM_NAME).sendKeys(PIPELINE_NAME);
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(BUTTON_SAVE).click();

        getDriver().findElement(JENKINS_ICON).click();
    }

    private void deletePipelineProject() {
        getDriver().findElement(JENKINS_ICON).click();
        getDriver().findElement(PROJECT).click();
        getDriver().findElement(By.cssSelector("svg.icon-edit-delete")).click();
        getDriver().switchTo().alert().accept();
    }

    @Ignore
    @Test
    public void testDisablePipelineProjectMessage() {
        createPipelineProject();

        getDriver().findElement(JENKINS_ICON).click();
        getDriver().findElement(PROJECT).click();
        getDriver().findElement(BUTTON_DISABLE_PROJECT).click();

        Assert.assertTrue(getDriver().findElement(By.id("enable-project")).getText()
                .contains("This project is currently disabled"));

        deletePipelineProject();
    }
    @Test
    public void testCreatedPipelineDisplayedOnMyViews() {

        createPipelineProject();
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//a[@href='job/" + PIPELINE_NAME + "/']")).isDisplayed());
    }
}
