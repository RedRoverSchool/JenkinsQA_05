import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class RenamePipelineTest extends BaseTest {
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
            By.xpath("//input[@id='hudson.model.MyView']/..//label[@class='jenkins-radio__label']");
    private static final By BUTTON_CREATE = By.id("ok");
    private static final By BUTTON_DELETE = By.cssSelector("svg.icon-edit-delete");
    private static final By VIEW =
            By.xpath(String.format("//div/a[contains(text(),'%s')]", VIEW_NAME));
    private static final By JOB_PIP1 =
            By.xpath("//tr[@id='job_pip1']//*[@class='jenkins-table__link model-link inside']");
    private static final By JOB_PIP1_MENU_DROPDOWN_CHEVRON =
            By.xpath("//tr[@id='job_pip1']//*[@class='jenkins-menu-dropdown-chevron']");
    private static final By JOB_MENU_RENAME = By.xpath("//div[@id='breadcrumb-menu']//span[contains(text(),'Rename')]");
    private static final By TEXTFIELD_NEW_NAME = By.name("newName");
    private static final By BUTTON_RENAME = By.id("yui-gen1-button");

    private void createPipelineProject() {
        getDriver().findElement(JENKINS_ICON).click();
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(PIPELINE).click();
        getDriver().findElement(ITEM_NAME).sendKeys("pip1");
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(BUTTON_SAVE).click();
        getDriver().findElement(JENKINS_ICON).click();
    }

    private void renamePipelineProject() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(JOB_PIP1));
        actions.moveToElement(getDriver().findElement(JOB_PIP1_MENU_DROPDOWN_CHEVRON)).click().perform();
        getDriver().findElement(JOB_MENU_RENAME).click();
        getDriver().findElement(TEXTFIELD_NEW_NAME).clear();
        getDriver().findElement(TEXTFIELD_NEW_NAME).sendKeys("renamed_pip1");
        getDriver().findElement(BUTTON_RENAME).click();
    }

    private void deletePipelineProject() {
        getDriver().findElement(JENKINS_ICON).click();
        getDriver().findElement(By.xpath("//a[@href = contains(., 'pip1')]/button")).click();
        getDriver().findElement(BUTTON_DELETE).click();
        getDriver().switchTo().alert().accept();
    }

    @Test
    public void testRenamePipelineWithValidName() {
        createPipelineProject();
        renamePipelineProject();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText()
                , "Pipeline " + "renamed_pip1");

        deletePipelineProject();
    }
}