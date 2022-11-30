import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.*;
import java.util.stream.Collectors;

import static runner.TestUtils.getRandomStr;

public class EditViewTest extends BaseTest{
    private static final String RANDOM_ALPHANUMERIC = getRandomStr();
    private static final String VIEW_PATH = String.format("//a[contains(@href, '/my-views/view/%s/')]", RANDOM_ALPHANUMERIC);
    private static final By DASHBOARD_CSS = By.cssSelector("#jenkins-name-icon");
    private static final By SUBMIT_BUTTON_CSS = By.cssSelector("[type='submit']");
    private static final By ITEM_PATH_CSS = By.cssSelector(".jenkins-table__link");
    private static final By ITEM_OPTION_CSS = By.cssSelector("input[json='true']+label");
    private static final By FILTER_QUEUE_CSS = By.cssSelector("input[name=filterQueue]+label");
    private static final By MY_VIEWS_XP = By.xpath("//a[@href='/me/my-views']");
    private static final By REGEX_CSS = By.cssSelector("input[name='useincluderegex']+label");
    private static final By INPUT_NAME_ID = By.id("name");
    private static final By STATUS_DRAG_HANDLE_XP = By.xpath("//div[@descriptorid='hudson.views.StatusColumn']//div[@class='dd-handle']");
    private static final By WEATHER_DRAG_HANDLE_XP = By.xpath("//div[@descriptorid='hudson.views.WeatherColumn']//div[@class='dd-handle']");
    private static final By DELETE_VIEW_CSS = By.cssSelector("a[href='delete']");
    private static final By ADD_COLUMN_CSS = By.cssSelector(".hetero-list-add[suffix='columns']");

    private void createItem(int i){
        final By CSS_FREESTYLE_0 = By.cssSelector(".j-item-options .hudson_model_FreeStyleProject");
        final By CSS_PIPELINE_1 = By.cssSelector(".j-item-options .org_jenkinsci_plugins_workflow_job_WorkflowJob");
        final By CSS_MULTICONFIG_2 = By.cssSelector(".j-item-options .hudson_matrix_MatrixProject");
        final By CSS_FOLDER_3 = By.cssSelector(".j-item-options .com_cloudbees_hudson_plugins_folder_Folder");
        final By CSS_MULTIBRANCH_4 = By.cssSelector(".j-item-options .org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject");
        final By CSS_ORGFOLDER_5 = By.cssSelector(".j-item-options .jenkins_branch_OrganizationFolder");
        final By[] menuOptions = {CSS_FREESTYLE_0,CSS_PIPELINE_1, CSS_MULTICONFIG_2, CSS_FOLDER_3, CSS_MULTIBRANCH_4, CSS_ORGFOLDER_5};

        getDriver().findElement(By.xpath("//a[contains(@href, '/view/all/newJob')]")).click();
        getDriver().findElement(By.cssSelector("#name.jenkins-input")).sendKeys(UUID.randomUUID().toString().substring(0, 8));
        getDriver().findElement(menuOptions[i]).click();
        getDriver().findElement(SUBMIT_BUTTON_CSS).submit();
        getDriver().findElement(DASHBOARD_CSS).click();
    }

    public void createManyItems(int i){
        for(int j = 0; j < i; j++){
            for(int k = 0; k < 6; k++) {
                createItem(k);
            }
        }
    }

    public void createGlobalView() {
        getDriver().findElement(DASHBOARD_CSS).click();
        getDriver().findElement(MY_VIEWS_XP).click();
        getDriver().findElement(By.cssSelector(".addTab")).click();
        getDriver().findElement(INPUT_NAME_ID).sendKeys(RANDOM_ALPHANUMERIC);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.ProxyView']")).click();
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();
    }

    public void createListView() {
        getDriver().findElement(DASHBOARD_CSS).click();
        getDriver().findElement(MY_VIEWS_XP).click();
        getDriver().findElement(By.cssSelector(".addTab")).click();
        getDriver().findElement(INPUT_NAME_ID).sendKeys(RANDOM_ALPHANUMERIC);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.ListView']")).click();
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();
    }

    public void createMyView() {
        getDriver().findElement(DASHBOARD_CSS).click();
        getDriver().findElement(MY_VIEWS_XP).click();
        getDriver().findElement(By.cssSelector(".addTab")).click();
        getDriver().findElement(INPUT_NAME_ID).sendKeys(RANDOM_ALPHANUMERIC);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.MyView']")).click();
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();
    }

    public void goToEditView() {
        getDriver().findElement(DASHBOARD_CSS).click();
        getDriver().findElement(MY_VIEWS_XP).click();
        getDriver().findElement(By.xpath(VIEW_PATH)).click();
        getDriver().findElement(By.xpath(String.format("//a[contains(@href, '/my-views/view/%s/configure')]", RANDOM_ALPHANUMERIC))).click();
    }

    public void globalViewSeriesPreConditions() {
        createManyItems(1);
        deleteAllViews();
        createGlobalView();
    }

    public void listViewSeriesPreConditions() {
        createManyItems(1);
        deleteAllViews();
        createListView();
    }

    public void myViewSeriesPreConditions() {
        createManyItems(1);
        deleteAllViews();
        createMyView();
    }

    public void deleteAllViews(){
        getDriver().findElement(MY_VIEWS_XP).click();
        List<WebElement> allViews = getDriver().findElements(By.xpath("//div[@class='tab']/a[contains(@href, 'my-views/view')]"));
        while (allViews.size() > 0) {
            getDriver().findElement(By.xpath("//div[@class='tab']/a[contains(@href, 'my-views/view')]")).click();
            getDriver().findElement(DELETE_VIEW_CSS).click();
            getDriver().findElement(SUBMIT_BUTTON_CSS).click();
            allViews = getDriver().findElements(By.xpath("//div[@class='tab']/a[contains(@href, 'my-views/view')]"));
        }
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

    @Test
    public void testListViewAddFiveItems() {
        listViewSeriesPreConditions();

        List<WebElement> itemsToSelect = getDriver().findElements(ITEM_OPTION_CSS);
        for (int i = 0; i < 5; i++) {
            itemsToSelect.get(i).click();
        }
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();
        int actualResult = getDriver().findElements(ITEM_PATH_CSS).size();

        Assert.assertEquals(actualResult,5);
    }

    @Test
    public void testGlobalViewAddBothFilters() {
        globalViewSeriesPreConditions();

        getDriver().findElement(FILTER_QUEUE_CSS).click();
        getDriver().findElement(By.cssSelector("input[name=filterExecutors]+label")).click();
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();
        goToEditView();
        String filterBuildQueueStatus = getDriver().findElement(
                By.cssSelector("input[name=filterQueue]")).getAttribute("checked");
        String filterBuildExecutorsStatus = getDriver().findElement(
                By.cssSelector("input[name=filterExecutors]")).getAttribute("checked");

        Assert.assertTrue(filterBuildQueueStatus.equals("true") && filterBuildExecutorsStatus.equals("true"));
    }

    @Test(dependsOnMethods = "testListViewAddFiveItems")
    public void testListViewAddNewColumn() {
        goToEditView();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        js.executeScript("arguments[0].scrollIntoView({block: 'center'})", getDriver().findElement(ADD_COLUMN_CSS));
        new Actions(getDriver()).pause(700).moveToElement(getDriver().findElement(ADD_COLUMN_CSS)).click().perform();
        getDriver().findElement(By.xpath("//a[@class='yuimenuitemlabel' and text()='Git Branches']")).click();
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();

        String expectedResult = "Git Branches";
        String actualResult = getDriver().findElement(By.cssSelector("#projectstatus th:last-child a")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testListViewAddAllItems() {
        listViewSeriesPreConditions();

        List<WebElement> itemsToSelect = getDriver().findElements(ITEM_OPTION_CSS);
        int expectedResult = itemsToSelect.size();
        itemsToSelect.forEach(WebElement::click);
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();
        int actualResult = getDriver().findElements(ITEM_PATH_CSS).size();

        Assert.assertEquals(actualResult,expectedResult);
    }

    @Test
    public void testListViewAddRegexFilter() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        createManyItems(3);
        List<WebElement> itemsToSelect = getDriver().findElements(By.cssSelector(".jenkins-table__link"));
        long expectedResult = itemsToSelect.stream().filter(element -> element.getText().contains("9")).count();
        deleteAllViews();
        createListView();

        js.executeScript("arguments[0].scrollIntoView({block: 'center'})", getDriver().findElement(REGEX_CSS));
        new Actions(getDriver()).pause(500).moveToElement(getDriver().findElement(REGEX_CSS)).click().perform();
        getDriver().findElement(By.cssSelector("input[name='includeRegex']")).sendKeys(".*9.*");
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();
        long actualResult = getDriver().findElements(By.cssSelector(".jenkins-table__link")).size();

        Assert.assertEquals(actualResult,expectedResult);
    }

    @Test(dependsOnMethods = "testListViewAddFiveItems")
    public void testListViewChangeColumnOrder() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        goToEditView();

        js.executeScript("arguments[0].scrollIntoView({block: 'center'})", getDriver().findElement(WEATHER_DRAG_HANDLE_XP));
        new Actions(getDriver()).pause(500).moveToElement(getDriver().findElement(WEATHER_DRAG_HANDLE_XP)).perform();
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(STATUS_DRAG_HANDLE_XP))
                .clickAndHold(getDriver().findElement(STATUS_DRAG_HANDLE_XP))
                .moveByOffset(0,50)
                .moveByOffset(0,50)
                .release().perform();
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();
        String[] expectedResult = {"W", "S"};
        String[] actualResult = {getDriver().findElement(By
                .cssSelector("#projectstatus th:nth-child(1) a")).getText(),getDriver().findElement(By
                .cssSelector("#projectstatus th:nth-child(2) a")).getText()};

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testListViewAddFilterBuildQueue() {
        listViewSeriesPreConditions();

        getDriver().findElement(FILTER_QUEUE_CSS).click();
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();
        boolean newPaneIsDisplayed = getDriver().findElements(By.cssSelector(".pane-header-title"))
                .stream().map(element -> element.getText()).collect(Collectors.toList())
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test
    public void testMyViewAddFilterBuildQueue() {
        myViewSeriesPreConditions();
        goToEditView();

        getDriver().findElement(FILTER_QUEUE_CSS).click();
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();
        boolean newPaneIsDisplayed = getDriver().findElements(By.cssSelector(".pane-header-title"))
                .stream().map(element -> element.getText()).collect(Collectors.toList())
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test(dependsOnMethods = "testListViewAddFiveItems")
    public void testListViewCheckEveryAddColumnItem() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        final String[] tableValues = {"S", "W", "Name", "Last Success", "Last Failure", "Last Stable", "Last Duration", "","Git Branches", "Name", "Description"};
        goToEditView();

        js.executeScript("arguments[0].scrollIntoView({block: 'center'})", getDriver().findElement(ADD_COLUMN_CSS));
        new Actions(getDriver()).pause(700).moveToElement(getDriver().findElement(ADD_COLUMN_CSS)).click().perform();
        final List<WebElement> addColumnMenuItems = getDriver().findElements(By.cssSelector("a.yuimenuitemlabel"));
        Map<String, String> tableMenuMap = new HashMap<>();
        for (int i = 0; i < addColumnMenuItems.size(); i++) {
            tableMenuMap.put(addColumnMenuItems.get(i).getText(), tableValues[i]);
        }
        List<Boolean> allMatches = new ArrayList<>(addColumnMenuItems.size());
        for (int j = 1; j <= addColumnMenuItems.size(); j++) {
            WebElement element = getDriver().findElement(By.cssSelector(String.format(".bd li[id^='yui']:nth-child(%d)", j)));
            String selectedColumnName = element.getText();
            element.click();
            getDriver().findElement(SUBMIT_BUTTON_CSS).click();
            String lastColumnName = getDriver().findElement(By.cssSelector("table#projectstatus th:last-child")).getText().replace("↓"," ").trim();
            allMatches.add(tableMenuMap.get(selectedColumnName).equals(lastColumnName));
            getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();
            js.executeScript("arguments[0].scrollIntoView({block: 'center'})", getDriver().findElement(ADD_COLUMN_CSS));
            new Actions(getDriver()).pause(700).moveToElement(getDriver().findElement(ADD_COLUMN_CSS)).perform();

            int existingColumns = getDriver().findElements(By.cssSelector(".hetero-list-container>div.repeated-chunk")).size();
            WebElement lastRow = getDriver().findElement(By.cssSelector(String.format(".hetero-list-container>div:nth-child(%d)", existingColumns)));
            lastRow.findElement(By.cssSelector("button.repeatable-delete")).click();
            js.executeScript("arguments[0].scrollIntoView({block: 'center'})", getDriver().findElement(ADD_COLUMN_CSS));
            new Actions(getDriver()).pause(700).moveToElement(getDriver().findElement(ADD_COLUMN_CSS)).click().perform();
        }
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();

        Assert.assertTrue(allMatches.stream().allMatch(element-> element == true));
    }

    public void testDeleteColumn() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        goToEditView();

        js.executeScript("arguments[0].scrollIntoView({block: 'center'})", getDriver().findElement(ADD_COLUMN_CSS));
        new Actions(getDriver()).pause(500).perform();
        getDriver().findElement(By.xpath("//div[contains(text(), 'Status')]/button")).click();
        new Actions(getDriver()).pause(300).perform();
        getDriver().findElement(SUBMIT_BUTTON_CSS).click();
        List<WebElement> columnList = getDriver().findElements(By.cssSelector("table#projectstatus th"));
        System.out.println(columnList.stream().map(element -> element.getText()).collect(Collectors.toList()));

        Assert.assertTrue(columnList.stream().noneMatch(element -> element.getText().equals("S")));
    }
}
