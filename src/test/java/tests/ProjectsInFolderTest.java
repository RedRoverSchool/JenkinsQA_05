package tests;

import model.HomePage;
import model.organization_folder.OrgFolderStatusPage;
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
                .clickDashboard();

        Assert.assertTrue(homePage.getJobList().contains(RANDOM_NAME));
    }

    @Test(dependsOnMethods = "testCreateFolder")
    public void createOrganizationFolderInFolderTest() {
        String actualOrganizationFolderDisplayName = new HomePage(getDriver())
                .clickJob(RANDOM_NAME)
                .clickFolderNewItem()
                .setItemName(RANDOM_NAME)
                .selectOrgFolderAndClickOk()
                .clickSaveBtn(OrgFolderStatusPage.class)
                .getDisplayName();

        Assert.assertEquals(actualOrganizationFolderDisplayName, RANDOM_NAME);
    }
}
