import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import runner.BaseTest;
import runner.TestUtils;
import java.util.*;
import java.util.stream.Collectors;
import static runner.TestUtils.getRandomStr;

public class EditViewTest extends BaseTest{
    private static String localRandomAlphaNumeric = "";
    private static final int WAIT_TIME = 5;
    private static final By DASHBOARD = By.cssSelector("#jenkins-name-icon");
    private static final By SUBMIT_BUTTON = By.cssSelector("[type='submit']");
    private static final By ITEM_PATH = By.cssSelector(".jenkins-table__link");
    private static final By ITEM_OPTION = By.cssSelector("input[json='true']+label");
    private static final By FILTER_QUEUE = By.cssSelector("input[name=filterQueue]+label");
    private static final By MY_VIEWS = By.xpath("//a[@href='/me/my-views']");
    private static final By REGEX = By.cssSelector("input[name='useincluderegex']+label");
    private static final By INPUT_NAME = By.cssSelector("[name='name']");
    private static final By PANE_HEADER = By.cssSelector(".pane-header-title");
    private static final By STATUS_DRAG_HANDLE = By
            .xpath("//div[@descriptorid='hudson.views.StatusColumn']//div[@class='dd-handle']");
    private static final By ADD_COLUMN = By.cssSelector(".hetero-list-add[suffix='columns']");
    private static final By LAST_EXISTING_COLUMN = By
            .xpath("//div[contains(@class, 'hetero-list-container')]/div[@class='repeated-chunk'][last()]");

    private void createItem(int i){
        final By FREESTYLE_0 = By.cssSelector(".j-item-options .hudson_model_FreeStyleProject");
        final By PIPELINE_1 = By.cssSelector(".j-item-options .org_jenkinsci_plugins_workflow_job_WorkflowJob");
        final By MULTICONFIG_2 = By.cssSelector(".j-item-options .hudson_matrix_MatrixProject");
        final By FOLDER_3 = By.cssSelector(".j-item-options .com_cloudbees_hudson_plugins_folder_Folder");
        final By MULTIBRANCH_4 = By
                .cssSelector(".j-item-options .org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject");
        final By ORGFOLDER_5 = By.cssSelector(".j-item-options .jenkins_branch_OrganizationFolder");
        final By[] MENU_OPTIONS = {FREESTYLE_0,PIPELINE_1, MULTICONFIG_2,FOLDER_3, MULTIBRANCH_4, ORGFOLDER_5};

        getDriver().findElement(By.xpath("//a[contains(@href, '/view/all/newJob')]")).click();
        getDriver().findElement(By.cssSelector("#name.jenkins-input")).sendKeys(getRandomStr());
        getDriver().findElement(MENU_OPTIONS[i]).click();
        getDriver().findElement(SUBMIT_BUTTON).submit();
        getDriver().findElement(DASHBOARD).click();
    }

    public void createManyItemsOfEach(int i){
        for(int j = 0; j < i; j++){
            for(int k = 0; k < 6; k++) {
                createItem(k);
            }
        }
    }

    public void createGlobalView(String randomAlphaNumeric) {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(By.cssSelector(".addTab")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(randomAlphaNumeric);
        getDriver().findElement(By
                .xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.ProxyView']")).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
    }

    public void createListView(String randomAlphaNumeric) {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(By.cssSelector(".addTab")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(randomAlphaNumeric);
        getDriver().findElement(By
                .xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.ListView']")).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
    }

    public void createMyView(String randomAlphaNumeric) {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(By.cssSelector(".addTab")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(randomAlphaNumeric);
        getDriver().findElement(By
                .xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.MyView']")).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
    }

    public void goToEditView(String randomAlphaNumeric) {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(MY_VIEWS).click();
        getDriver().findElement(By
                .xpath(String.format("//a[contains(@href, '/my-views/view/%s/')]", randomAlphaNumeric))).click();
        getDriver().findElement(By
                .xpath(String.format("//a[contains(@href, '/my-views/view/%s/configure')]", randomAlphaNumeric))).click();
    }

    public void globalViewSeriesPreConditions(String randomAlphaNumeric) {
        createManyItemsOfEach(1);
        createGlobalView(randomAlphaNumeric);
    }

    public void listViewSeriesPreConditions(String randomAlphaNumeric) {
        createManyItemsOfEach(1);
        createListView(randomAlphaNumeric);
        addFiveItemsToListView();
        goToEditView(randomAlphaNumeric);
    }

    public void myViewSeriesPreConditions(String randomAlphaNumeric) {
        createManyItemsOfEach(1);
        createMyView(randomAlphaNumeric);
    }

    public void scrollWaitTillNotMovingAndClick(int duration, By locator) {
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].scrollIntoView({block: 'center'})", getDriver().findElement(locator));
        getWait(duration).until(TestUtils.ExpectedConditions.elementIsNotMoving(locator));
        getDriver().findElement(locator).click();
    }

    public void addFiveItemsToListView() {
        List<WebElement> itemsToSelect = getDriver().findElements(ITEM_OPTION);
        for (int i = 0; i < 5; i++) {
            itemsToSelect.get(i).click();
        }
        getDriver().findElement(SUBMIT_BUTTON).click();
    }

    public void dragByYOffset (By locator, int offset) {
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
        globalViewSeriesPreConditions(getRandomStr());

        getDriver().findElement(FILTER_QUEUE).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
        boolean newPaneIsDisplayed = getDriver().findElements(PANE_HEADER)
                .stream().map(WebElement::getText).collect(Collectors.toList())
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test
    public void testListViewAddFiveItems() {
        localRandomAlphaNumeric = getRandomStr();
        createManyItemsOfEach(1);
        createListView(localRandomAlphaNumeric);
        addFiveItemsToListView();

        int actualResult = getDriver().findElements(ITEM_PATH).size();

        Assert.assertEquals(actualResult,5);
    }

    @Test
    public void testGlobalViewAddBothFilters() {
        localRandomAlphaNumeric = getRandomStr();
        globalViewSeriesPreConditions(localRandomAlphaNumeric);

        getDriver().findElement(FILTER_QUEUE).click();
        getDriver().findElement(By.cssSelector("input[name=filterExecutors]+label")).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
        goToEditView(localRandomAlphaNumeric);
        String filterBuildQueueStatus = getDriver().findElement(
                By.cssSelector("input[name=filterQueue]")).getAttribute("checked");
        String filterBuildExecutorsStatus = getDriver().findElement(
                By.cssSelector("input[name=filterExecutors]")).getAttribute("checked");

        Assert.assertTrue(filterBuildQueueStatus.equals("true") && filterBuildExecutorsStatus.equals("true"));
    }

    @Test
    public void testListViewAddNewColumn() {
        listViewSeriesPreConditions(getRandomStr());

        scrollWaitTillNotMovingAndClick(WAIT_TIME, ADD_COLUMN);
        getDriver().findElement(By.
                xpath("//a[@class='yuimenuitemlabel' and text()='Git Branches']")).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
        String expectedResult = "Git Branches";
        String actualResult = getDriver().findElement(By.cssSelector("#projectstatus th:last-child a")).getText();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Ignore
    @Test
    public void testListViewAddAllItems() {
        createManyItemsOfEach(1);
        localRandomAlphaNumeric = getRandomStr();
        createListView(localRandomAlphaNumeric);
        goToEditView(localRandomAlphaNumeric);

        List<WebElement> itemsToSelect = getDriver().findElements(ITEM_OPTION);
        int expectedResult = itemsToSelect.size();
        itemsToSelect.forEach(WebElement::click);
        getDriver().findElement(SUBMIT_BUTTON).click();
        int actualResult = getDriver().findElements(ITEM_PATH).size();

        Assert.assertEquals(actualResult,expectedResult);
    }

    @Test
    public void testListViewAddRegexFilter() {
        createManyItemsOfEach(2);
        List<WebElement> itemsToSelect = getDriver().findElements(ITEM_PATH);
        long expectedResult = itemsToSelect.stream().filter(element -> element.getText().contains("9")).count();
        createListView(getRandomStr());

        scrollWaitTillNotMovingAndClick(WAIT_TIME, REGEX);
        getDriver().findElement(By.cssSelector("input[name='includeRegex']")).sendKeys(".*9.*");
        getDriver().findElement(SUBMIT_BUTTON).click();
        long actualResult = getDriver().findElements(ITEM_PATH).size();

        Assert.assertEquals(actualResult,expectedResult);
    }

    @Test
    public void testListViewChangeColumnOrder() {
        localRandomAlphaNumeric = getRandomStr();
        listViewSeriesPreConditions(localRandomAlphaNumeric);

        scrollWaitTillNotMovingAndClick(WAIT_TIME, STATUS_DRAG_HANDLE);
        dragByYOffset(STATUS_DRAG_HANDLE, 100);
        getDriver().findElement(SUBMIT_BUTTON).click();
        String[] expectedResult = {"W", "S"};
        String[] actualResult = {getDriver().findElement(By
                .cssSelector("#projectstatus th:nth-child(1) a")).getText(),getDriver().findElement(By
                .cssSelector("#projectstatus th:nth-child(2) a")).getText()};

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testListViewAddFilterBuildQueue() {
        createManyItemsOfEach(1);
        createListView(getRandomStr());

        getDriver().findElement(FILTER_QUEUE).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
        boolean newPaneIsDisplayed = getDriver().findElements(PANE_HEADER)
                .stream().map(element -> element.getText()).collect(Collectors.toList())
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test
    public void testMyViewAddFilterBuildQueue() {
        localRandomAlphaNumeric = getRandomStr();
        myViewSeriesPreConditions(localRandomAlphaNumeric);
        goToEditView(localRandomAlphaNumeric);

        getDriver().findElement(FILTER_QUEUE).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
        boolean newPaneIsDisplayed = getDriver().findElements(PANE_HEADER)
                .stream().map(element -> element.getText()).collect(Collectors.toList())
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test
    public void testListViewCheckEveryAddColumnItem() {
        localRandomAlphaNumeric = getRandomStr();
        listViewSeriesPreConditions(localRandomAlphaNumeric);
        final String[] TABLE_VALUES = {
                "S", "W", "Name", "Last Success", "Last Failure", "Last Stable",
                "Last Duration", "","Git Branches", "Name", "Description"};

        scrollWaitTillNotMovingAndClick(WAIT_TIME, ADD_COLUMN);
        final List<WebElement> addColumnMenuItems = getDriver().findElements(By.cssSelector("a.yuimenuitemlabel"));
        Map<String, String> tableMenuMap = new HashMap<>();
        for (int i = 0; i < addColumnMenuItems.size(); i++) {
            tableMenuMap.put(addColumnMenuItems.get(i).getText(), TABLE_VALUES[i]);
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
            scrollWaitTillNotMovingAndClick(WAIT_TIME,LAST_EXISTING_COLUMN);
            getDriver().findElement(LAST_EXISTING_COLUMN).findElement(By.cssSelector("button.repeatable-delete")).click();
            scrollWaitTillNotMovingAndClick(WAIT_TIME, ADD_COLUMN);
        }
        getDriver().findElement(SUBMIT_BUTTON).click();

        Assert.assertTrue(allMatches.stream().allMatch(element-> element == true));
    }

    @Test
    public void testDeleteColumn() {
        localRandomAlphaNumeric = getRandomStr();
        listViewSeriesPreConditions(localRandomAlphaNumeric);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        js.executeScript("arguments[0].scrollIntoView({block: 'center'})", getDriver().findElement(ADD_COLUMN));
        getWait(WAIT_TIME).until(TestUtils.ExpectedConditions.elementIsNotMoving(ADD_COLUMN));
        getDriver().findElement(By
                .cssSelector("div[descriptorid='hudson.views.StatusColumn'] button.repeatable-delete")).click();
        new Actions(getDriver()).pause(300).perform();
        getDriver().findElement(SUBMIT_BUTTON).click();
        List<WebElement> columnList = getDriver().findElements(By.cssSelector("table#projectstatus th"));

        Assert.assertTrue(columnList.stream().noneMatch(element -> element.getText().equals("S")));
    }

    @Test
    public void testMultipleSpacesRenameView() {
        localRandomAlphaNumeric = getRandomStr();
        listViewSeriesPreConditions(localRandomAlphaNumeric);
        final String nonSpaces = getRandomStr((3 +(int)(Math.random() * (10 - 3) + 1)));
        final String spaces = nonSpaces.replaceAll("[a-zA-Z0-9]", " ");
        final String NEW_NAME = nonSpaces + spaces + nonSpaces;

        getDriver().findElement(INPUT_NAME).clear();
        getDriver().findElement(INPUT_NAME).sendKeys(NEW_NAME);
        getDriver().findElement(SUBMIT_BUTTON).click();
        String actualResult = getDriver().findElement(By.cssSelector(".tab.active")).getText();

        Assert.assertNotEquals(actualResult, localRandomAlphaNumeric);
        Assert.assertEquals(actualResult, (nonSpaces + " " + nonSpaces));
    }

    @Test
    public void testIllegalCharacterRenameView() {
        localRandomAlphaNumeric = getRandomStr();
        listViewSeriesPreConditions(localRandomAlphaNumeric);
        final char[] ILLEGAL_CHARACTERS = "#!@$%^&*:;<>?/[]|\\".toCharArray();

        List<Boolean> checks = new ArrayList<>();
        for (int i = 0; i < ILLEGAL_CHARACTERS.length; i++) {
            getDriver().findElement(INPUT_NAME).clear();
            getDriver().findElement(INPUT_NAME).sendKeys(ILLEGAL_CHARACTERS[i] + localRandomAlphaNumeric);
            getDriver().findElement(SUBMIT_BUTTON).click();
            if(getDriver().findElements(By.cssSelector("#main-panel h1")).size() > 0) {
                checks.add(String.format("‘%c’ is an unsafe character", ILLEGAL_CHARACTERS[i])
                        .equals(getDriver().findElement(By.cssSelector("#main-panel p")).getText()));
                getDriver().findElement(DASHBOARD).click();
                getDriver().findElement(MY_VIEWS).click();
                int finalI = i;
                checks.add(getDriver()
                        .findElements(By
                        .xpath(String.format("//a[contains(@href, '/my-views/view/%s/')]", localRandomAlphaNumeric)))
                        .stream().noneMatch(element -> element.getText()
                        .equals(String.format("‘%c’ is an unsafe character", ILLEGAL_CHARACTERS[finalI]))));
                goToEditView(localRandomAlphaNumeric);
            } else {
                checks.add(false);
            }
        }

        Assert.assertTrue(checks.stream().allMatch(element->element == true));
    }

    @Test
    public void testRenameView() {
        localRandomAlphaNumeric = getRandomStr();
        listViewSeriesPreConditions(localRandomAlphaNumeric);
        final String NEW_NAME = getRandomStr();

        getDriver().findElement(INPUT_NAME).clear();
        getDriver().findElement(INPUT_NAME).sendKeys(NEW_NAME);
        getDriver().findElement(SUBMIT_BUTTON).click();
        String actualResult = getDriver().findElement(By.cssSelector(".tab.active")).getText();

        Assert.assertFalse(actualResult.equals(localRandomAlphaNumeric));
        Assert.assertEquals(actualResult, NEW_NAME);
    }
}