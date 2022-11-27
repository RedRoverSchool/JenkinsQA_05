import runner.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EditViewTest extends BaseTest {

    private WebDriverWait wait;

    private static final String RANDOM_ALPHANUMERIC = UUID.randomUUID().toString().substring(0, 8);
    private static final String VIEW_PATH = String.format("//a[contains(@href, '/my-views/view/%s/')]", RANDOM_ALPHANUMERIC);
    private static final String EDIT_VIEW_PATH = String.format("//a[contains(@href, '/my-views/view/%s/configure')]", RANDOM_ALPHANUMERIC);

    private static final By CSS_DASHBOARD = By.cssSelector("#jenkins-name-icon");
    private static final By CSS_SUBMIT_BUTTON = By.cssSelector("[type='submit']");
    private static final By CSS_ITEM_PATH = By.cssSelector(".jenkins-table__link");
    private static final By CSS_ITEM_OPTION = By.cssSelector("input[json='true']+label");
    private static final By CSS_FILTER_QUEUE = By.cssSelector("input[name=filterQueue]+label");
    private static final By CSS_FILTER_EXECUTORS = By.cssSelector("input[name=filterExecutors]+label");
    private static final By CSS_NEW_ITEM = By.cssSelector("a[href='/view/all/newJob']");
    private static final By XP_MY_VIEWS = By.xpath("//a[@href='/me/my-views']");
    private static final By CSS_REGEX = By.cssSelector("input[name='useincluderegex']+label");
    private static final By ID_INPUT_NAME = By.id("name");
    private static final By CSS_JOB = By.cssSelector(".jenkins-table__link");
    private static final By XP_STATUS_DRAG_HANDLE = By.xpath("//div[@descriptorid='hudson.views.StatusColumn']//div[@class='dd-handle']");
    private static final By XP_WEATHER_DRAG_HANDLE = By.xpath("//div[@descriptorid='hudson.views.WeatherColumn']//div[@class='dd-handle']");
    private static final By CSS_DELETE_VIEW = By.cssSelector("a[href='delete']");

    private void createItem(int i){
        final By CSS_FREESTYLE_0 = By.cssSelector(".j-item-options .hudson_model_FreeStyleProject");
        final By CSS_PIPELINE_1 = By.cssSelector(".j-item-options .org_jenkinsci_plugins_workflow_job_WorkflowJob");
        final By CSS_MULTICONFIG_2 = By.cssSelector(".j-item-options .hudson_matrix_MatrixProject");
        final By CSS_FOLDER_3 = By.cssSelector(".j-item-options .com_cloudbees_hudson_plugins_folder_Folder");
        final By CSS_MULTIBRANCH_4 = By.cssSelector(".j-item-options .org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject");
        final By CSS_ORGFOLDER_5 = By.cssSelector(".j-item-options .jenkins_branch_OrganizationFolder");
        final By[] menuOptions = {CSS_FREESTYLE_0,CSS_PIPELINE_1, CSS_MULTICONFIG_2, CSS_FOLDER_3, CSS_MULTIBRANCH_4, CSS_ORGFOLDER_5};

        getDriver().findElement(CSS_NEW_ITEM).click();
        getDriver().findElement(By.cssSelector("#name.jenkins-input")).sendKeys(UUID.randomUUID().toString().substring(0, 8));
        getDriver().findElement(menuOptions[i]).click();
        List<WebElement> errors = getDriver().findElements(By.cssSelector(".input-validation-message"));
        errors.stream().anyMatch(element -> element.isDisplayed() == true);
        if(errors.stream().anyMatch(element -> element.isDisplayed() == true)) {
            System.out.println(errors.size());
            System.out.println(errors.stream().map(WebElement::getText).collect(Collectors.toList()));
        }
        getDriver().findElement(CSS_SUBMIT_BUTTON).submit();
        getDriver().findElement(CSS_DASHBOARD).click();
    }

    public void createManyItems(int i){
        for(int j = 0; j < i; j++){
            createItem(0);
            createItem(1);
            createItem(2);
            createItem(3);
            createItem(4);
            createItem(5);
        }
    }

    public void deleteAllItems(){
        int jobList = getDriver().findElements(CSS_JOB).size();
        for(int i = 0; i  < jobList; i++) {
            getDriver().findElement(CSS_JOB).findElement(By.cssSelector(".jenkins-menu-dropdown-chevron")).click();
            WebElement menuItemDelete = getDriver().findElement(By.partialLinkText("Delete"));
            if(menuItemDelete.getText().equals("Delete Project")||
                    menuItemDelete.getText().equals("Delete Pipeline")||
                    menuItemDelete.getText().equals("Delete Multi-configuration project")) {
                menuItemDelete.click();
                getDriver().switchTo().alert().accept();
            }else {
                menuItemDelete.click();
                getDriver().findElement(By.cssSelector("button[id^='yui']")).click();
            }
        }
    }


    public String getUserName(String url) {
        return url.substring(6,url.length()-1);
    }

    public void createGlobalView() {
        getDriver().findElement(XP_MY_VIEWS).click();
        getDriver().findElement(By.cssSelector(".addTab")).click();
        getDriver().findElement(ID_INPUT_NAME).sendKeys(RANDOM_ALPHANUMERIC);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.ProxyView']")).click();
        getDriver().findElement(CSS_SUBMIT_BUTTON).click();
    }

    public void createListView() {
        getDriver().findElement(XP_MY_VIEWS).click();
        getDriver().findElement(By.cssSelector(".addTab")).click();
        getDriver().findElement(ID_INPUT_NAME).sendKeys(RANDOM_ALPHANUMERIC);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.ListView']")).click();
        getDriver().findElement(CSS_SUBMIT_BUTTON).click();
    }

    public void goToEditView() {
        getDriver().findElement(CSS_DASHBOARD).click();
        getDriver().findElement(XP_MY_VIEWS).click();
        getDriver().findElement(By.xpath(VIEW_PATH)).click();
        getDriver().findElement(By.xpath(EDIT_VIEW_PATH)).click();
    }

    public void deleteCreatedView() {
        getDriver().findElement(CSS_DASHBOARD).click();
        getDriver().findElement(XP_MY_VIEWS).click();
        getDriver().findElement(By.xpath(VIEW_PATH)).click();
        getDriver().findElement(CSS_DELETE_VIEW).click();
        getDriver().findElement(CSS_SUBMIT_BUTTON).click();
    }

    public void globalViewSeriesPreConditions() {
        createManyItems(1);
        createGlobalView();
    }

    public void listViewSeriesPreConditions() {
        createManyItems(1);
        createListView();
    }

    @Test
    public void testGlobalViewAddFilterBuildQueue() {
        globalViewSeriesPreConditions();

        getDriver().findElement(CSS_FILTER_QUEUE).click();
        getDriver().findElement(CSS_SUBMIT_BUTTON).click();
        boolean newPaneIsDisplayed = getDriver().findElements(By.cssSelector(".pane-header-title"))
                .stream().map(element -> element.getText()).collect(Collectors.toList())
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
        deleteCreatedView();
    }
}
