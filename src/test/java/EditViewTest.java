import runner.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.UUID;
import java.util.stream.Collectors;

public class EditViewTest extends BaseTest {
    private static final String RANDOM_ALPHANUMERIC = UUID.randomUUID().toString().substring(0, 8);
    private static final By DASHBOARD_CSS = By.cssSelector("#jenkins-name-icon");
    private static final By SUBMIT_BUTTON_CSS = By.cssSelector("[type='submit']");
    private static final By FILTER_QUEUE_CSS = By.cssSelector("input[name=filterQueue]+label");
    private static final By MY_VIEWS_XP = By.xpath("//a[@href='/me/my-views']");
    private static final By INPUT_NAME_ID = By.id("name");

    private void createItem(int i) {
        final By CSS_FREESTYLE_0 = By.cssSelector(".j-item-options .hudson_model_FreeStyleProject");//
        final By CSS_PIPELINE_1 = By.cssSelector(".j-item-options .org_jenkinsci_plugins_workflow_job_WorkflowJob");//
        final By CSS_MULTICONFIG_2 = By.cssSelector(".j-item-options .hudson_matrix_MatrixProject");//
        final By CSS_FOLDER_3 = By.cssSelector(".j-item-options .com_cloudbees_hudson_plugins_folder_Folder");//
        final By CSS_MULTIBRANCH_4 = By.cssSelector(".j-item-options .org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject");//
        final By CSS_ORGFOLDER_5 = By.cssSelector(".j-item-options .jenkins_branch_OrganizationFolder");
        final By[] menuOptions = {CSS_FREESTYLE_0, CSS_PIPELINE_1, CSS_MULTICONFIG_2, CSS_FOLDER_3, CSS_MULTIBRANCH_4, CSS_ORGFOLDER_5};

        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("#name.jenkins-input")).sendKeys(UUID.randomUUID().toString().substring(0, 8));
        getDriver().findElement(menuOptions[i]).click();
        getDriver().findElement(SUBMIT_BUTTON_CSS).submit();
        getDriver().findElement(DASHBOARD_CSS).click();
    }

    public void createManyItems(int i) {
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < 6; k++)
                createItem(k);
        }
    }

    public void createGlobalView() {
        getDriver().findElement(MY_VIEWS_XP).click();
        getDriver().findElement(By.cssSelector(".addTab")).click();
        getDriver().findElement(INPUT_NAME_ID).sendKeys(RANDOM_ALPHANUMERIC);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.ProxyView']")).click();
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();
    }

    public void globalViewSeriesPreConditions() {
        createManyItems(1);
        createGlobalView();
    }

    @Test
    public void testGlobalViewAddFilterBuildQueue() {
        globalViewSeriesPreConditions();
        getDriver().findElement(FILTER_QUEUE_CSS).click();
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();
        boolean newPaneIsDisplayed = getDriver().findElements(By.cssSelector(".pane-header-title"))
                .stream().map(WebElement::getText).collect(Collectors.toList())
                .contains("Filtered Build Queue");
        Assert.assertTrue(newPaneIsDisplayed);
    }
}
