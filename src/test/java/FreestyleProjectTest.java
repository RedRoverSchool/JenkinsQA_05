import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class FreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final String FREESTYLE_NAME_WITH_DESCRIPTION = RandomStringUtils.randomAlphanumeric(10);
    private static final String NEW_FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final String FREESTYLE_DESCRIPTION = RandomStringUtils.randomAlphanumeric(10);
    private static final By LINK_NEW_ITEM = By.linkText("New Item");
    private static final By FIELD_ENTER_AN_ITEM_NAME = By.id("name");
    private static final By LINK_FREESTYLE_PROJECT = By.cssSelector(".hudson_model_FreeStyleProject");
    private static final By BUTTON_OK_IN_NEW_ITEM = By.cssSelector("#ok-button");
    private static final By LINK_CHANGES = By.linkText("Changes");
    private static final By BUTTON_SAVE = By.xpath("//button[@type = 'submit']");
    private static final By LIST_FREESTYLE_JOBS = By
            .xpath("//a[@class='jenkins-table__link model-link inside']");
    private static final By EDIT_DESCRIPTION_BUTTON = By.id("description-link");
    private static final By DESCRIPTION_TEXT_FIELD = By.xpath("//textarea[@name = 'description']");
    private static final By DESCRIPTION_SAVE_BUTTON = By.id("yui-gen2-button");
    private static final By DESCRIPTION_TEXT = By.xpath("//div[@id = 'description'] /div[1]");
    private static final By ITEM_NAME_INVALID = By.cssSelector("#itemname-invalid");
    private static final By MAIN_PANEL_LOCATOR = By.id("main-panel");

    private WebDriverWait wait;
    private Actions action;

    private WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        }
        return wait;
    }

    private Actions getAction() {
        if (action == null) {
            action = new Actions(getDriver());
        }
        return action;
    }

    private List<String> getListExistingFreestyleProjectsNames(By by) {
        return getDriver().findElements(by).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    @Test
    public void testCreateNewFreestyleProjectWithCorrectName() {
        getWait().until(ExpectedConditions.elementToBeClickable(LINK_NEW_ITEM)).click();

        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).click();
        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).sendKeys(FREESTYLE_NAME);
        getDriver().findElement(LINK_FREESTYLE_PROJECT).click();
        getWait().until(ExpectedConditions.elementToBeClickable(BUTTON_OK_IN_NEW_ITEM)).click();
        getWait().until(ExpectedConditions.elementToBeClickable(BUTTON_SAVE)).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//h1")).getText(), "Project " + FREESTYLE_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithCorrectName")
    public void testPresentationNewProjectOnDashboard() {

        Assert.assertTrue(getListExistingFreestyleProjectsNames(LIST_FREESTYLE_JOBS).contains(FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithCorrectName", priority = 2)
    public void testRenameFreestyleProject() {

        getDriver().findElement(By.cssSelector("tr#job_" + FREESTYLE_NAME + " .jenkins-menu-dropdown-chevron")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + FREESTYLE_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.cssSelector("input[name='newName']")).clear();
        getDriver().findElement(By.cssSelector("input[name='newName']")).sendKeys(NEW_FREESTYLE_NAME);
        getDriver().findElement(By.cssSelector("#yui-gen1-button")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertFalse(getListExistingFreestyleProjectsNames(LIST_FREESTYLE_JOBS).contains(FREESTYLE_NAME));
        Assert.assertTrue(getListExistingFreestyleProjectsNames(LIST_FREESTYLE_JOBS).contains(NEW_FREESTYLE_NAME));
    }

    @Test(dependsOnMethods = "testRenameFreestyleProject")
    public void testViewChangesNoBuildsSignAppears() {
        String expectedText = "Changes\nNo builds.";

        getDriver().findElement(By.xpath("//span[contains(text(),'" + NEW_FREESTYLE_NAME + "')]")).click();
        getDriver().findElement(LINK_CHANGES).click();

        String actualText = getDriver().findElement(MAIN_PANEL_LOCATOR).getText();

        Assert.assertEquals(actualText, expectedText);
    }

    @Test(dependsOnMethods = "testViewChangesNoBuildsSignAppears", priority = 3)
    public void testDeleteFreestyleProject() {

        getDriver().findElement(By.cssSelector("tr#job_" + NEW_FREESTYLE_NAME + " .jenkins-menu-dropdown-chevron")).click();
        getDriver().findElement(By.xpath("//span[contains(text(),'Delete Project')]")).click();

        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        Assert.assertFalse(getListExistingFreestyleProjectsNames(LIST_FREESTYLE_JOBS).contains(NEW_FREESTYLE_NAME));
    }

    @Test
    public void testCreateFreestyleProjectWithDescription() {
        final String description = "Some Description Text";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(FREESTYLE_NAME_WITH_DESCRIPTION);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
        getDriver().findElement(BUTTON_SAVE).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(),
                description);
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                String.format("Project %s", FREESTYLE_NAME_WITH_DESCRIPTION));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectWithDescription")
    public void testEditFreestyleProjectWithDescription() {
        getDriver().findElement(By.xpath("//span[contains(text(),'" + FREESTYLE_NAME_WITH_DESCRIPTION + "')]")).click();

        getDriver().findElement(EDIT_DESCRIPTION_BUTTON).click();
        getDriver().findElement(DESCRIPTION_TEXT_FIELD).clear();
        getDriver().findElement(DESCRIPTION_TEXT_FIELD).sendKeys(FREESTYLE_DESCRIPTION);
        getDriver().findElement(DESCRIPTION_SAVE_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(DESCRIPTION_TEXT).getText(), FREESTYLE_DESCRIPTION);
    }

    @Test
    public void testCreateFreestyleProjectWithIncorrectCharacters() {
        final List<Character> incorrectNameCharacters = List.of(
                '!', '@', '#', '$', '%', '^', '&', '*', '[', ']', '\\', '|', ';', ':', '/', '?', '<', '>');

        getDriver().findElement(LINK_NEW_ITEM).click();
        for (Character character : incorrectNameCharacters) {
            getAction().moveToElement(getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME)).click().build().perform();
            getAction().moveToElement(getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME))
                    .doubleClick().sendKeys(String.valueOf(character)).build().perform();

            Assert.assertEquals(getDriver().findElement(ITEM_NAME_INVALID).getText(),
                    "» ‘" + character + "’ is an unsafe character");
        }
    }

    @Test(dependsOnMethods = "testCreateNewFreestyleProjectWithCorrectName", priority = 1)
    public void testNoBuildFreestyleProjectChanges() {
        getDriver().findElement(By.linkText(FREESTYLE_NAME)).click();
        getDriver().findElement(LINK_CHANGES).click();

        final String actualChangesText = getDriver().findElement(MAIN_PANEL_LOCATOR)
                .getText().replace("Changes\n", "");

        Assert.assertEquals(actualChangesText, "No builds.");
    }

    @Test
    public void testFreestyleProjectPageIsOpenedFromDashboard() {
        getDriver().findElement(LINK_NEW_ITEM).click();
        getDriver().findElement(FIELD_ENTER_AN_ITEM_NAME).sendKeys(FREESTYLE_NAME);
        getDriver().findElement(LINK_FREESTYLE_PROJECT).click();
        getDriver().findElement(BUTTON_OK_IN_NEW_ITEM).click();

        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//span[@name='Submit']")))).click();

        getDriver().findElement(By.linkText("Dashboard")).click();

        getAction().moveToElement(
                getDriver().findElement(By.linkText(FREESTYLE_NAME))).click().build().perform();

        String actualH1 = getWait().until(ExpectedConditions.visibilityOf(
                getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")))).getText();
        String actualH2 = getDriver().findElement(By.xpath("//div[@id='main-panel']/h2")).getText();

        Assert.assertEquals(actualH1, "Project " + FREESTYLE_NAME);
        Assert.assertEquals(actualH2, "Permalinks");
        Assert.assertTrue(getDriver().findElement(By.cssSelector("#yui-gen1")).isDisplayed());
    }
}