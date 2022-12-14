import model.FolderStatusPage;
import model.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

public class FolderOneTest extends BaseTest {
    private static final By NEW_ITEM = By.linkText("New Item");
    private static final By NAME = By.id("name");
    private static final By NAME_CONFIGURE = By.name("_.displayNameOrNull");
    private static final By NEW_NAME_RENAME = By.name("newName");
    private static final By FOLDER_OPTION = By.className("com_cloudbees_hudson_plugins_folder_Folder");
    private static final By PIPELINE_OPTION = By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob");
    private static final By DROP_DOWN_MENU = By.cssSelector(".jenkins-menu-dropdown-chevron");
    private static final By DROP_DOWN_CONFIGURE = By.xpath("//span[contains(text(),'Configure')]");
    private static final By DROP_DOWN_RENAME = By.xpath("//span[contains(text(),'Rename')]");
    private static final By DROP_DOWN_DELETE = By.xpath("//span[contains(text(),'Delete Folder')]");
    private static final By DROP_DOWN_MOVE = By.xpath("//span[contains(text(),'Move')]");
    private static final By JENKINS_ICON = By.id("jenkins-name-icon");
    private static final By CREATE_JOB = By.linkText("Create a job");
    private static final By TEXT_ADDRESS = By.id("main-panel");
    private static final By TEXT_H1 = By.xpath("//h1");
    private static final By TEXTAREA = By.name("_.description");
    private static final By DELETE_FOLDER = By.linkText("Delete Folder");
    private static final By CONFIGURE_FOLDER = By.linkText("Configure");
    private static final By MOVE_FOLDER = By.linkText("Move");
    private static final By HEADER_ADDRESS= By.id("breadcrumbs");
    private static final By SELECTION_SCRIPT = By.xpath("//div[@class='samples']/select/option[4]");
    private static final String RANDOM_NAME_1 = TestUtils.getRandomStr(6);
    private static final String RANDOM_NAME_2 = TestUtils.getRandomStr(6);
    private static final String RANDOM_MULTIBRANCH_PIPELINE_NAME = TestUtils.getRandomStr(6);

    private void submitButtonClick(){
        getDriver().findElement(By.cssSelector("[type='submit']")).click();
    }

    private void createFolder(String folderName){
        new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(folderName)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickDashboard();
    }

    @Test
    public void testCreateNewFolder(){
        createFolder(RANDOM_NAME_1);

        HomePage homePage = new HomePage(getDriver());

        Assert.assertTrue(homePage.getJobList().contains(RANDOM_NAME_1));
    }

    @Test(dependsOnMethods = "testCreateNewFolder")
    public void testCreateFolderInFolder(){
        FolderStatusPage folderStatusPage =  new HomePage(getDriver())
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

    @Test (dependsOnMethods = "testConfigureFolderDisplayName")
    public void testAddFolderDescription(){
        FolderStatusPage folderStatusPage = new HomePage(getDriver())

                .clickFolderDropdownMenu(RANDOM_NAME_1)
                .clickConfigDropDownMenu()
                .setDescription("Folder description")
                .clickSaveButton();

        Assert.assertTrue(folderStatusPage.getDescriptionText().contains("Folder description"));
    }

    @Test(dependsOnMethods = "testAddFolderDescription")
    public void testRenameFolderDescription(){
        HomePage homePage = new HomePage(getDriver())

                .clickFolderDropdownMenu(RANDOM_NAME_1)
                .clickRenameDropDownMenu()
                .clearFieldAndInputNewName(RANDOM_NAME_2)
                .clickSubmitButton()
                .clickDashboard();
        Assert.assertTrue(homePage.getJobList().contains(RANDOM_NAME_2));
    }

    @Test
    public void testDeleteFolder(){
        createFolder(RANDOM_NAME_1);

        HomePage homePage = new HomePage(getDriver())
                .clickFolder(RANDOM_NAME_1)
                .clickDeleteFolder()
                .clickSubmitButton()
                .clickDashboard();

        Assert.assertFalse(homePage.getJobList().contains(RANDOM_NAME_1));
    }

    @Test
    public void testCreateFolderInFolderJob(){
        createFolder(RANDOM_NAME_1);

        new HomePage(getDriver())
                .clickFolder(RANDOM_NAME_1);

        createFolder(RANDOM_NAME_2);

        new HomePage(getDriver())
                .clickFolder(RANDOM_NAME_1);

        FolderStatusPage statusPage = new HomePage(getDriver())
                .clickFolder(RANDOM_NAME_2);

        Assert.assertTrue(statusPage.getHeaderText().contains(RANDOM_NAME_2));
        Assert.assertTrue(statusPage.getTopMenueLinkText().contains(RANDOM_NAME_1));
        Assert.assertTrue(statusPage.getTopMenueLinkText().contains(RANDOM_NAME_2));
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateFolderInFolderJob")
    public void testRenameFolder()  {
        getDriver().findElement(By.linkText(RANDOM_NAME_1)).findElement(DROP_DOWN_MENU).click();
        getWait(5).until(ExpectedConditions.presenceOfElementLocated(DROP_DOWN_RENAME)).click();
        getDriver().findElement(NEW_NAME_RENAME).clear();
        getDriver().findElement(NEW_NAME_RENAME).sendKeys(RANDOM_NAME_2 + "NEW");
        submitButtonClick();

        String actualFolderName = getDriver()
                .findElement(HEADER_ADDRESS)
                .findElement(By.linkText(RANDOM_NAME_2 + "NEW"))
                .getText();
        Assert.assertEquals(actualFolderName,(RANDOM_NAME_2 + "NEW"));
        Assert.assertTrue(getDriver().findElement(TEXT_ADDRESS).getText().contains(RANDOM_NAME_2 + "NEW"));
    }
    @Ignore
    @Test(dependsOnMethods = "testRenameFolder")
    public void testMoveFolderInFolder(){
        createFolder(RANDOM_NAME_1);
        getDriver().findElement(MOVE_FOLDER).click();
        getDriver().findElement(By.xpath("//select/option[@value='/"+ RANDOM_NAME_2 + "NEW" + "']")).click();
        submitButtonClick();

        String actualFolderName = getDriver()
                .findElement(HEADER_ADDRESS).findElement(By.linkText(RANDOM_NAME_1)).getText();
        String actualPipelineName = getDriver()
                .findElement(HEADER_ADDRESS).findElement(By.linkText(RANDOM_NAME_2 + "NEW")).getText();
        Assert.assertEquals(RANDOM_NAME_1,actualFolderName);
        Assert.assertEquals((RANDOM_NAME_2 + "NEW"),actualPipelineName);
    }
    @Ignore
    @Test(dependsOnMethods = "testMoveFolderInFolder")
    public void testDeleteFolderDropDown(){
        getDriver().findElement(By.linkText(RANDOM_NAME_2 + "NEW")).findElement(DROP_DOWN_MENU).click();
        getWait(5).until(ExpectedConditions.elementToBeClickable(DROP_DOWN_DELETE)).click();
        submitButtonClick();

        Assert.assertNotNull(getDriver().findElement(By.className("empty-state-block")));
    }
    @Ignore
    @Test(dependsOnMethods = "testDeleteFolderDropDown")
    public void testCreateNewFolderWithPipeline() {
        createFolder(RANDOM_NAME_1);
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(NAME).sendKeys(RANDOM_NAME_2);
        getDriver().findElement((PIPELINE_OPTION)).click();
        submitButtonClick();
        submitButtonClick();

        String actualFolderName = getDriver().findElement(By.linkText(RANDOM_NAME_1)).getText();
        String actualPipelineName = getDriver().findElement(By.linkText(RANDOM_NAME_2)).getText();
        Assert.assertEquals(actualFolderName, RANDOM_NAME_1);
        Assert.assertEquals(actualPipelineName, RANDOM_NAME_2);
        Assert.assertTrue(getDriver().findElement(TEXT_ADDRESS).getText()
                .contains(RANDOM_NAME_1 + "/" + RANDOM_NAME_2));
    }
    @Ignore
    @Test
    public void testCreateNewFolderPipelineOptionJob() {
        createFolder(RANDOM_NAME_1);
        getDriver().findElement(CREATE_JOB).click();
        getDriver().findElement(NAME).sendKeys(RANDOM_NAME_2);
        getDriver().findElement(PIPELINE_OPTION).click();
        submitButtonClick();
        getWait(5).until(ExpectedConditions.presenceOfElementLocated(SELECTION_SCRIPT)).click();
        submitButtonClick();

        String actualFolderName = getDriver().findElement(By.linkText(RANDOM_NAME_1)).getText();
        String actualPipelineName = getDriver().findElement(By.linkText(RANDOM_NAME_2)).getText();
        Assert.assertEquals(actualFolderName, RANDOM_NAME_1);
        Assert.assertEquals(actualPipelineName, RANDOM_NAME_2);
        Assert.assertTrue(getDriver().findElement(TEXT_ADDRESS).getText()
                .contains(RANDOM_NAME_1 + "/" + RANDOM_NAME_2));
    }
    @Ignore
    @Test(dependsOnMethods = "testCreateNewFolderPipelineOptionJob")
    public void testCreateFolderWithDisplayNameInFolder() {
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(NAME).sendKeys(RANDOM_NAME_1 + "_Folder2");
        getDriver().findElement(FOLDER_OPTION).click();
        submitButtonClick();
        getDriver().findElement(NAME_CONFIGURE).sendKeys(RANDOM_NAME_1 + "_Display2");
        getDriver().findElement(TEXTAREA).sendKeys("TEXT VERSION 1");
        submitButtonClick();
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(NAME).sendKeys(RANDOM_NAME_2 + "_SubFolder2");
        getDriver().findElement(FOLDER_OPTION).click();
        submitButtonClick();
        getDriver().findElement(NAME_CONFIGURE).sendKeys(RANDOM_NAME_2 + "_SubDisplay2");
        getDriver().findElement(TEXTAREA).sendKeys("TEXT VERSION 2");
        submitButtonClick();

        String actualFolderName = getDriver()
                .findElement(HEADER_ADDRESS).findElement(By.linkText(RANDOM_NAME_1 + "_Display2")).getText();
        String actualPipelineName = getDriver()
                .findElement(HEADER_ADDRESS).findElement(By.linkText(RANDOM_NAME_2 + "_SubDisplay2")).getText();
        Assert.assertEquals(actualFolderName, (RANDOM_NAME_1 + "_Display2"));
        Assert.assertEquals(actualPipelineName, (RANDOM_NAME_2 + "_SubDisplay2"));
        Assert.assertTrue(getDriver().findElement(TEXT_ADDRESS).getText()
                .contains((RANDOM_NAME_1 + "_Folder2") + "/" + (RANDOM_NAME_2 + "_SubFolder2")));
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateFolderWithDisplayNameInFolder")
    public void testMoveFolderByDropDown() {
        getDriver().findElement(By.linkText(RANDOM_NAME_1 + "_Display2")).click();
        getDriver().findElement(By.linkText(RANDOM_NAME_2 + "_SubDisplay2"))
                .findElement(DROP_DOWN_MENU).click();
        getWait(5).until(ExpectedConditions.elementToBeClickable(DROP_DOWN_MOVE)).click();
        getDriver().findElement(By.xpath("//select/option[@value='/"+ RANDOM_NAME_1 + "']")).click();
        submitButtonClick();

        String actualFolderName = getDriver()
                .findElement(HEADER_ADDRESS).findElement(By.linkText(RANDOM_NAME_1)).getText();
        String actualPipelineName = getDriver()
                .findElement(HEADER_ADDRESS).findElement(By.linkText(RANDOM_NAME_2 + "_SubDisplay2")).getText();
        Assert.assertEquals(actualFolderName, RANDOM_NAME_1);
        Assert.assertEquals(actualPipelineName, (RANDOM_NAME_2 + "_SubDisplay2"));
        Assert.assertTrue(getDriver().findElement(TEXT_ADDRESS).getText()
                .contains((RANDOM_NAME_1) + "/" + (RANDOM_NAME_2 + "_SubFolder2")));
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
}
