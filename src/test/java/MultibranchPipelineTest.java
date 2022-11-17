import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class MultibranchPipelineTest extends BaseTest {
    private final String NEW_ITEM_XPATH = "//div [@class='task '][1]";
    private final String ENTER_AN_ITEM_NAME_XPATH = "//input[@id='name']";
    private final String MULTIBRANCH_PIPELINE_XPATH =
            "//li[@class='org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject']";
    private final String BUTTON_OK_XPATH = "//span [@class='yui-button primary large-button']";
    private final String DASHBOARD_XPATH = "//li[@class='item'][1]/a [@class='model-link']";
    private final String JENKINS_HOME_LINK_CSS = "#jenkins-home-link";



    private void buttonClickXpath(String locator) {
        getDriver().findElement(By.xpath(locator)).click();
    }

    private void buttonClickID(String locator) {
        getDriver().findElement(By.id(locator)).click();
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


    private void deleteItem(String nameOfItem) {
        getDriver().get("http://localhost:8080/job/" + nameOfItem + "/delete");
        getDriver().findElement(By.id("yui-gen1-button")).click();
    }

    @Test
    public void Create_Multibranch_Pipeline() {
        String nameOfItem = "MultibranchPipeline";
        buttonClickXpath(NEW_ITEM_XPATH);
        inputTextByXPath(ENTER_AN_ITEM_NAME_XPATH, nameOfItem);
        buttonClickXpath(MULTIBRANCH_PIPELINE_XPATH);
        buttonClickXpath(BUTTON_OK_XPATH);
        buttonClickXpath("//button [@id='yui-gen8-button']");

        urlCheck("http://localhost:8080/job/MultibranchPipeline/");
        assertTextByXPath("//ul [@id='breadcrumbs']/li[3]/a[@class='model-link']", nameOfItem);

        buttonClickXpath(DASHBOARD_XPATH);

        assertTextByXPath("//span[text()='MultibranchPipeline']", nameOfItem);

        deleteItem(nameOfItem);
    }
    @Test
    public void Create_Multibranch_Pipeline_Invalid_Name() {
        buttonClickXpath(NEW_ITEM_XPATH);
        inputTextByXPath(ENTER_AN_ITEM_NAME_XPATH, "MultibranchPipeline@");
        buttonClickXpath(MULTIBRANCH_PIPELINE_XPATH);

        Assert.assertEquals((getDriver().findElement(By.cssSelector("#itemname-invalid")).getText()),
                "» ‘@’ is an unsafe character");

        buttonClickXpath(BUTTON_OK_XPATH);

        Assert.assertEquals(getDriver().getCurrentUrl(), "http://localhost:8080/view/all/createItem");
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                "Error");
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/p")).getText(),
                "‘@’ is an unsafe character");

        getDriver().findElement(By.cssSelector(JENKINS_HOME_LINK_CSS)).click();

        getDriver().findElement(By.xpath("//div [@class='empty-state-block']")).isDisplayed();
    }





    }
