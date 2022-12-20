package tests;

import model.HomePage;
import model.BuildHistoryPage;
import model.ManageUsersPage;
import model.freestyle.FreestyleProjectStatusPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import static runner.TestUtils.scrollToElement;

public class BuildHistoryTest extends BaseTest {

    private static final By DASHBOARD = By.xpath("//a[contains(text(), 'Dashboard')]");
    private static final By BUILD_HISTORY = By.linkText("Build History");

    private static final String FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private String jobName;

    private void createProject(String description) {
        jobName = TestUtils.getRandomStr(8);

        BuildHistoryPage homePage = new HomePage(getDriver())
                .clickBuildHistory()
                .clickCreateNewJob()
                .enterNewBuildName(jobName)
                .clickNewFreestyleProjectButton()
                .clickOkButton()
                .enterDescriptionField(description)
                .clickSubmitButton();
    }

    @Ignore
    @Test
    public void testVerifyDefaultIconSize() {
        final String size = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn(FreestyleProjectStatusPage.class)
                .clickDashboard()
                .clickBuildHistory()
                .getSizeText();

        int width = getDriver().findElement(By.xpath("//a[@class='jenkins-table__button']//*[name()='svg']")).getSize().getWidth();
        int height = getDriver().findElement(By.xpath("//a[@class='jenkins-table__button']//*[name()='svg']")).getSize().getHeight();

        switch (size) {
            case "S\nmall":
                Assert.assertEquals(width, 16);
                Assert.assertEquals(height, 16);
                break;
            case "M\nedium":
                Assert.assertEquals(width, 20);
                Assert.assertEquals(height, 20);
                break;
            case "L\narge":
                Assert.assertEquals(width, 24);
                Assert.assertEquals(height, 24);
                break;
        }
    }

    @Test
    public void testH1HeaderBuildHistory() {

        final String headerBuildHistory = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn(FreestyleProjectStatusPage.class)
                .clickDashboard()
                .clickBuildHistory()
                .getHeaderText();

        Assert.assertEquals(headerBuildHistory, "Build History of Jenkins");
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

        Assert.assertEquals(getDriver().findElements(By.xpath("//table[@id='projectStatus']/thead/tr/th")).size(), 5);
    }

    @Ignore
    @Test
    public void testTimelineItemExist() {
        createProject("empty");
        getDriver().findElement(By.linkText("Build Now")).click();
        getDriver().findElement(DASHBOARD).click();
        String jobLink = getDriver().getCurrentUrl() + "job/" + jobName + "/1/";
        getDriver().findElement(BUILD_HISTORY).click();
        getWait(10).until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//table[@id='projectStatus']//td[4]"), "stable")));
        getDriver().findElement(By.xpath("//div[contains(text(), \"" + jobName + "\")]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='timeline-event-bubble-title']/a")).getAttribute("href"), jobLink);
    }

    @Ignore
    @Test(dependsOnMethods = "testTimelineItemExist")
    public void testDescriptionIsAdded() {
        getDriver().navigate().refresh();
        getDriver().navigate().refresh();
        getDriver().navigate().refresh();
        getDriver().findElement(By.xpath("//table/tbody/tr/td/a[@class='jenkins-table__link jenkins-table__badge model-link inside']/button")).click();
        getDriver().findElement(By.xpath("//span[contains(text(),'Edit Build Information')]")).click();
        getDriver().findElement(By.name("description")).sendKeys(TestUtils.getRandomStr(8));
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();

        Assert.assertTrue(getDriver().findElement(By.id("description")).isDisplayed());
    }

    @Ignore
    @Test(dependsOnMethods = "testDescriptionIsAdded")
    public void testDeleteBuild() {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(BUILD_HISTORY).click();
        getDriver().findElement(By.xpath("//table/tbody/tr/td/a[@class='jenkins-table__link jenkins-table__badge model-link inside']/button")).click();
        scrollToElement(getDriver(), getDriver().findElement(By.xpath("//span[contains(text(),'Delete build')]")));
        getDriver().findElement(By.xpath("//span[contains(text(),'Delete build')]")).click();
        getDriver().findElement(By.xpath("//button[@id='yui-gen1-button']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@id='no-builds']")).isDisplayed());
    }

    @Test
    public void testRedirectToMainPage() {
        HomePage homePage = new HomePage(getDriver())
                .clickBuildHistory()
                .clickDashboard();

        Assert.assertEquals(homePage.getHeaderText(), "Welcome to Jenkins!");
    }
}
