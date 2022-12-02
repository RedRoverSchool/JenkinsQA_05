import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizationFolderTest extends BaseTest {
    private static final String uniqueOrganizationFolderName = "folder" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    private static final String ORG_FOLDER_NAME = TestUtils.getRandomStr();
    private static final By INPUT_NAME = By.xpath("//input [@name = 'name']");
    private static final By INPUT_DISPLAY_NAME = By.xpath("//input  [@name='_.displayNameOrNull']");
    private static final By DESCRIPTION = By.xpath("//textarea [@name='_.description']");
    private static final By ORGANIZATION_FOLDER = By.xpath("//li[@class = 'jenkins_branch_OrganizationFolder']");
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By DASHBOARD = By.xpath("//a[text()='Dashboard']");
    private static final By APPLY_BUTTON = By.id("yui-gen13-button");
    private static final By SAVE_BUTTON = By.id("yui-gen15-button");
    private static final By INPUT_LINE = By.name("newName");
    private static final By RENAME_BUTTON = By.id("yui-gen1-button");
    private static final By TITLE = By.xpath("//div[@id='main-panel']/h1");
    private static final By NEW_ITEM = By.linkText("New Item");
    private static final By BUTTON_DELETE_ORGANIZATION_FOLDER = By.xpath("//div[@id='tasks']//a[contains(@href, 'delete')]");
    private static final By BUTTON_SUBMIT = By.xpath("//button[@type= 'submit']");

    private static final String NAME_ORG_FOLDER = TestUtils.getRandomStr();
    private static final String NAME_FOLDER = TestUtils.getRandomStr();

    private static final By ITEM_FOLDER = By.xpath("//span[text()='" + NAME_FOLDER + "']");

    private static final By ITEM_ORG_FOLDER = By.xpath("//span[text()= '" + NAME_ORG_FOLDER + "']");

    private WebElement notificationSaved() {
        return getDriver().findElement(By.cssSelector("#notification-bar"));
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(5));
    }

    public WebElement getInputName() {
        return getDriver().findElement(INPUT_NAME);
    }

    public WebElement getOrganizationFolder() {
        return getDriver().findElement(ORGANIZATION_FOLDER);
    }

    public WebElement getOkButton() {
        return getDriver().findElement(OK_BUTTON);
    }

    public WebElement getDashboard() {
        return getDriver().findElement(DASHBOARD);
    }

    public WebElement getInputLine() {
        return getDriver().findElement(INPUT_LINE);
    }

    public WebElement getSaveButton() {
        return getDriver().findElement(SAVE_BUTTON);
    }

    private void createNewOrganizationFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(uniqueOrganizationFolderName);
        getDriver().findElement(ORGANIZATION_FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
    }

    private boolean isElementExist(String name) {
        try {
            getDriver().findElement(By.xpath("//span[text()='" + name + "']"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Test
    public void testCreateOrganizationFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getInputName().sendKeys(uniqueOrganizationFolderName + "2");
        getOrganizationFolder().click();
        getOkButton().click();
        getSaveButton().click();

        Assert.assertEquals(getDriver().findElement(TITLE).getText(), uniqueOrganizationFolderName + "2");
    }

    @Test
    public void testRenameOrganizationFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getInputName().sendKeys("Existing Organization Name");
        getOrganizationFolder().click();
        getOkButton().click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.linkText("Rename")).click();
        getInputLine().clear();
        getInputLine().sendKeys("New Organization Folder");
        getDriver().findElement(RENAME_BUTTON).click();
        getDashboard().click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='job/New%20Organization%20Folder/']"))
                .getText(), "New Organization Folder");
    }

    @Test
    public void testRenameOrganizationFolder1() {
        createNewOrganizationFolder();

        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(INPUT_LINE).clear();
        getDriver().findElement(INPUT_LINE).sendKeys(uniqueOrganizationFolderName + "1");
        getDriver().findElement(RENAME_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(TITLE).getText(), uniqueOrganizationFolderName + "1");
    }

    @Test
    public void testCreateOrganizationFolderWithCheckOnDashbord() {
        final String organizationFolderName = "OrganizationFolder_" + (int) (Math.random() * 100);
        boolean actualResult = false;

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.cssSelector(".jenkins_branch_OrganizationFolder")).click();
        getInputName().sendKeys(organizationFolderName);
        getOkButton().click();
        getDriver().findElement(By.xpath("//li[@class='item']/a[@href='/']")).click();
        List<WebElement> list = getDriver().findElements(By.cssSelector(".jenkins-table__link.model-link.inside span"));

        Assert.assertTrue(list.size() > 0);

        for (WebElement a : list) {
            if (a.getText().equals(organizationFolderName)) {
                actualResult = true;
                break;
            }
        }

        Assert.assertTrue(actualResult);
    }

    @Test
    public void testCreateOrgFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(uniqueOrganizationFolderName + 5);
        getDriver().findElement(ORGANIZATION_FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();

        Assert.assertEquals(getDriver().
                findElement(By.xpath("//div[@id='main-panel']/h1")).getText(), uniqueOrganizationFolderName + 5);
    }

    @Test
    public void testCreateOrgFolderEmptyName() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(ORGANIZATION_FOLDER).click();

        Assert.assertEquals(getDriver().
                        findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(getDriver().findElement(OK_BUTTON).isEnabled());
    }

    @Test
    public void testOrgFolderCreation() {
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(INPUT_NAME).sendKeys(NAME_ORG_FOLDER);
        WebElement element = getDriver().findElement(By.className("jenkins_branch_OrganizationFolder"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(By.id("yui-gen15-button")).click();
        getDashboard().click();

        Assert.assertTrue(getDriver().findElement(ITEM_ORG_FOLDER).isDisplayed());
    }

    @Test
    public void testFolderCreation() {
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(INPUT_NAME).sendKeys(NAME_FOLDER);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(By.id("yui-gen6-button")).click();
        getDashboard().click();

        Assert.assertTrue(getDriver().findElement(ITEM_FOLDER).isDisplayed());
    }

    @Test(dependsOnMethods = {"testFolderCreation", "testOrgFolderCreation"})
    public void testMoveOrgFolderToFolder() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        wait.until(ExpectedConditions.elementToBeClickable(ITEM_ORG_FOLDER));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",
                getDriver().findElement(ITEM_ORG_FOLDER));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();",
                getDriver().findElement(ITEM_ORG_FOLDER));
        getDriver().findElement(By.linkText("Move")).click();
        getDriver().findElement(By.name("destination")).click();
        getDriver().findElement(By.xpath("//option[contains(text(),'" + NAME_FOLDER + "')]")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
        getDashboard().click();

        wait.until(ExpectedConditions.visibilityOf(getDriver().findElement(By.className("dashboard"))));
        WebElement myFolder = getDriver().findElement(ITEM_FOLDER);

        Assert.assertFalse(isElementExist(NAME_ORG_FOLDER));
        Assert.assertTrue(myFolder.isDisplayed());

        wait.until(ExpectedConditions.elementToBeClickable(myFolder));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", myFolder);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", myFolder);

        Assert.assertTrue(getDriver().findElement(ITEM_ORG_FOLDER).isDisplayed());
    }

    @Test
    public void testCheckNotificationAfterApply() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(ORG_FOLDER_NAME);
        getDriver().findElement(ORGANIZATION_FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(APPLY_BUTTON).click();

        Assert.assertEquals(getWait().until(ExpectedConditions.visibilityOf(notificationSaved()))
                .getText(), "Saved");
        Assert.assertEquals(notificationSaved().getAttribute("class")
                , "notif-alert-success notif-alert-show");
    }

    @Test
    public void testCreateOrgFolderExistName() {
        createNewOrganizationFolder();
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(uniqueOrganizationFolderName);
        getDriver().findElement(ORGANIZATION_FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h1['Error']")).isDisplayed());
    }

    @Test(dependsOnMethods = "testMoveOrgFolderToFolder")
    public void testMoveOrgFolderToDashboard() {
        getDashboard().click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        wait.until(ExpectedConditions.elementToBeClickable(ITEM_FOLDER));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",
                getDriver().findElement(ITEM_FOLDER));
        getDriver().findElement(ITEM_FOLDER).click();

        wait.until(ExpectedConditions.elementToBeClickable(ITEM_ORG_FOLDER));
        getDriver().findElement(ITEM_ORG_FOLDER).click();

        getDriver().findElement(By.linkText("Move")).click();
        getDriver().findElement(By.name("destination")).click();
        getDriver().findElement(By.xpath("//option[text()='Jenkins']")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
        getDashboard().click();

        wait.until(ExpectedConditions.visibilityOf(getDriver().findElement(By.className("dashboard"))));

        Assert.assertTrue(getDriver().findElement(ITEM_ORG_FOLDER).isDisplayed());

        WebElement myFolder = getDriver().findElement(ITEM_FOLDER);
        wait.until(ExpectedConditions.elementToBeClickable(myFolder));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", myFolder);
        myFolder.click();

        Assert.assertFalse(isElementExist(NAME_ORG_FOLDER));
        Assert.assertEquals(getDriver().findElement(By.xpath("//h2[@class='h4']")).getText(),
                "This folder is empty");
    }

    @Test(dependsOnMethods = "testCreateOrgFolder")
    public void testDeleteOrganizationFolder() {
        final By itemInDashboard = By.xpath("//span[text()='" + uniqueOrganizationFolderName + 5 + "']");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        wait.until(ExpectedConditions.elementToBeClickable(itemInDashboard));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",
                getDriver().findElement(itemInDashboard));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();",
                getDriver().findElement(itemInDashboard));
        wait.until(ExpectedConditions.elementToBeClickable(BUTTON_DELETE_ORGANIZATION_FOLDER));
        getDriver().findElement(BUTTON_DELETE_ORGANIZATION_FOLDER).click();
        wait.until(ExpectedConditions.elementToBeClickable(BUTTON_SUBMIT));
        getDriver().findElement(BUTTON_SUBMIT).click();

        List<String> foldersList = getDriver()
                .findElements(By.xpath("//tr/td[3]/a/span"))
                .stream()
                .map(element -> element.getText())
                .collect(Collectors.toList());

        Assert.assertFalse(foldersList.contains(uniqueOrganizationFolderName + 5));
    }

    @Test
    public void testConfigureOrganizationFolder() {
        String displayName = "Test Configuration Display Name";
        String descriptionText = "Test Description";

        getDriver().findElement(By.linkText("New Item")).click();
        getInputName().sendKeys(NAME_ORG_FOLDER);
        getOrganizationFolder().click();
        getOkButton().click();
        getDriver().findElement(BUTTON_SUBMIT).click();
        getDriver().findElement(By.linkText("Configure")).click();
        getDriver().findElement(INPUT_DISPLAY_NAME).sendKeys(displayName);
        getDriver().findElement(DESCRIPTION).sendKeys(descriptionText);
        getSaveButton().click();
        getDashboard().click();

        List<String> foldersList = getDriver()
                .findElements(By.xpath("//tr/td[3]/a/span"))
                .stream()
                .map(element -> element.getText())
                .collect(Collectors.toList());

        Assert.assertTrue(foldersList.contains(displayName));
    }
}
