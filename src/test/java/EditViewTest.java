import model.HomePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import runner.BaseTest;
import runner.TestUtils;

import java.util.*;
import java.util.stream.Collectors;
import static runner.TestUtils.*;

public class EditViewTest extends BaseTest{
    private static String localViewNameVar;
    private static final int waitTime = 5;
    private static final By DASHBOARD = By.cssSelector("#jenkins-name-icon");
    private static final By SUBMIT_BUTTON = By.cssSelector("[type='submit']");
    private static final By JOB_PATH = By.cssSelector(".jenkins-table__link");
    private static final By ITEM_OPTION = By.cssSelector("input[json='true']+label");
    private static final By FILTER_QUEUE = By.cssSelector("input[name=filterQueue]+label");
    private static final By MY_VIEWS = By.xpath("//a[@href='/me/my-views']");
    private static final By REGEX_FIELD = By.cssSelector("input[name='useincluderegex']+label");
    private static final By INPUT_NAME = By.cssSelector("[name='name']");
    private static final By PANE_HEADER = By.cssSelector(".pane-header-title");
    private static final By STATUS_DRAG_HANDLE = By
            .xpath("//div[@descriptorid='hudson.views.StatusColumn']//div[@class='dd-handle']");
    private static final By ADD_COLUMN = By.cssSelector(".hetero-list-add[suffix='columns']");
    private static final By LAST_EXISTING_COLUMN = By
            .xpath("//div[contains(@class, 'hetero-list-container')]/div[@class='repeated-chunk'][last()]");

    final By FREESTYLE_0 = By.cssSelector(".j-item-options .hudson_model_FreeStyleProject");
    final By PIPELINE_1 = By.cssSelector(".j-item-options .org_jenkinsci_plugins_workflow_job_WorkflowJob");
    final By MULTICONFIG_2 = By.cssSelector(".j-item-options .hudson_matrix_MatrixProject");
    final By FOLDER_3 = By.cssSelector(".j-item-options .com_cloudbees_hudson_plugins_folder_Folder");
    final By MULTIBRANCH_4 = By
            .cssSelector(".j-item-options .org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject");
    final By ORGFOLDER_5 = By.cssSelector(".j-item-options .jenkins_branch_OrganizationFolder");
    final By[] listAllJobTypes = {FREESTYLE_0,PIPELINE_1, MULTICONFIG_2,FOLDER_3, MULTIBRANCH_4, ORGFOLDER_5};

    final By GLOBAL_VIEW_0 = By.xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.ProxyView']");
    final By LIST_VIEW_1 = By.xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.ListView']");
    final By GLOBAL_VIEW_2 = By.xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.MyView']");
    final By[] listAllViewTypes = {GLOBAL_VIEW_0, LIST_VIEW_1, GLOBAL_VIEW_2};


    private void createOneItemFromListOfJobTypes(int indexOfJob){
        getDriver().findElement(By.xpath("//a[contains(@href, '/view/all/newJob')]")).click();
        getDriver().findElement(By.cssSelector("#name.jenkins-input")).sendKeys(getRandomStr());
        getDriver().findElement(listAllJobTypes[indexOfJob]).click();
        getDriver().findElement(SUBMIT_BUTTON).submit();
        getDriver().findElement(DASHBOARD).click();
    }

    private void createManyJobsOfEachType(int numberOfJobsOfEachType){
        for(int i = 0; i < numberOfJobsOfEachType; i++){
            for(int j = 0; j < listAllJobTypes.length; j++) {
                createOneItemFromListOfJobTypes(j);
            }
        }
    }

    private void createViewFromListOfViewTypes(int indexOfView, String viewName) {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(By.cssSelector(".addTab")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(viewName);
        getDriver().findElement(listAllViewTypes[indexOfView]).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
    }

    private void goToEditView(String viewName) {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(By
                .xpath(String.format("//a[contains(@href, '/my-views/view/%s/')]", viewName))).click();
        getDriver().findElement(By
                .xpath(String.format("//a[contains(@href, '/my-views/view/%s/configure')]", viewName))).click();
    }

    private void editViewTestPreConditions(int indexOfView, String viewName) {
        createManyJobsOfEachType(1);
        createViewFromListOfViewTypes(indexOfView, viewName);
    }

    private void listViewSeriesPreConditions(int indexOfView, String viewName) {
        editViewTestPreConditions(indexOfView, viewName);
        addFiveItemsToListView();
        goToEditView(viewName);
    }
    private void scrollWaitTillNotMovingAndClick(int duration, By locator) {
        scrollToElement_PlaceInCenter(getDriver(), getDriver().findElement(locator));
        getWait(duration).until(TestUtils.ExpectedConditions.elementIsNotMoving(locator));
        getDriver().findElement(locator).click();
    }

    private void addFiveItemsToListView() {
        List<WebElement> itemsToSelect = getDriver().findElements(ITEM_OPTION);
        for (int i = 0; i < 5; i++) {
            itemsToSelect.get(i).click();
        }
        getDriver().findElement(SUBMIT_BUTTON).click();
    }

    private void dragByYOffset (By locator, int offset) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(locator))
                .clickAndHold(getDriver().findElement(locator))
                .moveByOffset(0,offset/2)
                .moveByOffset(0,offset/2)
                .release().perform();
    }
    @Ignore
    @Test
    public void testGlobalViewAddFilterBuildQueue() {
        boolean newPaneIsDisplayed = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(getRandomStr())
                .selectFolderAndClickOk()
                .clickDashboard()
                .clickMyViews()
                .clickAddViewLink()
                .setViewName(getRandomStr())
                .setGlobalViewType()
                .clickCreateButton()
                .filterBuildQueueOptionCheckBoxSelect()
                .clickOkButton()
                .getActiveFiltersList()
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test
    public void testListViewAddFiveItems() {
        localViewNameVar = getRandomStr();
        createManyJobsOfEachType(1);
        createViewFromListOfViewTypes(1, localViewNameVar);
        addFiveItemsToListView();

        int actualResult = getDriver().findElements(JOB_PATH).size();
        Assert.assertEquals(actualResult,5);
    }

    @Test
    public void testGlobalViewAddBothFilters() {
        localViewNameVar = getRandomStr();
        editViewTestPreConditions(0, localViewNameVar);

        getDriver().findElement(FILTER_QUEUE).click();
        getDriver().findElement(By.cssSelector("input[name=filterExecutors]+label")).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
        goToEditView(localViewNameVar);

        String filterBuildQueueStatus = getDriver().findElement(
                By.cssSelector("input[name=filterQueue]")).getAttribute("checked");
        String filterBuildExecutorsStatus = getDriver().findElement(
                By.cssSelector("input[name=filterExecutors]")).getAttribute("checked");
        Assert.assertTrue(filterBuildQueueStatus.equals("true") && filterBuildExecutorsStatus.equals("true"));
    }

    @Test
    public void testListViewAddNewColumn() {
        listViewSeriesPreConditions(1, getRandomStr());
        String expectedResult = "Git Branches";

        scrollWaitTillNotMovingAndClick(waitTime, ADD_COLUMN);
        getDriver().findElement(By.
                xpath("//a[@class='yuimenuitemlabel' and text()='Git Branches']")).click();
        getDriver().findElement(SUBMIT_BUTTON).click();

        String actualResult = getDriver().findElement(By.cssSelector("#projectstatus th:last-child a")).getText();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testListViewAddAllItems() {
        createManyJobsOfEachType(1);
        localViewNameVar = getRandomStr();
        createViewFromListOfViewTypes(1, localViewNameVar);
        goToEditView(localViewNameVar);

        List<WebElement> itemsToSelect = getDriver().findElements(ITEM_OPTION);
        int expectedResult = itemsToSelect.size();
        itemsToSelect.forEach(WebElement::click);
        getDriver().findElement(SUBMIT_BUTTON).click();

        int actualResult = getDriver().findElements(JOB_PATH).size();
        Assert.assertEquals(actualResult,expectedResult);
    }

    @Test
    public void testListViewAddRegexFilter() {
        createManyJobsOfEachType(2);
        List<WebElement> itemsToSelect = getDriver().findElements(JOB_PATH);
        long expectedResult = itemsToSelect.stream().filter(element -> element.getText().contains("9")).count();
        createViewFromListOfViewTypes(1, getRandomStr());

        //TestUtils.scrollToElement(getDriver(), getDriver().findElement(By.id("yui-gen1-button")));
        //TestUtils.scrollToElement(getDriver(), getDriver().findElement(By.xpath("//div[text()='Jobs']")));
        TestUtils.scrollToEnd(getDriver());
        getWait(10).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='useincluderegex']")));
        getDriver().findElement(REGEX_FIELD).click();

        getDriver().findElement(By.cssSelector("input[name='includeRegex']")).sendKeys(".*9.*");
        getDriver().findElement(SUBMIT_BUTTON).click();

        long actualResult = getDriver().findElements(JOB_PATH).size();
        Assert.assertEquals(actualResult,expectedResult);
    }

    @Test
    public void testListViewChangeColumnOrder() {
        localViewNameVar = getRandomStr();
        listViewSeriesPreConditions(1, localViewNameVar);
        String[] expectedResult = {"W", "S"};

        scrollWaitTillNotMovingAndClick(waitTime, STATUS_DRAG_HANDLE);
        dragByYOffset(STATUS_DRAG_HANDLE, 100);
        getDriver().findElement(SUBMIT_BUTTON).click();

        String[] actualResult = {getDriver().findElement(By
                .cssSelector("#projectstatus th:nth-child(1) a")).getText(),getDriver().findElement(By
                .cssSelector("#projectstatus th:nth-child(2) a")).getText()};
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testListViewAddFilterBuildQueue() {
        createManyJobsOfEachType(1);
        createViewFromListOfViewTypes(1, getRandomStr());

        getDriver().findElement(FILTER_QUEUE).click();
        getDriver().findElement(SUBMIT_BUTTON).click();

        boolean newPaneIsDisplayed = getDriver().findElements(PANE_HEADER)
                .stream().map(element -> element.getText()).collect(Collectors.toList())
                .contains("Filtered Build Queue");
        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test
    public void testMyViewAddFilterBuildQueue() {
        localViewNameVar = getRandomStr();
        editViewTestPreConditions(2, localViewNameVar);
        goToEditView(localViewNameVar);

        getDriver().findElement(FILTER_QUEUE).click();
        getDriver().findElement(SUBMIT_BUTTON).click();

        boolean newPaneIsDisplayed = getDriver().findElements(PANE_HEADER)
                .stream().map(element -> element.getText()).collect(Collectors.toList())
                .contains("Filtered Build Queue");
        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test
    public void testListViewCheckEveryAddColumnItem() {
        localViewNameVar = getRandomStr();
        listViewSeriesPreConditions(1, localViewNameVar);
        final String[] tableValues = {
                "S", "W", "Name", "Last Success", "Last Failure", "Last Stable",
                "Last Duration", "","Git Branches", "Name", "Description"};

        scrollWaitTillNotMovingAndClick(waitTime, ADD_COLUMN);
        final List<WebElement> addColumnMenuItems = getDriver().findElements(By.cssSelector("a.yuimenuitemlabel"));
        Map<String, String> tableMenuMap = new HashMap<>();
        for (int i = 0; i < addColumnMenuItems.size(); i++) {
            tableMenuMap.put(addColumnMenuItems.get(i).getText(), tableValues[i]);
        }
        List<Boolean> allMatches = new ArrayList<>(addColumnMenuItems.size());
        for (int j = 1; j <= addColumnMenuItems.size(); j++) {
            WebElement element = getDriver().findElement(By
                    .cssSelector(String.format(".bd li[id^='yui']:nth-child(%d)", j)));
            String selectedColumnName = element.getText();
            element.click();
            getDriver().findElement(SUBMIT_BUTTON).click();
            String lastColumnName = getDriver().findElement(By
                    .cssSelector("table#projectstatus th:last-child")).getText().replace("↓"," ").trim();
            allMatches.add(tableMenuMap.get(selectedColumnName).equals(lastColumnName));
            getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();
            scrollWaitTillNotMovingAndClick(waitTime,LAST_EXISTING_COLUMN);
            getDriver().findElement(LAST_EXISTING_COLUMN).findElement(By.cssSelector("button.repeatable-delete")).click();
            scrollWaitTillNotMovingAndClick(waitTime, ADD_COLUMN);
        }
        getDriver().findElement(SUBMIT_BUTTON).click();

        Assert.assertTrue(allMatches.stream().allMatch(element-> element == true));
    }

    @Test
    public void testDeleteColumn() {
        localViewNameVar = getRandomStr();
        listViewSeriesPreConditions(1, localViewNameVar);

        scrollToElement_PlaceInCenter(getDriver(), getDriver().findElement(ADD_COLUMN));
        getWait(waitTime).until(TestUtils.ExpectedConditions.elementIsNotMoving(ADD_COLUMN));
        getDriver().findElement(By
                .cssSelector("div[descriptorid='hudson.views.StatusColumn'] button.repeatable-delete")).click();
        new Actions(getDriver()).pause(300).perform();
        getDriver().findElement(SUBMIT_BUTTON).click();

        List<WebElement> columnList = getDriver().findElements(By.cssSelector("table#projectstatus th"));
        Assert.assertTrue(columnList.stream().noneMatch(element -> element.getText().equals("S")));
    }

    @Test
    public void testMultipleSpacesRenameView() {
        localViewNameVar = getRandomStr();
        listViewSeriesPreConditions(1, localViewNameVar);
        final String nonSpaces = getRandomStr(5);
        final String spaces = nonSpaces.replaceAll("[a-zA-Z0-9]", " ");
        final String newName = nonSpaces + spaces + nonSpaces;

        getDriver().findElement(INPUT_NAME).clear();
        getDriver().findElement(INPUT_NAME).sendKeys(newName);
        getDriver().findElement(SUBMIT_BUTTON).click();

        String actualResult = getDriver().findElement(By.cssSelector(".tab.active")).getText();
        Assert.assertNotEquals(actualResult, localViewNameVar);
        Assert.assertEquals(actualResult, (nonSpaces + " " + nonSpaces));
    }

    @Test
    public void testIllegalCharacterRenameView() {
        localViewNameVar = getRandomStr();
        listViewSeriesPreConditions(1, localViewNameVar);
        final char[] illegalCharacters = "#!@$%^&*:;<>?/[]|\\".toCharArray();

        List<Boolean> checks = new ArrayList<>();
        for (int i = 0; i < illegalCharacters.length; i++) {
            getDriver().findElement(INPUT_NAME).clear();
            getDriver().findElement(INPUT_NAME).sendKeys(illegalCharacters[i] + localViewNameVar);
            getDriver().findElement(SUBMIT_BUTTON).click();
            if(getDriver().findElements(By.cssSelector("#main-panel h1")).size() > 0) {
                checks.add(String.format("‘%c’ is an unsafe character", illegalCharacters[i])
                        .equals(getDriver().findElement(By.cssSelector("#main-panel p")).getText()));
                getDriver().findElement(DASHBOARD).click();
                getDriver().findElement(MY_VIEWS).click();
                int finalI = i;
                checks.add(getDriver()
                        .findElements(By
                        .xpath(String.format("//a[contains(@href, '/my-views/view/%s/')]", localViewNameVar)))
                        .stream().noneMatch(element -> element.getText()
                        .equals(String.format("‘%c’ is an unsafe character", illegalCharacters[finalI]))));
                goToEditView(localViewNameVar);
            } else {
                checks.add(false);
            }
        }

        Assert.assertTrue(checks.stream().allMatch(element->element == true));
    }

    @Test
    public void testRenameView() {
        localViewNameVar = getRandomStr();
        listViewSeriesPreConditions(1, localViewNameVar);
        final String newName = getRandomStr();

        getDriver().findElement(INPUT_NAME).clear();
        getDriver().findElement(INPUT_NAME).sendKeys(newName);
        getDriver().findElement(SUBMIT_BUTTON).click();

        String actualResult = getDriver().findElement(By.cssSelector(".tab.active")).getText();
        Assert.assertFalse(actualResult.equals(localViewNameVar));
        Assert.assertEquals(actualResult, newName);
    }
}
