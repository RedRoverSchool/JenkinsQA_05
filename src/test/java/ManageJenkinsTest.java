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

    private static final String PLUGIN_NAME = "TestNG Results";
    private static final By MANAGE_JENKINS = By.linkText("Manage Jenkins");
    private static final By PLUGIN_MANAGER = By.xpath("//a[@href='pluginManager']");
    private static final By AVAILABLE_PLUGINS_TAB = By.xpath("//a[@href='./available']");
    private static final By INSTALLED_PLUGINS_TAB = By.xpath("//a[@href='./installed']");
    private static final By SEARCH_PLUGIN_FIELD = By.id("filter-box");
    private static final By PLUGIN_TABLE_ROWS = By.xpath("//div[@id='main-panel']//tbody//tr");

    public static void jsClick(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public static WebDriverWait getWait(WebDriver driver, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
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
    public void testPluginManagerInstallPlugin() {

        getDriver().findElement(MANAGE_JENKINS).click();
        getDriver().findElement(PLUGIN_MANAGER).click();
        getDriver().findElement(AVAILABLE_PLUGINS_TAB).click();
        getDriver().findElement(SEARCH_PLUGIN_FIELD).sendKeys(ManageJenkinsTest.PLUGIN_NAME);
        getDriver().findElement(By.xpath("//tr[@data-plugin-id='testng-plugin']//label")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();

        for (WebElement element : getDriver().findElements(By.xpath("//td[contains(@id, 'status')]"))) {
            getWait(getDriver(), 10).until(ExpectedConditions.textToBePresentInElement(element, "Success"));
        }

        getDriver().findElement(By.linkText("Go back to the top page")).click();
        getDriver().findElement(MANAGE_JENKINS).click();
        getDriver().findElement(PLUGIN_MANAGER).click();
        getDriver().findElement(AVAILABLE_PLUGINS_TAB).click();
        getDriver().findElement(SEARCH_PLUGIN_FIELD).sendKeys(ManageJenkinsTest.PLUGIN_NAME);

        getWait(getDriver(), 10).until(ExpectedConditions.numberOfElementsToBe(PLUGIN_TABLE_ROWS, 0));

        getDriver().findElement(INSTALLED_PLUGINS_TAB).click();
        getDriver().findElement(SEARCH_PLUGIN_FIELD).sendKeys(ManageJenkinsTest.PLUGIN_NAME);

        Assert.assertFalse(getDriver().findElements(PLUGIN_TABLE_ROWS).isEmpty());
        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@data-plugin-id='testng-plugin']")).isDisplayed());
    }

    @Test(dependsOnMethods={"testPluginManagerInstallPlugin"})
    public void testPluginManagerDeletePlugin() {

        getDriver().findElement(MANAGE_JENKINS).click();
        getDriver().findElement(PLUGIN_MANAGER).click();
        getDriver().findElement(INSTALLED_PLUGINS_TAB).click();
        getDriver().findElement(SEARCH_PLUGIN_FIELD).sendKeys(ManageJenkinsTest.PLUGIN_NAME);

        getDriver().findElement(By.xpath("//button[@tooltip='Uninstall TestNG Results Plugin']")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
        getDriver().findElement(AVAILABLE_PLUGINS_TAB).click();
        getDriver().findElement(By.id("yui-gen2-button")).click();
        getDriver().findElement(By.xpath("//label[@for='scheduleRestartCheckbox']")).click();

        getDriver().navigate().refresh();

        getWait(getDriver(), 30).until(ExpectedConditions.visibilityOfElementLocated(By.name("j_username")));

        loginWeb();

        getDriver().findElement(MANAGE_JENKINS).click();
        getDriver().findElement(PLUGIN_MANAGER).click();
        getDriver().findElement(INSTALLED_PLUGINS_TAB).click();
        getDriver().findElement(SEARCH_PLUGIN_FIELD).sendKeys(ManageJenkinsTest.PLUGIN_NAME);

        getWait(getDriver(), 10).until(ExpectedConditions.invisibilityOfElementLocated(PLUGIN_TABLE_ROWS));

        getDriver().findElement(AVAILABLE_PLUGINS_TAB).click();
        getDriver().findElement(SEARCH_PLUGIN_FIELD).sendKeys(ManageJenkinsTest.PLUGIN_NAME);

        Assert.assertFalse(getDriver().findElements(PLUGIN_TABLE_ROWS).isEmpty());
        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@data-plugin-id='testng-plugin']")).isDisplayed());
    }
}