package tests;

import model.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.List;

public class ListViewTest extends BaseTest {

    private static final String RANDOM_LIST_VIEW_NAME = TestUtils.getRandomStr();

    @Test
    public void testCreateNewListViewWithExistingJob() {
        final String projectOne = TestUtils.getRandomStr();

        int quantityProjectsInListView = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(projectOne)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn()
                .clickDashboard()
                .clickAddViewLink()
                .setViewName(RANDOM_LIST_VIEW_NAME)
                .setListViewTypeAndClickCreate()
                .addJobToView(projectOne)
                .clickGlobalViewOkButton()
                .getJobList().size();

        Assert.assertEquals(quantityProjectsInListView, 1);
        Assert.assertTrue(new HomePage(getDriver()).getViewList().contains(RANDOM_LIST_VIEW_NAME));
    }

    @Test(dependsOnMethods = "testCreateNewListViewWithExistingJob")
    public void testEditViewAddDescription() {
        final String descriptionRandom = TestUtils.getRandomStr();

        String actualDescription = new HomePage(getDriver())
                .clickView(RANDOM_LIST_VIEW_NAME)
                .clickEditViewButton()
                .addDescription(descriptionRandom)
                .clickGlobalViewOkButton()
                .getTextDescription();

        Assert.assertEquals(actualDescription, descriptionRandom);
    }

    @Test(dependsOnMethods = "testEditViewAddDescription")
    public void testEditViewDeleteDescription() {
        String descriptionText = new HomePage(getDriver())
                .clickView(RANDOM_LIST_VIEW_NAME)
                .clickEditDescription()
                .clearDescription()
                .clickSaveButton()
                .getTextDescription();

        Assert.assertEquals(descriptionText, "");
    }

    @Test(dependsOnMethods = "testEditViewDeleteDescription")
    public void testDeleteListView() {

        List<String> viewList = new HomePage(getDriver())
                .clickView(RANDOM_LIST_VIEW_NAME)
                .clickDeleteViewItem()
                .clickYesButtonDeleteListView()
                .getViewList();

        Assert.assertFalse(viewList.contains(RANDOM_LIST_VIEW_NAME));
    }
}
