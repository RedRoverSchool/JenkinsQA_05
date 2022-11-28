import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class RenamePipelineTest extends BaseTest {
    private static final String JOB_NAME = RandomStringUtils.randomAlphanumeric(5);
    private static final String JOB_RENAME = "renamed";
    private static final By JENKINS_DASHBOARD =
            By.xpath("//button[@class='jenkins-menu-dropdown-chevron']/../../a[contains(text(),'Dashboard')]");
    private static final By BUTTON_DELETE = By.cssSelector("svg.icon-edit-delete");
    private static final By JOB_PIPELINE =
            By.xpath("//a[@href='job/" + JOB_NAME + "/']");
    private static final By JOB_PIPELINE_MENU_DROPDOWN_CHEVRON =
            By.xpath("//a[@href='job/" + JOB_NAME + "/']//*[@class='jenkins-menu-dropdown-chevron']");
    private static final By JOB_MENU_RENAME = By.xpath("//div[@id='breadcrumb-menu']//span[contains(text(),'Rename')]");
    private static final By TEXTFIELD_NEW_NAME = By.name("newName");
    private static final By BUTTON_RENAME = By.id("yui-gen1-button");

    private void createPipelineProject() {
        getDriver().findElement(JENKINS_DASHBOARD).click();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//*[text() = 'Pipeline']")).click();
        getDriver().findElement(By.id("name")).sendKeys(JOB_NAME);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("yui-gen6-button")).click();
        getDriver().findElement(JENKINS_DASHBOARD).click();
    }

    private void renamePipelineProject() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(JOB_PIPELINE));
        actions.moveToElement(getDriver().findElement(JOB_PIPELINE_MENU_DROPDOWN_CHEVRON)).click().perform();
        getDriver().findElement(JOB_MENU_RENAME).click();
        getDriver().findElement(TEXTFIELD_NEW_NAME).clear();
        getDriver().findElement(TEXTFIELD_NEW_NAME).sendKeys(JOB_RENAME + JOB_NAME);
        getDriver().findElement(BUTTON_RENAME).click();
    }

    private void deletePipelineProject() {
        getDriver().findElement(JENKINS_DASHBOARD).click();
        getDriver().findElement(By.xpath("//a[@href = contains(., '" + JOB_NAME + "')]/button")).click();
        getDriver().findElement(BUTTON_DELETE).click();
        getDriver().switchTo().alert().accept();
    }

    @Test
    public void testRenamePipelineWithValidName() {
        createPipelineProject();
        renamePipelineProject();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText()
                , "Pipeline " + JOB_RENAME + JOB_NAME);

        deletePipelineProject();
    }
}