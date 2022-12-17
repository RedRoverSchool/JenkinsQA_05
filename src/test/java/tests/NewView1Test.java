package tests;

import model.views.EditViewPage;
import model.HomePage;
import model.views.MyViewsPage;
import model.views.ViewPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class NewView1Test extends BaseTest {

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
                .clickSaveBtn()
                .clickDashboard()

                .clickNewItem()
                .setItemName(PIPELINE_PROJECT_NAME)
                .selectPipelineAndClickOk()
                .saveConfigAndGoToProject()
                .clickDashboard()

                .clickNewItem()
                .setItemName("Multi-configuration project")
                .selectMultiConfigurationProjectAndClickOk()
                .clickSave()
                .goToDashboard()

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
    public void testAddSomeJobsToListView() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickView(LIST_VIEW_NAME)
                .clickLinkTextAddExistingJob()
                .clickJobForInputToListView(FREESTYLE_PROJECT_NAME)
                .clickJobForInputToListView(PIPELINE_PROJECT_NAME)
                .clickListOrMyViewOkButton();

        Assert.assertEquals(myViewsPage.getCurrentURL(),
                "http://localhost:8080/user/admin/my-views/view/" + LIST_VIEW_NAME + "/");
        Assert.assertEquals(myViewsPage.getListProjectsNames(),
                FREESTYLE_PROJECT_NAME.concat(" ").concat(PIPELINE_PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testAddSomeJobsToListView")
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
}
