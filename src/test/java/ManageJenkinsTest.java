import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

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
}