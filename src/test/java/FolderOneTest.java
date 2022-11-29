import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class FolderOneTest extends BaseTest {
    private static final By NEW_ITEM = By.linkText("New Item");
    private static final By NAME = By.id("name");
    private static final By SUBMIT_BUTTON = By.xpath("//button[@type = 'submit']");
    private static final By FOLDER_OPTION = By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']");
    private static final By PIPELINE_OPTION = By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']");
    private static final By JENKINS_ICON = By.id("jenkins-name-icon");
    private static final By CREATE_JOB = By.linkText("Create a job");
    private static final By TEXT_ADDRESS = By.xpath("//div[@id='main-panel']");
    private static final By SELECTION_SCRIPT = By.xpath("//div[@class='samples']/select/option[4]");
    private static final String RANDOM_FOLDER_NAME = RandomStringUtils.randomAlphanumeric(8);
    private static final String RANDOM_PIPELINE_NAME = RandomStringUtils.randomAlphanumeric(8);

    private void createFolder(){
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(NAME).sendKeys(RANDOM_FOLDER_NAME);
        getDriver().findElement(FOLDER_OPTION).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
    }

    @Test
    public void testCreateNewFolder(){
        createFolder();
        getDriver().findElement(JENKINS_ICON).click();

        String actualFolderName = getDriver().findElement(By.linkText(RANDOM_FOLDER_NAME)).getText();

        Assert.assertEquals(RANDOM_FOLDER_NAME,actualFolderName);
    }

    @Test
    public void testCreateNewFolderWithPipeline() {
        createFolder();
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(NAME).sendKeys(RANDOM_PIPELINE_NAME);
        getDriver().findElement((PIPELINE_OPTION)).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
        getDriver().findElement(SUBMIT_BUTTON).click();

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
        getDriver().findElement(SUBMIT_BUTTON).click();
        getDriver().findElement(SELECTION_SCRIPT).click();
        getDriver().findElement(SUBMIT_BUTTON).click();

        String actualFolderName = getDriver().findElement(By.linkText(RANDOM_FOLDER_NAME)).getText();
        String actualPipelineName = getDriver().findElement(By.linkText(RANDOM_PIPELINE_NAME)).getText();

        Assert.assertEquals(RANDOM_FOLDER_NAME,actualFolderName);
        Assert.assertEquals(RANDOM_PIPELINE_NAME,actualPipelineName);
        Assert.assertTrue(getDriver().findElement(TEXT_ADDRESS).getText()
                .contains(RANDOM_FOLDER_NAME + "/" + RANDOM_PIPELINE_NAME));
    }
}

