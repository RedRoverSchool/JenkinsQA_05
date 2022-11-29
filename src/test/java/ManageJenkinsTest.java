import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

public class ManageJenkinsTest extends BaseTest {
    private static final String NEW_USERS_FULL_NAME = TestUtils.getRandomStr();
    private static final By MANAGE_JENKINS = By.linkText("Manage Jenkins");
    private static final By SECURITY_MANAGE_USERS = By.xpath("//a[@href='securityRealm/']");
    private static final By JENKINS_MENU_DROPDOWN = By.cssSelector("a[href='user/admin/'] > .jenkins-menu-dropdown-chevron");
    private static final By USER_ADMIN_CONFIGURE = By.cssSelector("a[href='/user/admin/configure']");
    private static final By USER_FULL_NAME = By.xpath("//input[@name='_.fullName']");
    private static final By SAVE_BUTTON = By.id("yui-gen3-button");
    private static final By H1_TITLE = By.xpath("//h1");
    private static final By PAGE_HEADER_USER = By.cssSelector(".model-link > .hidden-xs.hidden-sm");

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
    public void testRenameUsersFullName() {
        getDriver().findElement(MANAGE_JENKINS).click();
        getDriver().findElement(SECURITY_MANAGE_USERS).click();
        getDriver().findElement(JENKINS_MENU_DROPDOWN).click();
        getDriver().findElement(USER_ADMIN_CONFIGURE).click();
        getDriver().findElement(USER_FULL_NAME).clear();
        getDriver().findElement(USER_FULL_NAME).sendKeys(NEW_USERS_FULL_NAME);
        getDriver().findElement(SAVE_BUTTON).click();

        getDriver().navigate().refresh();

        Assert.assertEquals(getDriver().findElement(H1_TITLE).getText(), NEW_USERS_FULL_NAME);
        Assert.assertEquals(getDriver().findElement(PAGE_HEADER_USER).getText(), NEW_USERS_FULL_NAME);
    }
}