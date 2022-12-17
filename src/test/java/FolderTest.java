import model.FolderStatusPage;
import model.FreestyleProjectStatusPage;
import model.HomePage;
import model.MultiConfigurationProjectStatusPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FolderTest extends BaseTest {

    private static final By OK_BUTTON = By.id("ok-button");
    private static final By INPUT_NAME = By.xpath("//input [@name = 'name']");
    private static final By SAVE_BUTTON = By.xpath("//span[@name = 'Submit']");
    private static final By FOLDER = By.xpath("//span[text()='Folder']");
    private static final By DASHBOARD = By.xpath("//a[text()='Dashboard']");
    private static final By CREATE_NEW_ITEM = By.linkText("New Item");
    private static final By CREATE_A_JOB = By.linkText("Create a job");
    private static final By ADD_DESCRIPTION = By.linkText("Add description");
    private static final By DESCRIPTION = By.name("_.description");
    final String FOLDER_NAME_RANDOM = TestUtils.getRandomStr();

    public Actions getAction() {
        return new Actions(getDriver());
    }

    public WebElement getSaveButton() {
        return getDriver().findElement(SAVE_BUTTON);
    }

    public WebElement getDashboard() {
        return getDriver().findElement(DASHBOARD);
    }

    String generatedString = UUID.randomUUID().toString().substring(0, 8);

    public FolderStatusPage createFolder() {
        List<String> hrefs = getDriver()
                .findElements(By.xpath("//table[@id='projectstatus']/tbody/tr/td/a"))
                .stream()
                .map(element -> element.getAttribute("href"))
                .collect(Collectors.toList());
        for (String href : hrefs) {
            getDriver().get(href + "/delete");
            try {
                getDriver().findElement(By.id("yui-gen1-button")).click();
            } catch (NoSuchElementException ex) {
                String title = getDriver().getTitle();
                System.out.println("Job not found (" + title + "): " + href);
            }
        }
        return new HomePage(getDriver())
                .clickDashboard()
                .clickNewItem()
                .setProjectName(generatedString)
                .selectFolderAndClickOk()
                .clickSaveBtn(FolderStatusPage.class);
    }

    private List<String> getProjectNameFromProjectTable() {
        List<WebElement> projectTable = getDriver().findElements(By.xpath("//tr/td/a"));
        List<String> projectTableNames = new ArrayList<>();
        for (WebElement name : projectTable) {
            projectTableNames.add(name.getText());
        }

        return projectTableNames;
    }

    public void scrollByVisibleElement(By by) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", getDriver().findElement(by));
    }

    @Test
    public void testCreate() {
        createFolder();
        List<String> allJobsAfterCreate = new HomePage(getDriver())
                .clickDashboard()
                .getJobList();

        Assert.assertTrue(allJobsAfterCreate.contains(generatedString));
    }

    @Test
    public void testConfigureFolderDisplayName() {
        final String folderName = TestUtils.getRandomStr(5);
        final String secondJob = "Second job";
        HomePage folderStatusPage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(folderName)
                .selectFolderAndClickOk()
                .clickDashboard()
                .clickJobDropDownMenu(folderName)
                .clickConfigureDropDownMenuForFolder()
                .clickDisplayName(secondJob)
                .clickDescription("change name")
                .clickSaveBtn(FolderStatusPage.class)
                .clickDashboard();

        Assert.assertTrue(folderStatusPage.getJobList().contains(secondJob));
    }

    @Test
    public void testDeleteFolder() {
        createFolder();
        List<String> allJobsAfterDelete = new HomePage(getDriver())
                .clickDashboard()
                .clickJob(generatedString)
                .clickDelete()
                .clickSubmit()
                .getJobList();
        Assert.assertFalse(allJobsAfterDelete.contains(generatedString));
        Assert.assertTrue(allJobsAfterDelete.isEmpty());
    }

    @Test
    public void testConfigureFolderDisplayNameSaveFirstName() {
        String secondJobName = "Second name";
        createFolder();
        String folderStatusPage = new HomePage(getDriver())
                .clickDashboard()
                .clickJob(generatedString)
                .clickConfigureDropDownMenuForFolder()
                .clickDisplayName(secondJobName)
                .clickDescription("change name")
                .clickSaveBtn(FolderStatusPage.class)
                .clickDashboard()
                .clickJob(secondJobName)
                .clickMainPanel();
        Assert.assertEquals(folderStatusPage, "Folder name: " + generatedString);
    }

    @Test
    public void testConfigureFolderAddDescription() {
        final String folderName = TestUtils.getRandomStr(5);
        final String addDescription = "Add description";
        String folderStatusPage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(folderName)
                .selectFolderAndClickOk()
                .clickDescription(addDescription)
                .clickSaveBtn(FolderStatusPage.class)
                .getTextDescription(addDescription);
        Assert.assertEquals(folderStatusPage, addDescription);
    }

    @Test
    public void testMoveFolderInFolder() {
        createFolder();
        String generatedStringFolder2 = UUID.randomUUID().toString().substring(0, 8);
        List<String> folderStatusPage = new HomePage(getDriver())
                .clickDashboard()
                .clickNewItem()
                .setProjectName(generatedStringFolder2)
                .selectFolderAndClickOk()
                .clickSaveBtn(FolderStatusPage.class)
                .clickDashboard()
                .clickJob(generatedStringFolder2)
                .clickMove()
                .selectDestination(generatedString)
                .clickMoveAfterSelectDestination()
                .clickDashboard()
                .getJobList();

        Assert.assertEquals(folderStatusPage.get(0), generatedString);
    }

    @Test
    public void testNameAfterRenameIngFolder() {
        final String folderName1 = TestUtils.getRandomStr(6);
        final String folderName2 = TestUtils.getRandomStr(6);

        List<String> newFolderName = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(folderName1)
                .selectFolderAndClickOk()
                .clickDashboard()
                .clickFolder(folderName1)
                .clickRename(folderName1)
                .clearAndSetNewName(folderName2)
                .clickSubmitButton()
                .clickDashboard()
                .getJobList();

        Assert.assertTrue(newFolderName.contains(folderName2));
    }

    @Test
    public void testCreateFreestyleProjectInFolderCreateJob() {
        final String freestyleProjectName = TestUtils.getRandomStr();

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(FOLDER_NAME_RANDOM)
                .selectFolderAndClickOk()
                .clickSaveBtn(FolderStatusPage.class)
                .clickCreateJob()
                .setProjectName(freestyleProjectName)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn(FreestyleProjectStatusPage.class)
                .clickDashboard()
                .clickFolder(FOLDER_NAME_RANDOM)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(freestyleProjectName));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectInFolderCreateJob")
    public void testMoveFreestyleProjectInFolderUsingDropDownMenu() {
        final String freestyleProjectName = TestUtils.getRandomStr();

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(freestyleProjectName)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn(FreestyleProjectStatusPage.class)
                .clickDashboard()
                .clickJobDropDownMenu(freestyleProjectName)
                .clickMoveButtonDropdown()
                .selectFolder(FOLDER_NAME_RANDOM)
                .clickMove()
                .clickDashboard()
                .clickFolder(FOLDER_NAME_RANDOM)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(freestyleProjectName));
    }

    @Test(dependsOnMethods = "testMoveFreestyleProjectInFolderUsingDropDownMenu")
    public void testCreateFreestyleProjectInFolderNewItem() {
        final String freestyleProjectName = TestUtils.getRandomStr();

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME_RANDOM)
                .clickFolderNewItem()
                .setProjectName(freestyleProjectName)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn(FreestyleProjectStatusPage.class)
                .clickDashboard()
                .clickFolder(FOLDER_NAME_RANDOM)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(freestyleProjectName));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testCreateMultiConfigurationProjectInFolder() {
        final String multiConfigurationProjectName = TestUtils.getRandomStr();

        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickFolder(generatedString)
                .clickCreateJob()
                .setProjectName(multiConfigurationProjectName)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveBtn(MultiConfigurationProjectStatusPage.class)
                .clickParentFolderInBreadcrumbs();

        Assert.assertTrue(folderStatusPage.getJobList().contains(multiConfigurationProjectName));
    }

    @Test
    public void testCreateFolderInFolder() {

        final String folderName = TestUtils.getRandomStr();
        final String subFolderName = TestUtils.getRandomStr();
        final String expectedResult = String.format("/job/%s/job/%s/", folderName, subFolderName);

        getDriver().findElement(CREATE_NEW_ITEM).click();
        getDriver().findElement(INPUT_NAME).sendKeys(folderName);
        getDriver().findElement(FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();

        getDriver().findElement(CREATE_A_JOB).click();
        getDriver().findElement(INPUT_NAME).sendKeys(subFolderName);
        getDriver().findElement(FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();

        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", folderName))).click();
        getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", subFolderName))).click();

        List<WebElement> breadcrumbs = getDriver().findElements(By.xpath("//ul[@id='breadcrumbs']//li"));

        Assert.assertEquals(breadcrumbs.get(breadcrumbs.size() - 1).getAttribute("href"), expectedResult);
    }

    @Test
    public void testDeleteFolder2() {
        createFolder();

        getDashboard().click();
        getDriver().findElement(By.xpath("//span[text() = '" + generatedString + "']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/job/" + generatedString + "/delete']")).click();
        getDriver().findElement(By.xpath("//button[@type= 'submit']")).click();

        List<String> foldersList = getDriver()
                .findElements(By.xpath("//tr/td[3]/a/span"))
                .stream()
                .map(element -> element.getText())
                .collect(Collectors.toList());

        Assert.assertFalse(foldersList.contains(generatedString));
    }

    @Ignore
    @Test
    public void testConfigureFolderDisplayNameWithDropdownMenu() {

        String folderName = TestUtils.getRandomStr();
        String displayName = TestUtils.getRandomStr();
        Pattern pattern = Pattern.compile("\\bFolder.*\\b");

        getDriver().findElement(CREATE_NEW_ITEM).click();
        getDriver().findElement(INPUT_NAME).sendKeys(folderName);
        getDriver().findElement(FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();

        getDriver().findElement(DASHBOARD).click();

        getAction()
                .moveToElement(getDriver().findElement(By.linkText(folderName)))
                .moveToElement(getDriver().findElement(By.xpath("//tr[@id = 'job_" + folderName + "']//td/a/button")))
                .click()
                .build().perform();
        getDriver().findElement(By.xpath("//span[contains(text(),'Configure')]")).click();

        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(displayName);
        getDriver().findElement(SAVE_BUTTON).click();

        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.xpath(String.format("//a[@href='job/%s/']", folderName))).click();

        Matcher matcher = pattern.matcher(getDriver().findElement(By.xpath("//div[@id='main-panel']")).getText());

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                displayName);
        Assert.assertTrue(matcher.find());
        Assert.assertEquals(matcher.group(), String.format("Folder name: %s", folderName));
    }

    @Test
    public void testDeleteFolderUsingDropDown() {

        final String folderName = TestUtils.getRandomStr(5);

        String welcomeJenkinsHeader = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(folderName)
                .selectFolderAndClickOk()
                .clickDashboard()
                .clickJobDropDownMenu(folderName)
                .clickDeleteDropDownMenu()
                .clickSubmitDeleteProject()
                .getHeaderText();

        Assert.assertEquals(welcomeJenkinsHeader, "Welcome to Jenkins!");
    }

    @Ignore
    @Test
    public void testAddFolderDescription() {
        String folderName = TestUtils.getRandomStr();
        String folderDescription = TestUtils.getRandomStr();

        getDriver().findElement(CREATE_NEW_ITEM).click();
        getDriver().findElement(INPUT_NAME).sendKeys(folderName);
        getDriver().findElement(FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.linkText(folderName)).click();
        getDriver().findElement(ADD_DESCRIPTION).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(folderDescription);
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(folderDescription));
    }

    @Test
    public void testCreateFreestyleProjectInFolderByNewItemDropDownInCrambMenu() {
        final String folderName = TestUtils.getRandomStr();
        final String freestyleProjectName = TestUtils.getRandomStr();

        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickNewItem()
                .setProjectName(folderName)
                .selectFolderAndClickOk()
                .clickSaveBtn(FolderStatusPage.class)
                .clickNewItemDropdownThisFolderInBreadcrumbs()
                .setProjectName(freestyleProjectName)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn(FreestyleProjectStatusPage.class)
                .clickParentFolderInBreadcrumbs();

        Assert.assertTrue(folderStatusPage.getJobList().contains(freestyleProjectName));
    }

    @Test
    public void testCreateNewMagicFolder() {

        getDriver().findElement(CREATE_NEW_ITEM).click();
        getDriver().findElement(INPUT_NAME).sendKeys("Magic Folder");
        scrollByVisibleElement(FOLDER);
        new Actions(getDriver()).pause(1500).moveToElement(getDriver().findElement(FOLDER)).click()
                .perform();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(DESCRIPTION).sendKeys("Wand,mustache,top hat");
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD).click();

        Assert.assertTrue(getProjectNameFromProjectTable().contains("Magic Folder"));
    }

    @Test(dependsOnMethods = "testCreateNewMagicFolder")
    public void testCreateFolderWithDescriptionAndJobPipeline() {

        getDriver().findElement(By.xpath("//span[text()='Magic Folder']")).click();
        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();
        getDriver().findElement(By.xpath(" //input[@class='jenkins-input']")).sendKeys("New items");
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("yui-gen6-button")).click();
        getDriver().findElement(By.xpath("//a[@href='/'][@class='model-link']")).click();

        Assert.assertTrue(getDriver().findElement(By.id("projectstatus")).getText().contains("Magic Folder"));
    }
}