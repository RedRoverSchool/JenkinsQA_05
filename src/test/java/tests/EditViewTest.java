package tests;

import model.views.EditViewPage;
import model.HomePage;
import model.views.MyViewsPage;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import runner.BaseTest;
import runner.BaseUtils;
import runner.TestUtils;

import java.util.*;
import java.util.stream.Collectors;

public class EditViewTest extends BaseTest {
    private static String localViewName = TestUtils.getRandomStr();
    private static final int waitTime = 5;
    private static final By DASHBOARD = By.cssSelector("#jenkins-name-icon");
    private static final By SUBMIT_BUTTON = By.cssSelector("[type='submit']");
    private static final By ITEM_OPTION = By.cssSelector("input[json='true']+label");
    private static final By MY_VIEWS = By.xpath("//a[@href='/me/my-views']");
    private static final By INPUT_NAME = By.cssSelector("[name='name']");
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
    final By[] listAllJobTypes = {FREESTYLE_0, PIPELINE_1, MULTICONFIG_2, FOLDER_3, MULTIBRANCH_4, ORGFOLDER_5};

    final By GLOBAL_VIEW_0 = By.xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.ProxyView']");
    final By LIST_VIEW_1 = By.xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.ListView']");
    final By GLOBAL_VIEW_2 = By.xpath("//label[@class='jenkins-radio__label' and @for='hudson.model.MyView']");
    final By[] listAllViewTypes = {GLOBAL_VIEW_0, LIST_VIEW_1, GLOBAL_VIEW_2};


    private void createOneItemFromListOfJobTypes(int indexOfJob) {
        getDriver().findElement(By.xpath("//a[contains(@href, '/view/all/newJob')]")).click();
        getDriver().findElement(By.cssSelector("#name.jenkins-input")).sendKeys(TestUtils.getRandomStr());
        getDriver().findElement(listAllJobTypes[indexOfJob]).click();
        getDriver().findElement(SUBMIT_BUTTON).submit();
        getDriver().findElement(DASHBOARD).click();
    }

    private void createManyJobsOfEachType(int numberOfJobsOfEachType) {
        for (int i = 0; i < numberOfJobsOfEachType; i++) {
            for (int j = 0; j < listAllJobTypes.length; j++) {
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
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), getDriver().findElement(locator));
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

    @Test
    public void testCreateOneItemFromListOfJobTypes() {
        int actualNumberOfJobs = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(TestUtils.getRandomStr())
                .selectPipelineAndClickOk()

                .clickDashboard()
                .clickNewItem()
                .setItemName(TestUtils.getRandomStr())
                .selectPipelineAndClickOk()

                .clickDashboard()
                .clickNewItem()
                .setItemName(TestUtils.getRandomStr())
                .selectMultiConfigurationProjectAndClickOk()

                .clickDashboard()
                .clickNewItem()
                .setItemName(TestUtils.getRandomStr())
                .selectFolderAndClickOk()

                .clickDashboard()
                .clickNewItem()
                .setItemName(TestUtils.getRandomStr())
                .selectMultibranchPipelineAndClickOk()

                .clickDashboard()
                .clickNewItem()
                .setItemName(TestUtils.getRandomStr())
                .selectOrgFolderAndClickOk()

                .clickDashboard()
                .getJobNamesList()
                .size();

        Assert.assertEquals(actualNumberOfJobs, 6);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testGlobalViewAddFilterBuildQueue() {
        boolean newPaneIsDisplayed = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickAddViewLink()
                .setViewName(TestUtils.getRandomStr())
                .setGlobalViewType()
                .clickCreateButton()
                .selectFilterBuildQueueOptionCheckBox()
                .clickListOrMyViewOkButton()
                .getActiveFiltersList()
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testGlobalViewAddBothFilters() {
        EditViewPage editViewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickAddViewLink()
                .setViewName(TestUtils.getRandomStr())
                .setGlobalViewType()
                .clickCreateButton()
                .selectFilterBuildQueueOptionCheckBox()
                .selectFilterBuildExecutorsOptionCheckBox()
                .clickGlobalViewOkButton()
                .clickEditViewButton();

        Assert.assertTrue(editViewPage.isFilterBuildQueueOptionCheckBoxSelected() && editViewPage.isFilterBuildExecutorsOptionCheckBoxSelected());
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testListViewAddFiveItems() {
        int expectedResult = 5;

        int actualResult = new HomePage(getDriver())
            .clickMyViewsSideMenuLink()
            .clickAddViewLink()
            .setViewName(localViewName)
            .setListViewTypeAndClickCreate()
            .addJobsToListView(expectedResult)
            .clickListOrMyViewOkButton()
            .getJobNamesList()
            .size();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = {"testListViewAddFiveItems","testCreateOneItemFromListOfJobTypes"})
    public void testListViewAddNewColumn() {
        String expectedResult = "Git Branches";

        String actualResult = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(localViewName)
                .clickEditViewButton()
                .scrollToColumnDropDownMenuPlaceInCenterWaitTillNotMoving()
                .clickAddColumnDropDownMenu()
                .clickGitBranchesColumnMenuOption()
                .clickListOrMyViewOkButton()
                .getJobTableHeaderTextList()
                .get(new MyViewsPage(getDriver()).getJobTableHeaderTextList().size() - 1);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testAddAllItemsToListView() {
        int expectedResult = new HomePage(getDriver())
                .getJobNamesList().size();

        int actualResult = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickAddViewLink()
                .setViewName(TestUtils.getRandomStr())
                .setListViewTypeAndClickCreate()
                .addAllJobsToListView()
                .clickListOrMyViewOkButton()
                .getJobNamesList().size();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testListViewAddRegexFilter() {
        int expectedResult = new HomePage(getDriver())
                .getNumberOfJobsContainingString("9");

        new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickAddViewLink()
                .setViewName(TestUtils.getRandomStr())
                .setListViewTypeAndClickCreate()
                .scrollToRegexFilterCheckboxPlaceInCenterWaitTillNotMoving();
        if(!new EditViewPage(getDriver()).isRegexCheckboxChecked()) {
            new EditViewPage(getDriver()).clickRegexCheckbox();
        }

        int actualResult = new EditViewPage(getDriver())
                .scrollToRegexFilterCheckboxPlaceInCenterWaitTillNotMoving()
                .clearAndSendKeysRegexTextArea(".*9.*")
                .clickListOrMyViewOkButton()
                .getDisplayedNumberOfJobs();
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testListViewChangeColumnOrder() {
        String[] expectedResult = {"W", "S"};
        new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickAddViewLink()
                .setViewName(TestUtils.getRandomStr())
                .setListViewTypeAndClickCreate()
                .addAllJobsToListView()
                .scrollToStatusColumnDragHandlePlaceInCenterWaitTillNotMoving()
                .dragByYOffset(100)
                .clickListOrMyViewOkButton();

        String[] actualResult = {new MyViewsPage(getDriver())
                .getJobTableHeaderTextList().get(0), new MyViewsPage(getDriver())
                .getJobTableHeaderTextList().get(1)};
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testListViewAddFilterBuildQueue() {
        boolean newPaneIsDisplayed = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickAddViewLink()
                .setViewName(TestUtils.getRandomStr())
                .setListViewType()
                .clickCreateButton()
                .selectFilterBuildQueueOptionCheckBox()
                .clickListOrMyViewOkButton()
                .getActiveFiltersList()
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Test(dependsOnMethods = "testCreateOneItemFromListOfJobTypes")
    public void testMyViewAddFilterBuildQueue() {
        boolean newPaneIsDisplayed = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickAddViewLink()
                .setViewName(TestUtils.getRandomStr())
                .setMyViewType()
                .clickCreateButton()
                .clickEditViewButton()
                .selectFilterBuildQueueOptionCheckBox()
                .clickListOrMyViewOkButton()
                .getActiveFiltersList()
                .contains("Filtered Build Queue");

        Assert.assertTrue(newPaneIsDisplayed);
    }

    @Ignore
    @Test
    public void testListViewCheckEveryAddColumnItem() {
        localViewName = TestUtils.getRandomStr();
        listViewSeriesPreConditions(1, localViewName);
        final String[] tableValues = {
                "S", "W", "Name", "Last Success", "Last Failure", "Last Stable",
                "Last Duration", "", "Git Branches", "Name", "Description"};

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
                    .cssSelector("table#projectstatus th:last-child")).getText().replace("↓", " ").trim();
            allMatches.add(tableMenuMap.get(selectedColumnName).equals(lastColumnName));
            getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();
            scrollWaitTillNotMovingAndClick(waitTime, LAST_EXISTING_COLUMN);
            getDriver().findElement(LAST_EXISTING_COLUMN).findElement(By.cssSelector("button.repeatable-delete")).click();
            scrollWaitTillNotMovingAndClick(waitTime, ADD_COLUMN);
        }
        getDriver().findElement(SUBMIT_BUTTON).click();

        Assert.assertTrue(allMatches.stream().allMatch(element -> element == true));
    }

    @Test(dependsOnMethods = {"testListViewAddFiveItems","testCreateOneItemFromListOfJobTypes"})
    public void testDeleteStatusColumn() {
        boolean isContainingStatusColumn = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(localViewName)
                .clickEditViewButton()
                .scrollToDeleteStatusColumnButtonPlaceInCenterWaitTillNotMoving()
                .clickDeleteStatusColumnButton()
                .clickListOrMyViewOkButton()
                .getJobTableHeaderTextList()
                .contains("S");

        Assert.assertFalse(isContainingStatusColumn);
    }


    @Test
    public void testMultipleSpacesRenameView() {
        localViewName = TestUtils.getRandomStr();
        listViewSeriesPreConditions(1, localViewName);
        final String nonSpaces = TestUtils.getRandomStr(5);
        final String spaces = nonSpaces.replaceAll("[a-zA-Z0-9]", " ");
        final String newName = nonSpaces + spaces + nonSpaces;

        getDriver().findElement(INPUT_NAME).clear();
        getDriver().findElement(INPUT_NAME).sendKeys(newName);
        getDriver().findElement(SUBMIT_BUTTON).click();

        String actualResult = getDriver().findElement(By.cssSelector(".tab.active")).getText();
        Assert.assertNotEquals(actualResult, localViewName);
        Assert.assertEquals(actualResult, (nonSpaces + " " + nonSpaces));
    }

    @Test(dependsOnMethods = {"testListViewAddFiveItems","testCreateOneItemFromListOfJobTypes"})
    public void testIllegalCharacterRenameView() {
        final char[] illegalCharacters = "#!@$%^&*:;<>?/[]|\\".toCharArray();
        new HomePage(getDriver()).clickMyViewsSideMenuLink();

        List<Boolean> checksList = new ArrayList<>();
        for (int i = 0; i < illegalCharacters.length; i++) {
            try {
            new HomePage(getDriver())
                    .clickMyViewsTopMenuLink()
                    .clickView(localViewName)
                    .clickEditViewButton()
                    .renameView(illegalCharacters[i] + localViewName)
                    .clickListOrMyViewOkButton();
                    if (new EditViewPage(getDriver()).getErrorPageHeader().equals("Error")) {
                        checksList.add(new EditViewPage(getDriver()).isCorrectErrorPageDetailsText(illegalCharacters[i]));

                        checksList.add(!new HomePage(getDriver())
                                .clickDashboard()
                                .clickMyViewsSideMenuLink()
                                .getListViewsNames().contains(illegalCharacters[i] + localViewName));
                    } else {
                        checksList.add(false);
                        BaseUtils.log("Not an Error page");
                    }
                } catch (NoSuchElementException exception) {
                    BaseUtils.log(String.format("Invalid Page at Title: %s, URL: %s",
                            getDriver().getTitle(),
                            getDriver().getCurrentUrl()));
                    checksList.add(false);
                }
            }

        Assert.assertTrue(checksList.stream().allMatch(element -> element == true));
    }

    @Test
    public void testRenameView() {
        localViewName = TestUtils.getRandomStr();
        listViewSeriesPreConditions(1, localViewName);
        final String newName = TestUtils.getRandomStr();

        getDriver().findElement(INPUT_NAME).clear();
        getDriver().findElement(INPUT_NAME).sendKeys(newName);
        getDriver().findElement(SUBMIT_BUTTON).click();

        String actualResult = getDriver().findElement(By.cssSelector(".tab.active")).getText();
        Assert.assertFalse(actualResult.equals(localViewName));
        Assert.assertEquals(actualResult, newName);
    }
}