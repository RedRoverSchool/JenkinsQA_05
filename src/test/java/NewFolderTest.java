import model.HomePage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class NewFolderTest extends BaseTest {
    private String folder1Name;
    private String folder2Name;
    private String folder3Name;

    private static final By FOLDER_ICON_IN_THE_TABLE = By.xpath("//span[text()='Folder']");

    @Test
    public void Folder1CreateTest() {
        folder1Name = "Folder1";

        String actualFolderName = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(folder1Name)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickDashboard()
                .getItemName();

        Assert.assertEquals(actualFolderName, folder1Name);
    }

    @Test(dependsOnMethods = {"Folder1CreateTest"})
    public void Folder2CreateTest() {
        folder2Name = "Folder2";

        String actualFolderName = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(folder2Name)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickDashboard()
                .getJobList().get(1);

        Assert.assertEquals(actualFolderName, folder2Name);
    }

    @Test(dependsOnMethods = {"Folder2CreateTest"})
    public void Folder2MoveToFolder1Test() {

        String actualFolderName = new HomePage(getDriver())
                .clickFolder(folder2Name)
                .clickMoveFolder()
                .selectFolder(folder1Name)
                .clickMove()
                .clickDashboard()
                .clickFolder(folder1Name)
                .getJobList().get(0);

        Assert.assertEquals(actualFolderName, folder2Name);
    }

    @Test(dependsOnMethods = {"Folder2MoveToFolder1Test"})
    public void Folder1RenameToFolder3Test() {
        folder3Name = "Folder3";
        int numOfRowsExpected = 1;

        String actualFolderName = new HomePage(getDriver())
                .clickFolder(folder1Name)
                .clickRename(folder1Name)
                .clearAndSetNewName(folder3Name)
                .clickSubmitButton()
                .clickDashboard()
                .getItemName();

        int numOfRows = getDriver().findElements(FOLDER_ICON_IN_THE_TABLE).size();

        Assert.assertEquals(actualFolderName, folder3Name);
        Assert.assertEquals(numOfRows, numOfRowsExpected);
    }

    @Test(dependsOnMethods = {"Folder1RenameToFolder3Test"})
    public void DeleteFolder3Test() {
        String welcomeNote = "Start building your software project";

        String actualFolderName = new HomePage(getDriver())
                .clickFolder(folder3Name)
                .clickDeleteFolder()
                .clickSubmitButton()
                .getStartBuildingYourSoftWareProjectText();

        Assert.assertEquals(actualFolderName, welcomeNote);
    }
}
