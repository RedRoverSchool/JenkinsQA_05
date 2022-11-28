import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.time.Duration;

public class ManageJenkinsTest extends BaseTest {

    private static final By MANAGE_JENKINS = By.linkText("Manage Jenkins");

    public static void jsClick(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    @Test
    public void testRenameFullUserName() {
        final String newFullName = RandomStringUtils.randomAlphanumeric(8);

        getDriver().findElement(By.linkText("Manage Jenkins")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();
        String UserIDName = getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText();
        getDriver().findElement(By.className("jenkins-table__button")).click();
        getDriver().findElement(By.name("_.fullName")).clear();
        getDriver().findElement(By.name("_.fullName")).sendKeys(newFullName);
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().navigate().refresh();

        String actualFullNameOnBreadCrumbs = getDriver().findElement(
                By.xpath("//a[@href='/manage/securityRealm/user/" + UserIDName + "/']")).getText();
        String actualFullNameOnPageHeader = getDriver().findElement(
                By.xpath("//a[@href='/user/" + UserIDName + "']")).getText();

        Assert.assertEquals(actualFullNameOnBreadCrumbs, newFullName);
        Assert.assertEquals(actualFullNameOnPageHeader, newFullName);
    }

    @Test
    public void testManageOldData() {

        final String expectedText = "No old data was found.";

        getDriver().findElement(MANAGE_JENKINS).click();

        jsClick(getDriver(), getDriver().findElement(By.xpath("//a[@href='administrativeMonitor/OldData/']")));

        String allTextFromMainPanel = getDriver().findElement(By.id("main-panel")).getText();
        String[] actualText = allTextFromMainPanel.split("\n");

        Assert.assertTrue(getDriver().findElements(By.xpath("//div[@id='main-panel']//tbody//tr")).isEmpty());
        Assert.assertEquals(actualText[actualText.length - 1], expectedText);
    }

    @Test
    public void testPluginManager() {

        final String pluginName = "TestNG Results";
        final By pluginManager = By.xpath("//a[@href='pluginManager']");
        final By availablePluginsTab = By.xpath("//a[@href='./available']");
        final By searchField = By.id("filter-box");
        final By pluginsTableRows = By.xpath("//div[@id='main-panel']//tbody//tr");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));

        getDriver().findElement(MANAGE_JENKINS).click();
        getDriver().findElement(pluginManager).click();
        getDriver().findElement(availablePluginsTab).click();
        getDriver().findElement(searchField).sendKeys(pluginName);
        getDriver().findElement(By.xpath("//tr[@data-plugin-id='testng-plugin']//label")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();

        wait.until(ExpectedConditions.textToBe(By.id("status26"), "Success"));
        wait.until(ExpectedConditions.textToBe(By.id("status28"), "Success"));

        getDriver().findElement(By.linkText("Go back to the top page")).click();
        getDriver().findElement(MANAGE_JENKINS).click();
        getDriver().findElement(pluginManager).click();
        getDriver().findElement(availablePluginsTab).click();
        getDriver().findElement(searchField).sendKeys(pluginName);

        wait.until(ExpectedConditions.numberOfElementsToBe(pluginsTableRows, 0));

        getDriver().findElement(By.xpath("//a[@href='./installed']")).click();
        getDriver().findElement(searchField).sendKeys(pluginName);

        Assert.assertFalse(getDriver().findElements(pluginsTableRows).isEmpty());
        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@data-plugin-id='testng-plugin']")).isDisplayed());
    }
}