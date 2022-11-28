import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class RenamePipelineTest extends BaseTest {

    private static final By JENKINS_ICON = By.id("jenkins-name-icon");
    private static final By MY_VIEWS = By.xpath("//a[@href='/me/my-views']");
    private static final String VIEW_NAME = RandomStringUtils.randomAlphanumeric(5);
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
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//*[text() = 'Pipeline']")).click();
        getDriver().findElement(By.id("name")).sendKeys("pip1");
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("yui-gen6-button")).click();
        getDriver().findElement(JENKINS_ICON).click();
    }

    private void renamePipelineProject() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(JOB_PIP1));
        actions.moveToElement(getDriver().findElement(JOB_PIP1_MENU_DROPDOWN_CHEVRON)).click().build().perform();
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

    private void createNewView() {
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(By.className("addTab")).click();
        getDriver().findElement(By.id("name")).sendKeys(VIEW_NAME);
        getDriver().findElement(By.xpath("//input[@id='hudson.model.MyView']/..//label[@class='jenkins-radio__label']")).click();
        getDriver().findElement(By.id("ok")).click();
    }

    private void deleteNewView() {
        getDriver().findElement(JENKINS_ICON).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(VIEW).click();
        getDriver().findElement(BUTTON_DELETE).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
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

    @Test
    public void testRenamedPipelineIsDisplayedInMyViews() {
        createPipelineProject();
        createNewView();
        renamePipelineProject();
        getDriver().findElement(JENKINS_ICON).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(VIEW).click();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//tbody//a[@href = contains(., 'renamed_pip1')]")).getText()
                , "renamed_pip1");

        deleteNewView();
        deletePipelineProject();
    }

    @Test
    public void testRenamePipelineWithoutChangingName() {
        createPipelineProject();
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(JOB_PIP1));
        actions.moveToElement(getDriver().findElement(JOB_PIP1_MENU_DROPDOWN_CHEVRON)).click().build().perform();
        getDriver().findElement(JOB_MENU_RENAME).click();
        getDriver().findElement(BUTTON_RENAME).click();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//div[@id='main-panel']//h1[contains(text(),'Error')]")).getText()
                , "Error");
        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//div[@id='main-panel']//p")).getText()
                , "The new name is the same as the current name.");

        deletePipelineProject();
    }

    @Test
    public void testRenamePipelineUsingSpecialCharacter() {
        String specialCharactersString = "!@#$%*/:;?[]^|";
        for (int i = 0; i < specialCharactersString.length(); i++) {
            createPipelineProject();
            Actions actions = new Actions(getDriver());
            actions.moveToElement(getDriver().findElement(JOB_PIP1)).pause(100);
            actions.moveToElement(getDriver().findElement(JOB_PIP1_MENU_DROPDOWN_CHEVRON)).click().build().perform();
            getDriver().findElement(JOB_MENU_RENAME).click();
            getDriver().findElement(TEXTFIELD_NEW_NAME).clear();
            getDriver().findElement(TEXTFIELD_NEW_NAME).sendKeys("renamed_pip1" + specialCharactersString.charAt(i));
            getDriver().findElement(BUTTON_RENAME).click();

            Assert.assertEquals(getDriver()
                            .findElement(By.xpath("//div[@id='main-panel']//h1[contains(text(),'Error')]")).getText()
                    , "Error");
            Assert.assertEquals(getDriver()
                            .findElement(By.xpath("//div[@id='main-panel']//p")).getText()
                    , String.format("‘%s’ is an unsafe character", specialCharactersString.charAt(i)));
            deletePipelineProject();
        }
    }
}