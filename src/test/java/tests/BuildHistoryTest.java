package tests;

import model.freestyle.FreestyleProjectStatusPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;


import model.HomePage;

import static runner.TestUtils.scrollToElement;

public class BuildHistoryTest extends BaseTest {

    private static final By DASHBOARD = By.xpath("//a[contains(text(), 'Dashboard')]");
    private static final By BUILD_HISTORY = By.linkText("Build History");

    private static final String FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private String jobName;

    private void createProject(String description) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        jobName = TestUtils.getRandomStr(8);
        getDriver().findElement(By.id("name")).sendKeys(jobName);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        if (!(description.equals("empty"))) {
            getDriver().findElement(By.name("description")).sendKeys(description);
        }
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
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
    public void testH1Header_BuildHistory() {

        final String header_BuildHistory = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FREESTYLE_NAME)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn(FreestyleProjectStatusPage.class)
                .clickDashboard()
                .clickBuildHistory().getHeaderText();

        Assert.assertEquals(header_BuildHistory, "Build History of Jenkins");
    }

    @Test
    public void testIfSMLIconsExist() {
        HomePage homePage = new HomePage(getDriver())
                .clickBuildHistory();

        Assert.assertTrue(getDriver().findElement(By.xpath("//a[@href='/iconSize?16x16']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class='jenkins-icon-size__items jenkins-buttons-row']/ol/li/following-sibling::li[2]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class='jenkins-icon-size__items jenkins-buttons-row']/ol/li[last()]")).isDisplayed());
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
