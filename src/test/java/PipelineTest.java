import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class PipelineTest extends BaseTest {
    private static final String RENAME_SUFFIX = "renamed";
    private static final String PIPELINE_NAME = generatePipelineProjectName();
    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By ITEM_NAME = By.id("name");
    private static final By PIPELINE = By.xpath("//span[text() = 'Pipeline']");
    private static final By BUTTON_OK = By.id("ok-button");
    private static final By BUTTON_SAVE = By.id("yui-gen6-button");
    private static final By BUTTON_DISABLE_PROJECT = By.id("yui-gen1-button");
    private static final By BUTTON_DELETE = By.cssSelector("svg.icon-edit-delete");
    private static final By DASHBOARD = By.xpath("//a[text()='Dashboard']");
    private static final By JOB_PIPELINE =
            By.xpath(String.format("//span[text()[contains(.,'%s')]]", PIPELINE_NAME));
    private static final By JOB_PIPELINE_MENU_DROPDOWN_CHEVRON =
            By.xpath(String.format("//span[text()[contains(.,'%s')]]/../button", PIPELINE_NAME));
    private static final By JOB_MENU_RENAME =
            By.xpath("//div[@id='breadcrumb-menu']//span[contains(text(),'Rename')]");
    private static final By TEXTFIELD_NEW_NAME = By.name("newName");
    private static final By BUTTON_RENAME = By.id("yui-gen1-button");
    private static final By MY_VIEWS = By.xpath("//a[@href='/me/my-views']");
    private static final By ADD_TAB = By.className("addTab");
    private static final By VIEW_NAME_FIELD = By.id("name");
    private static final String VIEW_NAME = RandomStringUtils.randomAlphanumeric(5);
    private static final By RADIO_BUTTON_MY_VIEW =
            By.xpath("//input[@id='hudson.model.MyView']/..//label[@class='jenkins-radio__label']");
    private static final By BUTTON_CREATE = By.id("ok");
    private static final By VIEW =
            By.xpath(String.format("//div/a[contains(text(),'%s')]", VIEW_NAME));

    private static String generatePipelineProjectName() {
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
    private void deletePipelineProject(String name) {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath(String.format("//a[@href = contains(., '%s')]/button", name))).click();
        getDriver().findElement(BUTTON_DELETE).click();
        getDriver().switchTo().alert().accept();
    }
    private void renamePipelineProject(String name, String rename) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(JOB_PIPELINE))
                .moveToElement(getDriver().findElement(JOB_PIPELINE_MENU_DROPDOWN_CHEVRON)).click().build().perform();
        getDriver().findElement(JOB_MENU_RENAME).click();
        getDriver().findElement(TEXTFIELD_NEW_NAME).clear();
        getDriver().findElement(TEXTFIELD_NEW_NAME).sendKeys(name + rename);
        getDriver().findElement(BUTTON_RENAME).click();
    }
    private void createNewViewOfTypeMyView() {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(ADD_TAB).click();
        getDriver().findElement(VIEW_NAME_FIELD).sendKeys(VIEW_NAME);
        getDriver().findElement(RADIO_BUTTON_MY_VIEW).click();
        getDriver().findElement(BUTTON_CREATE).click();
    }

    private void deleteNewView() {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(VIEW).click();
        getDriver().findElement(BUTTON_DELETE).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
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
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(PIPELINE).click();
        getDriver().findElement(ITEM_NAME).sendKeys(pipelinePojectName);
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(BUTTON_SAVE).click();

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(pipelinePojectName + "description");
        getDriver().findElement(By.id("yui-gen2-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), pipelinePojectName + "description");
    }

    @Test
    public void testNewPipelineItemAppearedInTheList() {

        String pipelineProjectName = generatePipelineProjectName();
        createPipelineProject(pipelineProjectName);

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//a[@href='job/" + pipelineProjectName + "/']")).getText(), pipelineProjectName);
    }

    @Test
    public void testRenamePipelineWithValidName() {
        createPipelineProject(PIPELINE_NAME);
        renamePipelineProject(PIPELINE_NAME, RENAME_SUFFIX);

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText()
                , "Pipeline " + PIPELINE_NAME + RENAME_SUFFIX);

        deletePipelineProject(PIPELINE_NAME);
    }
    @Test
    public void testRenamedPipelineIsDisplayedInMyViews() {
        createPipelineProject(PIPELINE_NAME);
        createNewViewOfTypeMyView();
        renamePipelineProject(PIPELINE_NAME, RENAME_SUFFIX);
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(VIEW).click();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath(String.format("//tbody//a[@href = contains(., '%s%s')]",PIPELINE_NAME, RENAME_SUFFIX))).getText()
                , PIPELINE_NAME + RENAME_SUFFIX);

        deleteNewView();
        deletePipelineProject(PIPELINE_NAME);
    }
}
