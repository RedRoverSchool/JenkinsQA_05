package tests;

import model.HomePage;
import model.BuildHistoryPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class BuildHistoryTest extends BaseTest {

    private static final String FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);

    @Test
    public void testH1HeaderBuildHistory() {

        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickBuildHistory();

        Assert.assertEquals(homePage.getHeaderText(), "Build History of Jenkins");
    }

    @Test
    public void testIfSMLIconsExist() {
        new HomePage(getDriver()).clickBuildHistory();
        BuildHistoryPage buildHistoryPage = new BuildHistoryPage(getDriver());

        Assert.assertTrue(buildHistoryPage.smallSizeIconIsDisplayed());
        Assert.assertTrue(buildHistoryPage.middleSizeIconIsDisplayed());
        Assert.assertTrue(buildHistoryPage.largeSizeIconIsDisplayed());
    }

    @Test
    public void testRssItemsExist() {
        new HomePage(getDriver()).clickBuildHistory();
        BuildHistoryPage buildHistoryPage = new BuildHistoryPage(getDriver());

        Assert.assertTrue(buildHistoryPage.iconAtomFeedForAllIsDisplayed());
        Assert.assertTrue(buildHistoryPage.iconAtomFeedForFailuresIsDisplayed());
        Assert.assertTrue(buildHistoryPage.iconAtomFeedForFoJustLatestBuildsIsDisplayed());
    }

    @Test
    public void testIfTheIconLegendExist() {
        new HomePage(getDriver()).clickBuildHistory();

        BuildHistoryPage buildHistoryPage = new BuildHistoryPage(getDriver());

        Assert.assertTrue(buildHistoryPage.isIconDisplayed());
    }

    @Test
    public void testNumberOfColumns_ProjectStatusTable() {
        new HomePage(getDriver()).clickBuildHistory();

        BuildHistoryPage buildHistoryPage = new BuildHistoryPage(getDriver());

        Assert.assertEquals(buildHistoryPage.getSize(), 5);
    }

    @Test
    public void testRedirectToMainPage() {
        HomePage homePage = new HomePage(getDriver())
                .clickBuildHistory()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertEquals(homePage.getHeaderText(), "Welcome to Jenkins!");
    }
}
