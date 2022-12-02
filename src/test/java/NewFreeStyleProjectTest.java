import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import static runner.TestUtils.getRandomStr;

public class NewFreeStyleProjectTest extends BaseTest {
    private String renameNewName;
    private static final By CREATE_NEW_JOB_BUTTON = By.xpath("//a[@href='newJob']");
    private static final By NEW_NAME_INPUT_FIELD = By.className("jenkins-input");
    private static final By CREATE_NEW_FREESTYLE_PROJECT_BUTTON = By.className("hudson_model_FreeStyleProject");
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By DESCRIPTION_FIELD = By.xpath("//textarea[@name='description']");
    private static final By GITHUB_CHECKBOX = By.xpath("//label[text()='GitHub project']");
    private static final By GIT_INFO_QUESTION_MARK = By
            .xpath("//div[@class='jenkins-radio']/following-sibling::a");
    private static final By GIT_INFO_RAPPER_POINT = By.xpath("//a[@href='https://plugins.jenkins.io/git/']");
    private static final By SAVE_BUTTON = By.name("Submit");
    private static final By DASHBOARD_BUTTON = By.xpath("//a[contains(text(),'Dashboard')]");
    private static final By FREESTYLE_PROJECT_NAME_IN_THE_TABLE = By.xpath("//span[text()='AlexProject']");
    private static final By TIME_TRIGGER_CHECKBOX = By
            .xpath("//input[@name='hudson-triggers-TimerTrigger']/following-sibling::label");
    private static final By TIME_TRIGGER_FIELD = By.name("_.spec");
    private static final By ANT_CHECKBOX = By
            .xpath("//input[@name='hudson-tasks-AntWrapper']/following-sibling::label");
    private static final By RENAME_BUTTON = By.xpath("//a[@href='/job/AlexProject/confirm-rename']");
    private static final By RENAME_FIELD = By.xpath("//input[@checkdependson='newName']");
    private static final By RENAME_CONFIRM_BUTTON = By.id("yui-gen1");
    @Test
    public void createFreestyleProjectTest() throws InterruptedException {
        String projectName = "AlexProject";
        String descriptionText = "Some text to description area";
        String timeTriggerText = "H H 1,15 1-11 *";

        getDriver().findElement(CREATE_NEW_JOB_BUTTON).click();
        getDriver().findElement(NEW_NAME_INPUT_FIELD).sendKeys(projectName);
        getDriver().findElement(CREATE_NEW_FREESTYLE_PROJECT_BUTTON).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(DESCRIPTION_FIELD).sendKeys(descriptionText);
        getDriver().findElement(GITHUB_CHECKBOX).click();
        getDriver().findElement(GITHUB_CHECKBOX).click();
        getDriver().findElement(GIT_INFO_QUESTION_MARK).click();

        Assert.assertTrue(getDriver().findElement(GIT_INFO_RAPPER_POINT).isDisplayed());

        WebElement checkBox = getDriver().findElement(TIME_TRIGGER_CHECKBOX);
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", checkBox);

        Thread.sleep(1000);
        checkBox.click();
        getDriver().findElement(TIME_TRIGGER_FIELD).sendKeys(timeTriggerText);

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

        getDriver().findElement(ANT_CHECKBOX).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD_BUTTON).click();

        String expectedName = getDriver().findElement(FREESTYLE_PROJECT_NAME_IN_THE_TABLE).getText();
        Assert.assertTrue(expectedName.contains(projectName));
    }

    @Test(dependsOnMethods = {"createFreestyleProjectTest"})
    public void renameFreeStyleProjectTest() {
        renameNewName = getRandomStr(5);
        getDriver().findElement(By.linkText("AlexProject")).click();
        getDriver().findElement(RENAME_BUTTON).click();
        getDriver().findElement(RENAME_FIELD).clear();
        getDriver().findElement(RENAME_FIELD).sendKeys(renameNewName);
        getDriver().findElement(RENAME_CONFIRM_BUTTON).click();
        getDriver().findElement(DASHBOARD_BUTTON).click();
        String expectedName = getDriver().findElement(By.xpath("//span[text()='" + renameNewName + "']")).getText();
        Assert.assertTrue(expectedName.contains(renameNewName));

    }

    @Test(dependsOnMethods = {"renameFreeStyleProjectTest"})
    public void renameFreeStyleProjectNegativeTest() {
        String expectedPhrase = "The new name is the same as the current name.";
        getDriver().findElement(By.xpath("//a[@href='job/" + renameNewName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + renameNewName + "/confirm-rename']")).click();
        //a[@href='job/ELdwI/']
//        getDriver().findElement(RENAME_FIELD).clear();
//        getDriver().findElement(RENAME_FIELD).sendKeys(renameNewName);
        getDriver().findElement(RENAME_CONFIRM_BUTTON).click();
//        getDriver().findElement(DASHBOARD_BUTTON).click();
        String actualPhrase = getDriver().findElement(By.tagName("p")).getText();

        Assert.assertEquals(actualPhrase, expectedPhrase);

    }

    @Test(dependsOnMethods = {"renameFreeStyleProjectNegativeTest"})
    public void deleteFreestyleProjectTest() {

        final String expectedLine = "Start building your software project";

        getDriver().findElement(By.xpath("//*[contains(text(), 'Dashboard')]")).click();

        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//span[text()='" + renameNewName + "']")).isDisplayed());

        getDriver().findElement(By.xpath("//span[text()='" + renameNewName + "']")).click();
        getDriver().findElement(By.cssSelector(".icon-edit-delete")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h2[@class='h4']"))
                .getText().contains(expectedLine));
    }
}
