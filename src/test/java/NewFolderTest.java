import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class NewFolderTest extends BaseTest {

    private static final By CREATE_NEW_JOB_BUTTON = By.xpath("//a[@href='newJob']");
    private static final By NEW_NAME_INPUT_FIELD = By.className("jenkins-input");
    private static final By CREATE_NEW_FOLDER_BUTTON = By.className("com_cloudbees_hudson_plugins_folder_Folder");
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By SAVE_BUTTON = By.name("Submit");
    private static final By DASHBOARD_BUTTON = By.xpath("//a[contains(text(),'Dashboard')]");
    private static final By FOLDER1_NAME_IN_THE_TABLE = By.xpath("//span[text()='Folder1']");
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
    public void Folder1CreateTest() {

        String folder1Name = "Folder1";

        getDriver().findElement(CREATE_NEW_JOB_BUTTON).click();
        getDriver().findElement(NEW_NAME_INPUT_FIELD).sendKeys(folder1Name);
        getDriver().findElement(CREATE_NEW_FOLDER_BUTTON).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD_BUTTON).click();

        String actualFolderName = getDriver().findElement(FOLDER1_NAME_IN_THE_TABLE).getText();

        Assert.assertEquals(actualFolderName, folder1Name);
    }

    @Test(dependsOnMethods = {"Folder1CreateTest"})
    public void Folder2CreateTest() {

        String folder2Name = "Folder2";

        getDriver().findElement(CREATE_NEW_ITEM_ICON).click();
        getDriver().findElement(NEW_NAME_INPUT_FIELD).sendKeys(folder2Name);
        getDriver().findElement(CREATE_NEW_FOLDER_BUTTON).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD_BUTTON).click();

        String actualFolderName = getDriver().findElement(FOLDER2_NAME_IN_THE_TABLE).getText();

        Assert.assertEquals(actualFolderName, folder2Name);
    }

    @Test(dependsOnMethods = {"Folder2CreateTest"})
    public void Folder2MoveToFolder1Test() {
        String folder2Name = "Folder2";

        getDriver().findElement(FOLDER2_NAME_IN_THE_TABLE).click();
        getDriver().findElement(FOLDER2_MOVE_BUTTON).click();
        getDriver().findElement(FOLDER2_MOVE_DESTINATION_FIELD).click();
        getDriver().findElement(FOLDER2_MOVE_TO_FOLDER1_FIELD).click();
        getDriver().findElement(MOVE_BUTTON).click();
        getDriver().findElement(DASHBOARD_BUTTON).click();
        getDriver().findElement(FOLDER1_LINK_IN_THE_TABLE).click();

//        String actualFolderName = getDriver().findElement(FOLDER2_NAME_IN_THE_TABLE).getText();
//
//        Assert.assertEquals(actualFolderName, folder2Name);
    }

    @Test(dependsOnMethods = {"Folder2MoveToFolder1Test"})
    public void Folder1RenameToFolder3Test() {
        String newName = "Folder3";
        int numOfRowsExpected = 1;

        getDriver().findElement(FOLDER1_LINK_IN_THE_TABLE).click();
        getDriver().findElement(RENAME_BUTTON).click();
        getDriver().findElement(RENAME_FIELD).clear();
        getDriver().findElement(RENAME_FIELD).sendKeys(newName);
        getDriver().findElement(RENAME_CONFIRM_BUTTON).click();
        getDriver().findElement(DASHBOARD_BUTTON).click();

        int numOfRows = getDriver().findElements(FOLDER_ICON_IN_THE_TABLE).size();

        String actualFolderName = getDriver().findElement(FOLDER3_NAME_IN_THE_TABLE).getText();

        Assert.assertEquals(actualFolderName, newName);
        Assert.assertEquals(numOfRows, numOfRowsExpected);
    }

    @Test(dependsOnMethods = {"Folder1RenameToFolder3Test"})
    public void DeleteFolder3Test() {

        String welcomeNote = "Добро пожаловать в Jenkins!";

        getDriver().findElement(FOLDER3_NAME_IN_THE_TABLE).click();
        getDriver().findElement(DELETE_FOLDER_BUTTON).click();
        getDriver().findElement(DELETE_CONFIRM_BUTTON).click();

        String actualFolderName = getDriver().findElement(JENKINS_START_PAGE_WELCOME_TEXT).getText();

        Assert.assertEquals(actualFolderName, welcomeNote);
    }

}
