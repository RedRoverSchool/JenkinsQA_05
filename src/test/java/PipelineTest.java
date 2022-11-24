import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.AcceptedW3CCapabilityKeys;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class PipelineTest extends BaseTest {

    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By ITEM_NAME = By.id("name");
    private static final By PIPELINE = By.xpath("//*[text() = 'Pipeline']");
    private static final By BUTTON_OK = By.id("ok-button");
    private static final By BUTTON_SAVE = By.id("yui-gen6-button");
    private static final By BUTTON_DISABLE_PROJECT = By.id("yui-gen1-button");
    private static final By DASHBOARD = By.xpath("//a[text()='Dashboard']");

    private String generatePipelineProjectName() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private void createPipelineProject(String projectName) {

        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(PIPELINE).click();
        getDriver().findElement(ITEM_NAME).sendKeys(projectName);
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(BUTTON_SAVE).click();
        getDriver().findElement(DASHBOARD).click();
    }

    private void createPipelineProjectCuttedVersion(String projectName) {

        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(PIPELINE).click();
        getDriver().findElement(ITEM_NAME).sendKeys(projectName);
        getDriver().findElement(BUTTON_OK).click();
    }

    @Test
    public void testDisablePipelineProjectMessage() {

        String pipelinePojectName = generatePipelineProjectName();
        createPipelineProject(pipelinePojectName);

        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath(String.format("//td/a/span[contains(text(),'%s')]", pipelinePojectName))).click();
        getDriver().findElement(BUTTON_DISABLE_PROJECT).click();

        Assert.assertTrue(getDriver().findElement(By.id("enable-project")).getText()
                .contains("This project is currently disabled"));
    }

    @Test
    public void testCreatedPipelineDisplayedOnMyViews() {

        String pipelinePojectName = generatePipelineProjectName();
        createPipelineProject(pipelinePojectName);

        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//a[@href='job/" + pipelinePojectName + "/']"))
                .isDisplayed());
    }

    @Test
    public void testPipelineAddDescription() {

        String pipelinePojectName = generatePipelineProjectName();
        createPipelineProjectCuttedVersion(pipelinePojectName);
        getDriver().findElement(BUTTON_SAVE).click();

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(pipelinePojectName + "description");
        getDriver().findElement(By.id("yui-gen2-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), pipelinePojectName + "description");
    }

    @Test
    public void testPipelinePreviewDescription() {

        String pipelinePojectName = generatePipelineProjectName();
        createPipelineProjectCuttedVersion(pipelinePojectName);

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(pipelinePojectName + "description");
        getDriver().findElement(By.className("textarea-show-preview")).click();

        Assert.assertEquals(getDriver().findElement(By.className("textarea-preview")).getText(), pipelinePojectName + "description");

        getDriver().findElement(BUTTON_SAVE).click();
    }

    @Test
    public void testPipelineHidePreviewDescription() {

        String pipelinePojectName = generatePipelineProjectName();
        createPipelineProjectCuttedVersion(pipelinePojectName);

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(pipelinePojectName + "description");
        getDriver().findElement(By.className("textarea-show-preview")).click();
        getDriver().findElement(By.className("textarea-hide-preview")).click();

        Assert.assertFalse(getDriver().findElement(By.className("textarea-preview")).isDisplayed());

        getDriver().findElement(BUTTON_SAVE).click();
    }
}
