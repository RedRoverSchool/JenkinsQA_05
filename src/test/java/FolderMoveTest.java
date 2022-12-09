import model.HomePage;
import model.StatusPage;

import org.apache.commons.lang3.RandomStringUtils;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import runner.BaseTest;

public class FolderMoveTest extends BaseTest {

    private static final String folderName1 = RandomStringUtils.randomAlphanumeric(5);
    private static final String folderName2 = RandomStringUtils.randomAlphanumeric(5);

    private void createFolder(String folderName) {
        new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(folderName)
                .selectFolderAndClickOk()
                .clickDashboard();
    }

    @Ignore
    @Test
    public void testMoveFolderToFolder() {
        createFolder(folderName1);
        createFolder(folderName2);

        HomePage homePage = new HomePage(getDriver())
                .clickFolderDropdownMenu(folderName1)
                .clickMoveButtonDropdown()
                .selectFolder(folderName2)
                .clickMove()
                .clickDashboard();

        Assert.assertFalse(homePage.getJobList().contains(folderName1));

        StatusPage statusPage = new HomePage(getDriver())
                .clickFolder(folderName2);

        Assert.assertTrue(statusPage.getJobList().contains(folderName1));
    }
}