import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class NewFreeStyleProject extends BaseTest {
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
    private static final By FOLDER1_LINK_IN_THE_TABLE = By.xpath("//a[@href='job/Folder1/']");
    private static final By FOLDER2_NAME_IN_THE_TABLE = By.xpath("//span[text()='Folder2']");
    private static final By FOLDER3_NAME_IN_THE_TABLE = By.xpath("//span[text()='Folder3']");
    private static final By CREATE_NEW_ITEM_ICON = By.className("task-icon-link");
    private static final By FOLDER2_DROPBOX_BUTTON = By
            .xpath("//a[@href='job/Folder2/']/button[@class='jenkins-menu-dropdown-chevron']");
    private static final By FOLDER2_MOVE_BUTTON = By.xpath("//*[@href='/job/Folder2/move']");
    private static final By FOLDER2_MOVE_DESTINATION_FIELD = By.name("destination");
    private static final By FOLDER2_MOVE_TO_FOLDER1_FIELD = By
            .xpath("//select[@name='destination']/option[@value='/Folder1']");
    private static final By MOVE_BUTTON = By.id("yui-gen1-button");
    private static final By RENAME_BUTTON = By.xpath("//span[text()='Rename']");
    private static final By RENAME_FIELD = By.xpath("//input[@name='newName']");
    private static final By RENAME_CONFIRM_BUTTON = By.id("yui-gen1-button");
    private static final By DELETE_FOLDER_BUTTON = By.xpath("//span[text()='Delete Folder']");
    private static final By DELETE_CONFIRM_BUTTON = By.id("yui-gen1-button");
    private static final By TABLE_ID = By.xpath("//*[@id='projectstatus']/tbody");
    private static final By FOLDER_ICON_IN_THE_TABLE = By.xpath("//span[text()='Folder']");
    private static final By JENKINS_START_PAGE_WELCOME_TEXT = By
            .xpath("//h1[text()='Добро пожаловать в Jenkins!']");

    @Test
    public void createFreestyleProject() throws InterruptedException {
        String projectName = "AlexProject";
        String descriptionText = "Some text to description area";

        getDriver().findElement(CREATE_NEW_JOB_BUTTON).click();
        getDriver().findElement(NEW_NAME_INPUT_FIELD).sendKeys(projectName);
        getDriver().findElement(CREATE_NEW_FREESTYLE_PROJECT_BUTTON).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(DESCRIPTION_FIELD).sendKeys(descriptionText);
        getDriver().findElement(GITHUB_CHECKBOX).click();
        Thread.sleep(2000);
        getDriver().findElement(GITHUB_CHECKBOX).click();
        Thread.sleep(2000);

//        Assert.assertFalse(getDriver().findElement(GIT_INFO_RAPPER_POINT).isDisplayed());

//        Thread.sleep(2000);
        getDriver().findElement(GIT_INFO_QUESTION_MARK).click();
        Thread.sleep(2000);
        Assert.assertTrue(getDriver().findElement(GIT_INFO_RAPPER_POINT).isDisplayed());

        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD_BUTTON).click();

        String expectedName = getDriver().findElement(FREESTYLE_PROJECT_NAME_IN_THE_TABLE).getText();
        Assert.assertTrue(expectedName.contains(projectName));

    }

    @Test(dependsOnMethods = {"createFreestyleProject"})
    public void deleteFreestyleProject() {

        final String expectedLine = "Start building your software project";

        getDriver().findElement(By.xpath("//*[contains(text(), 'Dashboard')]")).click();

        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//tr/td/a[@href= 'job/AlexProject/']")).isDisplayed());

        getDriver().findElement(By.linkText("AlexProject")).click();
        getDriver().findElement(By.cssSelector(".icon-edit-delete")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h2[@class='h4']"))
                .getText().contains(expectedLine));
    }
}
