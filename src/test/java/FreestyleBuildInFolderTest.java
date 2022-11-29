import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.UUID;

public class FreestyleBuildInFolderTest extends BaseTest {
    private static final String DESCRIPTION = "This folder contains job's documentation";
    private static final String DESCRIPTION_FREESTYLE = "Freestyle job";
    private static String x = UUID.randomUUID().toString().substring(0, 2);
    private static String name = "project-" + x;
    private static final By FOLDER = By.cssSelector("li[class='com_cloudbees_hudson_plugins_folder_Folder'] div[class='desc']");
    private static final By ITEM_NAME = By.cssSelector("#name");
    private static final By DESCRIPTION_IN = By.cssSelector("textarea[name='_.description']");
    private static final By DESCRIPTION_JOB = By.cssSelector("textarea[name='description']");
    private static final By SAVE_FOLDER = By.cssSelector("#yui-gen6-button");
    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By DISPLAY_NAME = By.cssSelector("input[name='_.displayNameOrNull']");
    private static final By PROJECT_TYPE = By.xpath("//span[normalize-space()='Freestyle project']");
    private static final By OK_BUTTON = By.cssSelector(".yui-button.primary.large-button");
    private static final By SAVE_BUTTON = By.xpath("//*[contains(@id, 'yui-gen') and contains(@type,'submit')]");
    private static final By DASHBOARD = By.xpath("//a[normalize-space()='Dashboard']");
    private static final By CREATED_FOLDER = By.xpath("//a[@class='jenkins-table__link model-link inside']");
    private static final By NEW_ITEM_DROPDOWN = By.xpath("//*[contains(@class,'icon-new-package icon-md')]");
    private static final By DOCS_SELECT = By.xpath("//*[contains(@class,'jenkins-table')and contains(@href,'job/Docs/')]");
    private static final By CONSOLE_OUTPUT = By.xpath("//span[normalize-space()='Console Output']");
    private static final By BUILD_DROPDOWN = By.cssSelector("a[class='model-link inside build-link display-name'] button[class='jenkins-menu-dropdown-chevron']");
    private static final By JOB_IN = By.cssSelector("a[class='jenkins-table__link model-link inside']");
    private static final By BUILD = By.cssSelector("#yui-gen3");
    private static final By JOB_DROPDOWN = By.cssSelector("a[class='jenkins-table__link model-link inside'] button[class='jenkins-menu-dropdown-chevron']");
    private static final By DOCS_IN = By.xpath("//a[@class='jenkins-table__link model-link inside']");

    @Test
    public void createFolderTest() {

        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(ITEM_NAME).sendKeys("Docs");
        getDriver().findElement(FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(DISPLAY_NAME).sendKeys("Docs");
        getDriver().findElement(DESCRIPTION_IN).sendKeys(DESCRIPTION);
        getDriver().findElement(SAVE_FOLDER).click();

        Assert.assertTrue(getDriver().findElement(
                By.cssSelector("#view-message")).getText().contains(DESCRIPTION));
    }
    @Test(dependsOnMethods = {"createFolderTest"})
    public void testCreateFreestyleProject() throws InterruptedException {

        getDriver().findElement(CREATED_FOLDER).click();
        getDriver().findElement(NEW_ITEM_DROPDOWN).click();
        getDriver().findElement(ITEM_NAME).sendKeys(name);
        getDriver().findElement(PROJECT_TYPE).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(DESCRIPTION_JOB).sendKeys(DESCRIPTION_FREESTYLE);
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(DOCS_SELECT).click();
        getDriver().findElement(DOCS_IN).click();
        Thread.sleep(3000);

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText(), name);
    }

    @Test(dependsOnMethods = {"testCreateFreestyleProject"})
    public void testBuildFreestyleProject() throws InterruptedException {

        getDriver().findElement(DOCS_IN).click();
        getDriver().findElement(JOB_IN).click();
        getDriver().findElement(JOB_DROPDOWN).click();
        Thread.sleep(3000);
        getDriver().findElement(BUILD).click();
        getDriver().findElement(JOB_IN).click();
        getDriver().findElement(BUILD_DROPDOWN).click();
        getDriver().findElement(CONSOLE_OUTPUT).click();
        Thread.sleep(3000);

        Assert.assertTrue(getDriver().findElement(
                By.cssSelector(".console-output")).getText().toUpperCase().contains("SUCCESS"));
    }
}

