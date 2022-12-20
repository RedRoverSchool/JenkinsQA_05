package tests;

import model.freestyle.FreestyleProjectStatusPage;
import model.multiconfiguration.MultiConfigurationProjectStatusPage;
import model.pipeline.PipelineStatusPage;
import model.views.*;
import model.HomePage;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

public class NewViewTest extends BaseTest {

    private static final String PROJECT_RANDOM_NAME = TestUtils.getRandomStr(6);
    private static final String FREESTYLE_PROJECT_NAME = "Freestyle project";
    private static final String PIPELINE_PROJECT_NAME = "Pipeline";
    private static final String GLOBAL_VIEW_NAME = "Global_View";
    private static final String LIST_VIEW_NAME = "List_View";
    private static final String MY_VIEW_NAME = "My_View";
    private static final String LIST_VIEW_RENAME = "NewListView";

    @Test
    public void testCreateViews() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn(FreestyleProjectStatusPage.class)
                .clickDashboard()

                .clickNewItem()
                .setItemName(PIPELINE_PROJECT_NAME)
                .selectPipelineAndClickOk()
                .clickSaveBtn(PipelineStatusPage.class)
                .clickDashboard()

                .clickNewItem()
                .setItemName("Multi-configuration project")
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveBtn(MultiConfigurationProjectStatusPage.class)
                .clickDashboard()

                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(GLOBAL_VIEW_NAME)
                .setGlobalViewType()
                .clickCreateButton()
                .clickDashboard()

                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LIST_VIEW_NAME)
                .setListViewType()
                .clickCreateButton()
                .clickDashboard()

                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(MY_VIEW_NAME)
                .setMyViewType()
                .clickCreateButton()
                .clickDashboard()

                .clickMyViewsSideMenuLink();

        Assert.assertTrue(myViewsPage.getListViewsNames().contains(GLOBAL_VIEW_NAME));
        Assert.assertTrue(myViewsPage.getListViewsNames().contains(LIST_VIEW_NAME));
        Assert.assertTrue(myViewsPage.getListViewsNames().contains(MY_VIEW_NAME));
    }

    @Test(dependsOnMethods = "testCreateViews")
    public void testSelectJobsToAddInListView() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_NAME)
                .clickLinkTextAddExistingJob()
                .clickJobsCheckBoxForAddRemoveToListView(FREESTYLE_PROJECT_NAME)
                .clickJobsCheckBoxForAddRemoveToListView(PIPELINE_PROJECT_NAME)
                .clickListOrMyViewOkButton();

        Assert.assertEquals(myViewsPage.getCurrentURL(),
                "http://localhost:8080/user/admin/my-views/view/" + LIST_VIEW_NAME + "/");
        Assert.assertEquals(myViewsPage.getListProjectsNamesAsString(),
                FREESTYLE_PROJECT_NAME.concat(" ").concat(PIPELINE_PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testSelectJobsToAddInListView")
    public void testDeselectJobsFromListView() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .goToEditView(LIST_VIEW_NAME)
                .clickJobsCheckBoxForAddRemoveToListView(FREESTYLE_PROJECT_NAME)
                .clickJobsCheckBoxForAddRemoveToListView(PIPELINE_PROJECT_NAME)
                .clickListOrMyViewOkButton();

        Assert.assertEquals(myViewsPage.getCurrentURL(),
                "http://localhost:8080/user/admin/my-views/view/" + LIST_VIEW_NAME + "/");
        Assert.assertTrue(myViewsPage.getTextContentOnViewMainPanel().contains(
                "This view has no jobs associated with it. "
                        + "You can either add some existing jobs to this view or create a new job in this view."));
    }

    @Test(dependsOnMethods = "testDeselectJobsFromListView")
    public void testCreateViewWithExistingName() {
        NewViewPage newViewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(LIST_VIEW_NAME)
                .setListViewType();

        Assert.assertEquals(newViewPage.getErrorMessageViewAlreadyExist(),
                "A view with name " + LIST_VIEW_NAME + " already exists");

        MyViewsPage myViewsPage = new NewViewPage(getDriver())
                .setListViewType()
                .clickCreateButton();

        Assert.assertTrue(myViewsPage.getTextContentOnViewMainPanel().
                contains("A view already exists with the name \"" + LIST_VIEW_NAME + "\""));
    }

    @Test(dependsOnMethods = "testCreateViewWithExistingName")
    public void testRenameView() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .goToEditView(LIST_VIEW_NAME)
                .renameView(LIST_VIEW_RENAME)
                .clickListOrMyViewOkButton();

        Assert.assertTrue(myViewsPage.getListViewsNames().contains(LIST_VIEW_RENAME));
    }

    @Test(dependsOnMethods = "testRenameView")
    public void testViewHasSelectedTypeGlobalView() {
        EditViewPage editViewPage = new HomePage(getDriver())
                .goToEditView(GLOBAL_VIEW_NAME);

        Assert.assertEquals(editViewPage.getUniqueTextOnGlobalViewEditPage(),
                "The name of a global view that will be shown.");
    }

    @Test(dependsOnMethods = "testViewHasSelectedTypeGlobalView")
    public void testViewHasSelectedTypeListView() {
        EditViewPage editViewPage = new HomePage(getDriver())
                .goToEditView(LIST_VIEW_RENAME);

        Assert.assertEquals(editViewPage.getUniqueSectionOnListViewEditPage(),
                "Job Filters");
    }

    @Test(dependsOnMethods = "testViewHasSelectedTypeListView")
    public void testViewHasSelectedTypeMyView() {
        ViewPage viewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(MY_VIEW_NAME);

        Assert.assertEquals(viewPage.getJobList(),
                new HomePage(getDriver()).getJobList());
    }

    @Test(dependsOnMethods = "testViewHasSelectedTypeMyView")
    public void testViewSideMenu() {
        ViewPage viewPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_RENAME);

        Assert.assertTrue(viewPage.getBreadcrumbsItemName(LIST_VIEW_RENAME).contains(LIST_VIEW_RENAME));
        Assert.assertEqualsNoOrder(viewPage.getSideMenuTextList(), viewPage.getActualSideMenu());
    }

    @Test(dependsOnMethods = "testViewSideMenu")
    public void testDeleteView() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_RENAME)
                .clickDeleteViewItem()
                .clickYesButtonDeleteView();

        Assert.assertFalse(myViewsPage.getListViewsNames().contains(LIST_VIEW_RENAME));
    }

    @Test(dependsOnMethods = "testDeleteView")
    public void testDeleteAllViews() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .deleteAllViews();

        Assert.assertEquals(myViewsPage.getListViewsNames(), "");
    }

    @Test
    public void testAddDescription() {
        String actualResult = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickAddDescription()
                .clearDescriptionField()
                .sendKeysInDescriptionField("Description")
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualResult, "Description");
    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testEditDescription() {
        String actualResult = new MyViewsPage(getDriver())
                .clickEditDescription()
                .clearDescriptionField()
                .sendKeysInDescriptionField("New Description")
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualResult, "New Description");
    }

    @Test
    public void testCreateListViewWithAddSettings(){
        int countColumnsBeforeAdd = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_RANDOM_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn(FreestyleProjectStatusPage.class)
                .clickDashboard()
                .clickMyViewsSideMenuLink()
                .clickNewView()
                .setViewName(TestUtils.getRandomStr(6))
                .setListViewTypeAndClickCreate()
                .addJobToView(PROJECT_RANDOM_NAME)
                .getCountColumns();

        String textConfirmAfterClickingApply = new EditListViewPage(getDriver())
                .addColumn("Build Button")
                .clickApplyButton()
                .getTextConfirmAfterClickingApply();

        String actualMarkedProjectName = new EditViewPage(getDriver())
                .clickGlobalViewOkButton()
                .clickEditViewButton()
                .getSelectedJobName();

        int countColumnsAfterAdd = new EditListViewPage(getDriver())
                .getCountColumns();

        Assert.assertEquals(actualMarkedProjectName, PROJECT_RANDOM_NAME);
        Assert.assertEquals(countColumnsAfterAdd, countColumnsBeforeAdd + 1);
        Assert.assertEquals(textConfirmAfterClickingApply, "Saved");
    }

    @Test
    public void testLettersSMLClickableMyViews() {
        String expectedClassTableM = "jenkins-table jenkins-table--medium sortable";
        String expectedClassTableL = "jenkins-table  sortable";
        String expectedClassTableS = "jenkins-table jenkins-table--small sortable";

        String myViewsPageSizeM = new MyViewsPage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn(FreestyleProjectStatusPage.class)
                .clickDashboard()
                .clickMyViewsSideMenuLink()
                .clickSizeM();

        Assert.assertEquals(myViewsPageSizeM, expectedClassTableM);

        String myViewsPageSizeS = new MyViewsPage(getDriver())
                .clickSizeS();

        Assert.assertEquals(myViewsPageSizeS, expectedClassTableS);

        String myViewsPageSizeL = new MyViewsPage(getDriver())
                .clickSizeL();

        Assert.assertEquals(myViewsPageSizeL, expectedClassTableL);
    }
}
