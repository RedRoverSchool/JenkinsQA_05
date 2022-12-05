import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class FolderOneTest extends BaseTest {
    private static final By NEW_ITEM = By.linkText("New Item");
    private static final By NAME = By.id("name");
    private static final By NAME_CONFIGURE = By.xpath("//input[@name='_.displayNameOrNull']");
    private static final By NEW_NAME_RENAME = By.xpath("//input[@name='newName']");
    private static final By FOLDER_OPTION = By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']");
    private static final By PIPELINE_OPTION = By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']");
    private static final By DROP_DOWN_MENU = By.xpath("//td/a/button[@class='jenkins-menu-dropdown-chevron']");
    private static final By DROP_DOWN_CONFIGURE = By.xpath("//span[contains(text(),'Configure')]");
    private static final By DROP_DOWN_RENAME = By.xpath("//span[contains(text(),'Rename')]");
    private static final By DROP_DOWN_DELETE = By.xpath("//span[contains(text(),'Delete Folder')]");
    private static final By JENKINS_ICON = By.id("jenkins-name-icon");
    private static final By CREATE_JOB = By.linkText("Create a job");
    private static final By TEXT_ADDRESS = By.xpath("//div[@id='main-panel']");
    private static final By TEXT_H1 = By.xpath("//h1");
    private static final By TEXTAREA = By.xpath("//textarea[@name='_.description']");
    private static final By DELETE_FOLDER = By.linkText("Delete Folder");
    private static final By CONFIGURE_FOLDER = By.linkText("Configure");
    private static final By MOVE_FOLDER = By.linkText("Move");
    private static final By SELECTION_SCRIPT = By.xpath("//div[@class='samples']/select/option[4]");
    private static final String RANDOM_FOLDER_NAME = RandomStringUtils.randomAlphanumeric(6);
    private static final String RANDOM_PIPELINE_NAME = RandomStringUtils.randomAlphanumeric(6);

    private void submitButtonClick(){
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();
    }

    private void createFolder(){
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(NAME).sendKeys(RANDOM_FOLDER_NAME);
        getDriver().findElement(FOLDER_OPTION).click();
        submitButtonClick();
        submitButtonClick();
    }

    @Test
    public void testCreateNewFolder(){
        createFolder();
        getDriver().findElement(JENKINS_ICON).click();

        String actualFolderName = getDriver().findElement(By.linkText(RANDOM_FOLDER_NAME)).getText();

        Assert.assertEquals(RANDOM_FOLDER_NAME,actualFolderName);
    }

    @Test(dependsOnMethods = "testCreateNewFolder")
    public void testCreateFolderInFolder(){
        getDriver().findElement(By.linkText(RANDOM_FOLDER_NAME)).click();
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(NAME).sendKeys(RANDOM_PIPELINE_NAME);
        getDriver().findElement(FOLDER_OPTION).click();
        submitButtonClick();

        String actualFolderName = getDriver().findElement(By.id("breadcrumbs")).findElement(By.linkText(RANDOM_FOLDER_NAME)).getText();
        String actualPipelineName = getDriver().findElement(By.id("breadcrumbs")).findElement(By.linkText(RANDOM_PIPELINE_NAME)).getText();

        Assert.assertEquals(RANDOM_FOLDER_NAME,actualFolderName);
        Assert.assertEquals(RANDOM_PIPELINE_NAME,actualPipelineName);
    }

    @Test(dependsOnMethods = {"testCreateFolderInFolder"})
    public void testConfigureFolderDisplayName() {
        getDriver().findElement(By.linkText(RANDOM_FOLDER_NAME)).findElement(DROP_DOWN_MENU).click();
        getDriver().findElement(DROP_DOWN_CONFIGURE).click();
        getDriver().findElement(NAME_CONFIGURE).sendKeys(RANDOM_PIPELINE_NAME + "NEW");
        submitButtonClick();

        Assert.assertEquals(getDriver().findElement(TEXT_H1).getText(), (RANDOM_PIPELINE_NAME + "NEW"));
    }

    @Test(dependsOnMethods = {"testConfigureFolderDisplayName"})
    public void testAddFolderDescription(){
        getDriver().findElement(By.linkText(RANDOM_PIPELINE_NAME + "NEW")).findElement(DROP_DOWN_MENU).click();
        getDriver().findElement(DROP_DOWN_CONFIGURE).click();
        getDriver().findElement(TEXTAREA).sendKeys("NEW TEXT");
        submitButtonClick();

        Assert.assertTrue(getDriver().findElement(TEXT_ADDRESS).getText().contains("NEW TEXT"));
    }

    @Test(dependsOnMethods = { "testAddFolderDescription"})
    public void testRenameFolderDescription(){
        getDriver().findElement(By.linkText(RANDOM_PIPELINE_NAME + "NEW")).findElement(DROP_DOWN_MENU).click();
        getDriver().findElement(DROP_DOWN_RENAME).click();
        getDriver().findElement(NEW_NAME_RENAME).clear();
        getDriver().findElement(NEW_NAME_RENAME).sendKeys(RANDOM_PIPELINE_NAME + "NEW_NEW_FOLDER");
        submitButtonClick();
        getDriver().findElement(CONFIGURE_FOLDER).click();
        getDriver().findElement(TEXTAREA).sendKeys(" VERSION 2");
        submitButtonClick();

        Assert.assertTrue(getDriver().findElement(TEXT_ADDRESS).getText()
                .contains(RANDOM_PIPELINE_NAME + "NEW_NEW_FOLDER"));
        Assert.assertTrue(getDriver().findElement(TEXT_ADDRESS).getText().contains("NEW TEXT VERSION 2"));
    }

    @Test(dependsOnMethods = { "testRenameFolderDescription"})
    public void testDeleteFolder(){
        getDriver().findElement(By.linkText(RANDOM_PIPELINE_NAME + "NEW")).click();
        getDriver().findElement(DELETE_FOLDER).click();
        submitButtonClick();

        Assert.assertNotNull(getDriver().findElement(By.className("empty-state-block")));
    }

    @Test(dependsOnMethods = { "testDeleteFolder"})
    public void testCreateFolderInFolderJob(){
        createFolder();
        getDriver().findElement(CREATE_JOB).click();
        getDriver().findElement(NAME).sendKeys(RANDOM_PIPELINE_NAME);
        getDriver().findElement(FOLDER_OPTION).click();
        submitButtonClick();
        submitButtonClick();

        String actualFolderName = getDriver().findElement(By.id("breadcrumbs")).findElement(By.linkText(RANDOM_FOLDER_NAME)).getText();
        String actualPipelineName = getDriver().findElement(By.id("breadcrumbs")).findElement(By.linkText(RANDOM_PIPELINE_NAME)).getText();

        Assert.assertEquals(RANDOM_FOLDER_NAME,actualFolderName);
        Assert.assertEquals(RANDOM_PIPELINE_NAME,actualPipelineName);
    }

    @Test(dependsOnMethods = "testCreateFolderInFolderJob")
    public void testRenameFolder()  {
        getDriver().findElement(By.linkText(RANDOM_FOLDER_NAME)).findElement(DROP_DOWN_MENU).click();
        getDriver().findElement(DROP_DOWN_RENAME).click();
        getDriver().findElement(NEW_NAME_RENAME).clear();
        getDriver().findElement(NEW_NAME_RENAME).sendKeys(RANDOM_PIPELINE_NAME + "NEW");
        submitButtonClick();

        String actualFolderName = getDriver()
                .findElement(By.id("breadcrumbs"))
                .findElement(By.linkText(RANDOM_PIPELINE_NAME + "NEW"))
                .getText();

        Assert.assertEquals((RANDOM_PIPELINE_NAME + "NEW"),actualFolderName);
        Assert.assertTrue(getDriver().findElement(TEXT_ADDRESS).getText().contains(RANDOM_PIPELINE_NAME + "NEW"));
    }

    @Test(dependsOnMethods = {"testRenameFolder"})
    public void testMoveFolderInFolder(){
        createFolder();
        getDriver().findElement(MOVE_FOLDER).click();
        getDriver().findElement(By.xpath("//select/option[@value='/"+ RANDOM_PIPELINE_NAME + "NEW" + "']")).click();
        submitButtonClick();

        String actualFolderName = getDriver()
                .findElement(By.id("breadcrumbs")).findElement(By.linkText(RANDOM_FOLDER_NAME)).getText();
        String actualPipelineName = getDriver()
                .findElement(By.id("breadcrumbs")).findElement(By.linkText(RANDOM_PIPELINE_NAME + "NEW")).getText();

        Assert.assertEquals(RANDOM_FOLDER_NAME,actualFolderName);
        Assert.assertEquals((RANDOM_PIPELINE_NAME + "NEW"),actualPipelineName);
    }

    @Test(dependsOnMethods = {"testMoveFolderInFolder"})
    public void testDeleteFolderDropDown(){
        getDriver().findElement(By.linkText(RANDOM_PIPELINE_NAME + "NEW")).findElement(DROP_DOWN_MENU).click();
        getDriver().findElement(DROP_DOWN_DELETE).click();
        submitButtonClick();

        Assert.assertNotNull(getDriver().findElement(By.className("empty-state-block")));
    }

    @Test(dependsOnMethods = {"testDeleteFolderDropDown"})
    public void testCreateNewFolderWithPipeline() {
        createFolder();
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(NAME).sendKeys(RANDOM_PIPELINE_NAME);
        getDriver().findElement((PIPELINE_OPTION)).click();
        submitButtonClick();
        submitButtonClick();

        String actualFolderName = getDriver().findElement(By.linkText(RANDOM_FOLDER_NAME)).getText();
        String actualPipelineName = getDriver().findElement(By.linkText(RANDOM_PIPELINE_NAME)).getText();

        Assert.assertEquals(RANDOM_FOLDER_NAME,actualFolderName);
        Assert.assertEquals(RANDOM_PIPELINE_NAME,actualPipelineName);
        Assert.assertTrue(getDriver().findElement(TEXT_ADDRESS).getText()
                .contains(RANDOM_FOLDER_NAME + "/" + RANDOM_PIPELINE_NAME));
    }

    @Test
    public void testCreateNewFolderPipelineOptionJob() {
        createFolder();
        getDriver().findElement(CREATE_JOB).click();
        getDriver().findElement(NAME).sendKeys(RANDOM_PIPELINE_NAME);
        getDriver().findElement(PIPELINE_OPTION).click();
        submitButtonClick();
        getDriver().findElement(SELECTION_SCRIPT).click();
        submitButtonClick();

        String actualFolderName = getDriver().findElement(By.linkText(RANDOM_FOLDER_NAME)).getText();
        String actualPipelineName = getDriver().findElement(By.linkText(RANDOM_PIPELINE_NAME)).getText();

        Assert.assertEquals(RANDOM_FOLDER_NAME,actualFolderName);
        Assert.assertEquals(RANDOM_PIPELINE_NAME,actualPipelineName);
        Assert.assertTrue(getDriver().findElement(TEXT_ADDRESS).getText()
                .contains(RANDOM_FOLDER_NAME + "/" + RANDOM_PIPELINE_NAME));
    }
}

