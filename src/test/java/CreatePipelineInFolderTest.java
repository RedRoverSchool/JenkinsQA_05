import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class CreatePipelineInFolderTest extends BaseTest {

    private static final String NEW_ITEM = "New Item";
    private static final String NAME = "name";
    private static final String SUBMIT_BUTTON = "//button[@type = 'submit']";
    private static final String RANDOM_FOLDER_NAME = RandomStringUtils.randomAlphanumeric(8);
    private static final String RANDOM_PIPELINE_NAME = RandomStringUtils.randomAlphanumeric(8);

    private void xpathClick(String locator) {
        getDriver().findElement(By.xpath(locator)).click();
    }
    private void linkTextClick(String locator) { getDriver().findElement(By.linkText(locator)).click(); }
    private void idClick(String locator) { getDriver().findElement(By.id(locator)).click(); }
    private void idSendKeys(String locator1, String locator2) { getDriver().findElement(By.id(locator1)).sendKeys(locator2); }

    private String xpathGetText(String locator) {
        String text = getDriver().findElement(By.xpath(locator)).getText();
        return text;
    }

    public void deleteCreatedFolder()  {
        idClick("jenkins-name-icon");
        linkTextClick(RANDOM_FOLDER_NAME);
        linkTextClick("Delete Folder");
        idClick("yui-gen1-button");
    }

    @Test
    public void testCreateFolder() {
        linkTextClick(NEW_ITEM);
        idSendKeys(NAME, RANDOM_FOLDER_NAME);
        xpathClick("//form/div[2]/div[2]/ul/li[1]");
        xpathClick(SUBMIT_BUTTON);
        xpathClick(SUBMIT_BUTTON);
        linkTextClick(NEW_ITEM);
        idSendKeys(NAME,RANDOM_PIPELINE_NAME);
        xpathClick("//form/div[2]/div[1]/ul/li[2]");
        xpathClick(SUBMIT_BUTTON);
        xpathClick(SUBMIT_BUTTON);

        String actualFolderName = xpathGetText("//ul[@id='breadcrumbs']/li[3]");
        String actualPipelineName = xpathGetText("//ul[@id='breadcrumbs']/li[5]");

        Assert.assertEquals(RANDOM_FOLDER_NAME,actualFolderName);
        Assert.assertEquals(RANDOM_PIPELINE_NAME,actualPipelineName);

        deleteCreatedFolder();
    }
}


