import model.EditViewPage;
import model.HomePage;
import model.MyViewsPage;
import model.ViewPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class NewView1Test extends BaseTest {

    private static final String GLOBAL_VIEW_NAME = "Global_View";
    private static final String LIST_VIEW_NAME = "List_View";
    private static final String MY_VIEW_NAME = "My_View";
    private static final String LIST_VIEW_RENAME = "NewListView";

    @Test
    public void testCreateViews() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName("Freestyle project")
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn()
                .clickDashboard()

                .clickNewItem()
                .setProjectName("Pipeline")
                .selectPipelineAndClickOk()
                .saveConfigAndGoToProject()
                .clickDashboard()

                .clickNewItem()
                .setProjectName("Multi-configuration project")
                .selectMultiConfigurationProjectAndClickOk()
                .clickSave()
                .goToDashboard()

                .clickMyViews()
                .clickNewView()
                .setViewName(GLOBAL_VIEW_NAME)
                .setGlobalViewType()
                .clickCreateButton()
                .clickDashboard()

                .clickMyViews()
                .clickNewView()
                .setViewName(LIST_VIEW_NAME)
                .setListViewType()
                .clickCreateButton()
                .clickDashboard()

                .clickMyViews()
                .clickNewView()
                .setViewName(MY_VIEW_NAME)
                .setMyViewType()
                .clickCreateButton()
                .clickDashboard()

                .clickMyViews();

        Assert.assertTrue(myViewsPage.getListViewsNames().contains(GLOBAL_VIEW_NAME));
        Assert.assertTrue(myViewsPage.getListViewsNames().contains(LIST_VIEW_NAME));
        Assert.assertTrue(myViewsPage.getListViewsNames().contains(MY_VIEW_NAME));
    }

    @Test(dependsOnMethods = "testCreateViews")
    public void testRenameView() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .goToEditView(LIST_VIEW_NAME)
                .renameView(LIST_VIEW_RENAME)
                .clickOk()
                .clickMyViews();

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
                .clickMyViews()
                .clickView(MY_VIEW_NAME);

        Assert.assertEquals(viewPage.getJobList(),
                new HomePage(getDriver()).getJobList());
    }

    @Test(dependsOnMethods = "testViewHasSelectedTypeMyView")
    public void testViewSideMenu() {
        ViewPage viewPage = new HomePage(getDriver())
                .clickMyViews()
                .clickView(LIST_VIEW_RENAME);

        Assert.assertTrue(viewPage.getBreadcrumbsItemName(LIST_VIEW_RENAME).contains(LIST_VIEW_RENAME));

        Assert.assertEqualsNoOrder(viewPage.getSideMenuTextList(),viewPage.getActualSideMenu());
    }

    @Test(dependsOnMethods = "testViewSideMenu")
    public void testDeleteView() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .clickMyViews()
                .clickView(LIST_VIEW_RENAME)
                .clickDeleteViewItem()
                .clickYesButtonDeleteView();

        Assert.assertFalse(myViewsPage.getListViewsNames().contains(LIST_VIEW_RENAME));
    }

    @Test(dependsOnMethods = "testDeleteView")
    public void testDeleteAllViews() {
        MyViewsPage myViewsPage = new HomePage(getDriver())
                .clickMyViews()
                .deleteAllViews();

        Assert.assertEquals(myViewsPage.getListViewsNames(), "");
    }
}