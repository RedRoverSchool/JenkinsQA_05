import model.FolderStatusPage;
import model.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

public class FolderOneTest extends BaseTest {

    private static final String RANDOM_NAME_1 = TestUtils.getRandomStr(6);
    private static final String RANDOM_NAME_2 = TestUtils.getRandomStr(6);
    private static final String RANDOM_MULTIBRANCH_PIPELINE_NAME = TestUtils.getRandomStr(6);

    private void createFolder(String folderName) {
        new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(folderName)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickDashboard();
    }

    @Test
    public void testCreateNewFolder() {
        createFolder(RANDOM_NAME_1);

        HomePage homePage = new HomePage(getDriver());

        Assert.assertTrue(homePage.getJobList().contains(RANDOM_NAME_1));
    }

    @Test(dependsOnMethods = "testCreateNewFolder")
    public void testCreateFolderInFolder() {
        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickFolder(RANDOM_NAME_1)
                .clickFolderNewItem()
                .setProjectName(RANDOM_NAME_2)
                .selectFolderAndClickOk()
                .clickSaveButton();

        Assert.assertTrue(folderStatusPage.getHeaderText().contains(RANDOM_NAME_2));
        Assert.assertTrue(folderStatusPage.getTopMenueLinkText().contains(RANDOM_NAME_2));
        Assert.assertTrue(folderStatusPage.getTopMenueLinkText().contains(RANDOM_NAME_1));
    }

    @Test
    public void testConfigureFolderDisplayName() {
        createFolder(RANDOM_NAME_1);

        HomePage homePage = new HomePage(getDriver())
                .clickFolderDropdownMenu(RANDOM_NAME_1)
                .clickConfigDropDownMenu()
                .setProjectName(RANDOM_NAME_2)
                .clickSaveButton()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobList().contains(RANDOM_NAME_2));
    }

    @Test(dependsOnMethods = "testConfigureFolderDisplayName")
    public void testAddFolderDescription() {
        FolderStatusPage folderStatusPage = new HomePage(getDriver())

                .clickFolderDropdownMenu(RANDOM_NAME_1)
                .clickConfigDropDownMenu()
                .setDescription("Folder description")
                .clickSaveButton();

        Assert.assertTrue(folderStatusPage.getDescriptionText().contains("Folder description"));
    }

    @Test(dependsOnMethods = "testAddFolderDescription")
    public void testRenameFolderDescription() {
        HomePage homePage = new HomePage(getDriver())

                .clickFolderDropdownMenu(RANDOM_NAME_1)
                .clickRenameDropDownMenu()
                .clearFieldAndInputNewName(RANDOM_NAME_2)
                .clickSubmitButton()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobList().contains(RANDOM_NAME_2));
    }

    @Test
    public void testDeleteFolder() {
        createFolder(RANDOM_NAME_1);

        HomePage homePage = new HomePage(getDriver())
                .clickFolder(RANDOM_NAME_1)
                .clickDeleteFolder()
                .clickSubmitButton()
                .clickDashboard();

        Assert.assertFalse(homePage.getJobList().contains(RANDOM_NAME_1));
    }

    @Test
    public void testCreateFolderInFolderJob() {
        createFolder(RANDOM_NAME_1);

        new HomePage(getDriver()).clickFolder(RANDOM_NAME_1);

        createFolder(RANDOM_NAME_2);

        new HomePage(getDriver()).clickFolder(RANDOM_NAME_1);

        FolderStatusPage statusPage = new HomePage(getDriver())
                .clickFolder(RANDOM_NAME_2);

        Assert.assertTrue(statusPage.getHeaderText().contains(RANDOM_NAME_2));
        Assert.assertTrue(statusPage.getTopMenueLinkText().contains(RANDOM_NAME_1));
        Assert.assertTrue(statusPage.getTopMenueLinkText().contains(RANDOM_NAME_2));
    }


    @Test(dependsOnMethods = "testCreateFolderInFolderJob")
    public void testRenameFolder() {
        HomePage homePage = new HomePage(getDriver())
                .clickFolderDropdownMenu(RANDOM_NAME_1)
                .clickRenameDropDownMenu()
                .clearFieldAndInputNewName(RANDOM_NAME_1 + "NEW")
                .clickSubmitButton()
                .clickDashboard();

        Assert.assertFalse(homePage.getJobList().contains(RANDOM_NAME_1));
        Assert.assertTrue(homePage.getJobList().contains(RANDOM_NAME_1 + "NEW"));
    }


    @Test(dependsOnMethods = "testRenameFolder")
    public void testMoveFolderInFolder() {
        createFolder(RANDOM_NAME_1);

        FolderStatusPage statusPage = new HomePage(getDriver())
                .clickFolder(RANDOM_NAME_1)
                .clickMoveFolder()
                .selectFolder(RANDOM_NAME_1 + "NEW")
                .clickMove()
                .clickDashboard()
                .clickFolder(RANDOM_NAME_1 + "NEW");

        Assert.assertTrue(statusPage.getJobList().contains(RANDOM_NAME_1));
    }

    @Test(dependsOnMethods = "testMoveFolderInFolder")
    public void testDeleteFolderDropDown() {
        HomePage homePage = new HomePage(getDriver())
                .clickFolderDropdownMenu(RANDOM_NAME_1 + "NEW")
                .clickDeleteDropDownMenu()
                .clickSubmitDeleteProject();

        Assert.assertFalse(homePage.getJobList().contains(RANDOM_NAME_1 + "NEW"));
    }

    @Test
    public void testCreateNewFolderWithPipeline() {
        createFolder(RANDOM_NAME_1);

        FolderStatusPage actualResult = new HomePage(getDriver())
                .clickFolder(RANDOM_NAME_1)
                .clickFolderNewItem()
                .setProjectName(RANDOM_MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipeline()
                .clickOKButton()
                .clickSubmitButton();

        Assert.assertTrue(actualResult.getTopMenueLinkText().contains(RANDOM_NAME_1));
        Assert.assertTrue(actualResult.getTopMenueLinkText().contains(RANDOM_MULTIBRANCH_PIPELINE_NAME));
        Assert.assertTrue(actualResult.getHeaderText().contains(RANDOM_MULTIBRANCH_PIPELINE_NAME));
    }

    @Test
    public void testCreateNewFolderPipelineOptionJob() {
        createFolder(RANDOM_NAME_1);

        FolderStatusPage actualResult = new HomePage(getDriver())
                .clickFolder(RANDOM_NAME_1)
                .clickCreateJob()
                .setProjectName(RANDOM_NAME_2)
                .selectPipeline()
                .clickOKButton()
                .clickSubmitButton();

        Assert.assertTrue(actualResult.getTopMenueLinkText().contains(RANDOM_NAME_1));
        Assert.assertTrue(actualResult.getTopMenueLinkText().contains(RANDOM_NAME_2));
        Assert.assertTrue(actualResult.getDescriptionText().contains(RANDOM_NAME_1 + "/" + RANDOM_NAME_2));
    }

    @Test
    public void testCreateFolderWithDisplayNameInFolder() {
        FolderStatusPage statusPage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(RANDOM_NAME_1)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickCreateJob()
                .setProjectName(RANDOM_NAME_2)
                .selectFolderAndClickOk()
                .clickDisplayName("NewDisplayName")
                .clickSaveButton();

        Assert.assertTrue(statusPage.getHeaderText().contains("NewDisplayName"));
        Assert.assertTrue(statusPage.getTopMenueLinkText().contains(RANDOM_NAME_1));
        Assert.assertTrue(statusPage.getTopMenueLinkText().contains("NewDisplayName"));
    }

    @Test(dependsOnMethods = "testCreateFolderWithDisplayNameInFolder")
    public void testMoveFolderByDropDown() {
        createFolder(RANDOM_MULTIBRANCH_PIPELINE_NAME);

        FolderStatusPage actualResult = new HomePage(getDriver())
                .clickFolderDropdownMenu(RANDOM_MULTIBRANCH_PIPELINE_NAME)
                .clickMoveButtonDropdown()
                .selectFolder(RANDOM_NAME_1)
                .clickMove()
                .clickDashboard()
                .clickFolder(RANDOM_NAME_1);

        Assert.assertTrue(actualResult.getJobList().contains(RANDOM_MULTIBRANCH_PIPELINE_NAME));
    }

    @Test
    public void testCreateMultibranchPipelineInFolder() {
        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(RANDOM_NAME_1)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickCreateJob()
                .setProjectName(RANDOM_MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .clickDashboard()
                .clickFolder(RANDOM_NAME_1);

        Assert.assertEquals(folderStatusPage.getHeaderFolderText(), RANDOM_NAME_1);
        Assert.assertTrue(folderStatusPage.getJobList().size() != 0);
        Assert.assertTrue(folderStatusPage.getJobList().contains(RANDOM_MULTIBRANCH_PIPELINE_NAME));
    }

    @Test
    public void testMoveFolderToFolder() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(RANDOM_NAME_1)
                .selectFolderAndClickOk()
                .clickDashboard()

                .clickNewItem()
                .setProjectName(RANDOM_NAME_2)
                .selectFolderAndClickOk()
                .clickDashboard()

                .clickFolderDropdownMenu(RANDOM_NAME_1)
                .clickMoveButtonDropdown()
                .selectFolder(RANDOM_NAME_2)
                .clickMove()
                .clickDashboard();

        Assert.assertFalse(homePage.getJobList().contains(RANDOM_NAME_1));

        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickFolder(RANDOM_NAME_2);
        Assert.assertTrue(folderStatusPage.getJobList().contains(RANDOM_NAME_1));
    }
}
