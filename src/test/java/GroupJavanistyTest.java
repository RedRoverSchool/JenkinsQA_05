import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class GroupJavanistyTest extends BaseTest {
    private final String NEW_ITEM_XPATH = "//div [@class='task '][1]";
    private final String ENTER_AN_ITEM_NAME_XPATH = "//input[@id='name']";
    private final String FREESTYLE_PROJECT_XPATH = "//li[@class='hudson_model_FreeStyleProject active']";
    private final String PIPELINE_XPATH = "//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']";
    private final String MULTI_CONFIGURATION_PROJECT_XPATH = "//li[@class='hudson_matrix_MatrixProject']";
    private final String FOLDER_XPATH = "//li[@class='com_cloudbees_hudson_plugins_folder_Folder']";
    private final String MULTIBRANCH_PIPELINE_XPATH =
            "//li[@class='org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject']";
    private final String ORGANIZATION_FOLDER_XPATH = "//li[@class='jenkins_branch_OrganizationFolder']";
    private final String BUTTON_OK_XPATH = "//span [@class='yui-button primary large-button']";
    private final String DASHBOARD_XPATH = "//li[@class='item'][1]/a [@class='model-link']";


    private void buttonClickXpath(String locator) {
        getDriver().findElement(By.xpath(locator)).click();
    }

    private void inputTextByXPath(String locator, String text) {
        getDriver().findElement(By.xpath(locator)).sendKeys(text);
    }

    private void urlCheck(String expectedUrl) {
        Assert.assertEquals(getDriver().getCurrentUrl(), expectedUrl);
    }

    private void assertTextByXPath(String locator, String text) {
        Assert.assertEquals(getDriver().findElement(By.xpath(locator)).getText(), text);
    }

    private void scrollDown(String locator) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebElement Element = getDriver().findElement(By.xpath(locator));
        js.executeScript("arguments[0].scrollIntoView();", Element);
    }

    private void threadSleep1000() throws InterruptedException {
        Thread.sleep(1000);
    }

    private void threadSleep500() throws InterruptedException {
        Thread.sleep(500);
    }

    private void deleteItem() {
        buttonClickXpath("//table[@id='projectstatus']/tbody/tr/td/a/span");
        buttonClickXpath("//div [@class='task '][5]/span");
        buttonClickXpath("//span/button [@id='yui-gen1-button']");
    }

    @Test
    public void Create_Multibranch_pipeline() {
        buttonClickXpath(NEW_ITEM_XPATH);
        inputTextByXPath(ENTER_AN_ITEM_NAME_XPATH, "MultibranchPipeline");
        buttonClickXpath(MULTIBRANCH_PIPELINE_XPATH);
        buttonClickXpath(BUTTON_OK_XPATH);
        buttonClickXpath("//button [@id='yui-gen8-button']");

        urlCheck("http://localhost:8080/job/MultibranchPipeline/");
        assertTextByXPath("//ul [@id='breadcrumbs']/li[3]/a[@class='model-link']", "MultibranchPipeline");

        buttonClickXpath(DASHBOARD_XPATH);

        assertTextByXPath("//span[text()='MultibranchPipeline']", "MultibranchPipeline");

        deleteItem();
    }


}
