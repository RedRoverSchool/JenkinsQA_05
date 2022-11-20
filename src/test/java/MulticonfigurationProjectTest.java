import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;

public class MulticonfigurationProjectTest extends BaseTest {
    private static final String PROJECT_NAME = RandomStringUtils.randomAlphanumeric(8);
    private static final String NEW_PROJECT_NAME = RandomStringUtils.randomAlphanumeric(8);
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By DASHBOARD = By.xpath("//img[@id='jenkins-head-icon']");
    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By SAVE_BUTTON = By.xpath("//button[@type='submit']");
    private static final By INPUT_NAME = By.id("name");
    private static final By CONFIGURE = By.xpath(String.format("//a[@href='/job/%s/configure']", PROJECT_NAME));
    private WebDriverWait wait;

    private void deleteNewMCProject() {
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", PROJECT_NAME))).click();
        getDriver().findElement(By.xpath(String.format("//a[@href = contains(., '%s')]/button", PROJECT_NAME))).click();
        getDriver().findElement(
                By.xpath("//div[@id = 'tasks']//span[contains(text(), 'Delete Multi-configuration project')]")).click();
        getDriver().switchTo().alert().accept();
    }

    @Test
    public void testCreateMultiConfigurationProjectWithValidName_HappyPath() {
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(INPUT_NAME).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[contains(text(), 'Multi-configuration project')]")).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD).click();

        Assert.assertEquals(getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", PROJECT_NAME)))
                .getText(), PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectWithValidName_HappyPath")
    public void testMulticonfigurationProjectAddDescription() {
        final String description = "Description";

        getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", PROJECT_NAME))).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(description);
        getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description div")).getText(), description);
    }

    @Test(dependsOnMethods = "testMulticonfigurationProjectAddDescription")
    public void testMultiConfigurationProjectRenameProjectViaDropDownMenu() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        getDriver().findElement(
                By.xpath("//a[@class='jenkins-table__link model-link inside']//button[@class='jenkins-menu-dropdown-chevron']")).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//span[text() = 'Rename']"))).click();
        getDriver().findElement(By.xpath(String.format("//input[@value='%s']", PROJECT_NAME))).clear();
        getDriver().findElement(By.xpath(String.format("//input[@value='%s']", PROJECT_NAME))).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.id("yui-gen1-button")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath(String.format("//h1[contains(text(),'Project %s')]", NEW_PROJECT_NAME))).getText(), String.format("Project %s", NEW_PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectRenameProjectViaDropDownMenu")
    public void testMultiConfigurationProjectRenameProjectViaSideMenu() {

        getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", NEW_PROJECT_NAME))).click();
        getDriver().findElement(By.xpath(String.format("//a[@href='/job/%s/confirm-rename']", NEW_PROJECT_NAME))).click();
        getDriver().findElement(By.xpath(String.format("//input[@value='%s']", NEW_PROJECT_NAME))).clear();
        getDriver().findElement(By.xpath(String.format("//input[@value='%s']", NEW_PROJECT_NAME))).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[contains(text(),'Rename')]")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath(String.format("//h1[contains(text(),'Project %s')]", PROJECT_NAME))).getText(),
                String.format("Project %s", PROJECT_NAME));
    }

    @Ignore
    @Test
    public void testMultiConfigurationProjectDelete() {

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        getDriver().findElement(By.xpath("//tr[@id = 'job_FirstMultiProject']/descendant::td//button")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Delete Multi-configuration project')]")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectRenameProjectViaSideMenu")
    public void testMultiConfigurationProjectDisable() {
        getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", PROJECT_NAME))).click();
        getDriver().findElement(CONFIGURE).click();
        getDriver().findElement(By.xpath("//label[@for='enable-disable-project']")).click();
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertTrue(getDriver().findElement(By.id("enable-project"))
                .getText().contains("This project is currently disabled"));

        deleteNewMCProject();
    }
}
