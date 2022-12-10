import model.HomePage;
import model.StatusUserPage;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.time.Duration;

public class ManageJenkinsTest extends BaseTest {
    private static final String NEW_USERS_FULL_NAME = TestUtils.getRandomStr(6);
    private static final String PLUGIN_NAME = "TestNG Results";
    private static final By MANAGE_JENKINS = By.linkText("Manage Jenkins");
    private static final By SECURITY_MANAGE_USERS = By.xpath("//a[@href='securityRealm/']");
    private static final By JENKINS_MENU_DROPDOWN = By.cssSelector("a[href='user/admin/'] > .jenkins-menu-dropdown-chevron");
    private static final By USER_ADMIN_CONFIGURE = By.cssSelector("a[href='/user/admin/configure']");
    private static final By USER_FULL_NAME = By.xpath("//input[@name='_.fullName']");
    private static final By SAVE_BUTTON = By.id("yui-gen3-button");
    private static final By H1_TITLE = By.xpath("//h1");
    private static final By PAGE_HEADER_USER = By.cssSelector(".model-link > .hidden-xs.hidden-sm");
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
        StatusUserPage userStatusPage = new HomePage(getDriver())
                .clickMenuManageJenkins()
                .clickManageUsers()
                .clickConfigureUser()
                .clearInputFieldFullUserName()
                .inputNameInFieldFullUserName(NEW_USERS_FULL_NAME)
                .clickSaveButton()
                .refreshPage();

        Assert.assertEquals(userStatusPage.getPageHeaderUserName(), NEW_USERS_FULL_NAME);
        Assert.assertEquals(userStatusPage.getBreadcrumbsUserName(), NEW_USERS_FULL_NAME);
        Assert.assertEquals(userStatusPage.getH1Title(), NEW_USERS_FULL_NAME);
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
    public void testRenameUsersFullName() {
        getDriver().findElement(MANAGE_JENKINS).click();
        getDriver().findElement(SECURITY_MANAGE_USERS).click();
        getDriver().findElement(JENKINS_MENU_DROPDOWN).click();
        getWait(1).until(ExpectedConditions.elementToBeClickable(USER_ADMIN_CONFIGURE)).click();
        getDriver().findElement(USER_FULL_NAME).clear();
        getDriver().findElement(USER_FULL_NAME).sendKeys(NEW_USERS_FULL_NAME);
        getDriver().findElement(SAVE_BUTTON).click();

        getDriver().navigate().refresh();

        Assert.assertEquals(getDriver().findElement(H1_TITLE).getText(), NEW_USERS_FULL_NAME);
        Assert.assertEquals(getDriver().findElement(PAGE_HEADER_USER).getText(), NEW_USERS_FULL_NAME);
    }

    @Ignore
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
}