package tests;

import model.HomePage;
import model.BuildHistoryPage;
import model.freestyle.FreestyleProjectStatusPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
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
                .clickSaveBtn(FreestyleProjectStatusPage.class)
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
        HomePage homePage = new HomePage(getDriver())
                .clickBuildHistory();

        Assert.assertTrue(getDriver().findElement(By.xpath("//a/span[contains(text(), 'Atom feed for all')]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//a/span[contains(text(), 'Atom feed for failures')]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//a/span[contains(text(), 'Atom feed for just latest builds')]")).isDisplayed());
    }

    @Test
    public void testIfTheIconLegendExist() {
        HomePage homePage = new HomePage(getDriver())
                .clickBuildHistory();

        Assert.assertTrue(getDriver().findElement(By.xpath("//a[@href='/legend']")).isDisplayed());
    }

    @Test
    public void testNumberOfColumns_ProjectStatusTable() {
        HomePage homePage = new HomePage(getDriver())
                .clickBuildHistory();

        BuildHistoryPage buildHistoryPage = new BuildHistoryPage(getDriver());

        Assert.assertEquals(buildHistoryPage.getSize(), 5);
    }

    @Test
    public void testRedirectToMainPage() {
        HomePage homePage = new HomePage(getDriver())
                .clickBuildHistory()
                .clickDashboard();

        Assert.assertEquals(homePage.getHeaderText(), "Welcome to Jenkins!");
    }
}
