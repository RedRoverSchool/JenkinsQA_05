package tests;

import model.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import static runner.TestUtils.getRandomStr;

public class ProjectsInFolderTest extends BaseTest {
    private static final String RANDOM_NAME = getRandomStr(10);

    @Test
    public void testCreateFolder() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(RANDOM_NAME)
                .selectFolderAndClickOk()
                .getBreadcrumbs()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(RANDOM_NAME));
    }

    @Test(dependsOnMethods = "testCreateFolder")
    public void createOrganizationFolderInFolderTest() {
        String actualOrganizationFolderDisplayName = new HomePage(getDriver())
                .clickFolder(RANDOM_NAME)
                .clickFolderNewItem()
                .setItemName(RANDOM_NAME)
                .selectOrgFolderAndClickOk()
                .clickSaveButton()
                .getNameText();

        Assert.assertEquals(actualOrganizationFolderDisplayName, RANDOM_NAME);
    }
}
