import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class FolderTest_1 extends BaseTest {

    private static final String NEW_ITEM = "New Item";
    private static final String NAME = "name";
    private static final String SUBMIT_BUTTON = "//button[@type = 'submit']";
    private static final String FOLDER_LOCATION = "//ul[@id='breadcrumbs']/li[3]";
    private static final String PIPELINE_LOCATION = "//ul[@id='breadcrumbs']/li[5]";
    private static final String FOLDER_OPTION = "//form/div[2]/div[2]/ul/li[1]";
    private static final String PIPELINE_OPTION = "//form/div[2]/div[1]/ul/li[2]";
    private static final String JENKINS_ICON = "jenkins-name-icon";
    private static final String CREATE_JOB = "Create a job";
    private static final String DELETE_FOLDER = "Delete Folder";
    private static final String YES_BUTTON_DELETE_PR = "yui-gen1-button";
    private static final String SELECTION_SCRIPT = "//div[@class='samples']/select/option[4]";
    private static final String RANDOM_FOLDER_NAME = RandomStringUtils.randomAlphanumeric(8);
    private static final String RANDOM_PIPELINE_NAME = RandomStringUtils.randomAlphanumeric(8);

    public void deleteCreatedFolder()  {
        getDriver().findElement(By.id(JENKINS_ICON)).click();
        getDriver().findElement(By.linkText(RANDOM_FOLDER_NAME)).click();
        getDriver().findElement(By.linkText(DELETE_FOLDER)).click();
        getDriver().findElement(By.id(YES_BUTTON_DELETE_PR)).click();
    }

    @Test
    public void testCreateFolderWithPipeline() {
        getDriver().findElement(By.linkText(NEW_ITEM)).click();
        getDriver().findElement(By.id(NAME)).sendKeys(RANDOM_FOLDER_NAME);
        getDriver().findElement(By.xpath(FOLDER_OPTION)).click();
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();
        getDriver().findElement(By.linkText(NEW_ITEM)).click();
        getDriver().findElement(By.id(NAME)).sendKeys(RANDOM_PIPELINE_NAME);
        getDriver().findElement(By.xpath(PIPELINE_OPTION)).click();
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();

        String actualFolderName = getDriver().findElement(By.xpath(FOLDER_LOCATION)).getText();
        String actualPipelineName = getDriver().findElement(By.xpath(PIPELINE_LOCATION)).getText();

        Assert.assertEquals(RANDOM_FOLDER_NAME,actualFolderName);
        Assert.assertEquals(RANDOM_PIPELINE_NAME,actualPipelineName);

        deleteCreatedFolder();
    }

    @Test
    public void testCreateFolderPipelineOptionJob() {
        getDriver().findElement(By.linkText(NEW_ITEM)).click();
        getDriver().findElement(By.id(NAME)).sendKeys(RANDOM_FOLDER_NAME);
        getDriver().findElement(By.xpath(FOLDER_OPTION)).click();
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();
        getDriver().findElement(By.linkText(CREATE_JOB)).click();
        getDriver().findElement(By.id(NAME)).sendKeys(RANDOM_PIPELINE_NAME);
        getDriver().findElement(By.xpath(PIPELINE_OPTION)).click();
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();
        getDriver().findElement(By.xpath(SELECTION_SCRIPT)).click();
        getDriver().findElement(By.xpath(SUBMIT_BUTTON)).click();

        String actualFolderName = getDriver().findElement(By.xpath(FOLDER_LOCATION)).getText();
        String actualPipelineName = getDriver().findElement(By.xpath(PIPELINE_LOCATION)).getText();

        Assert.assertEquals(RANDOM_FOLDER_NAME,actualFolderName);
        Assert.assertEquals(RANDOM_PIPELINE_NAME,actualPipelineName);

        deleteCreatedFolder();
    }
}


