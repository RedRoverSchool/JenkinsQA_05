import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
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
    private static final By FREESTYLE_PROJECT = By.xpath("//span[text()='Freestyle project']");
    private static final By CREATE_A_JOB = By.linkText("Create a job");
    private static final By ADD_DESCRIPTION = By.linkText("Add description");
    private static final By SUBMIT_DELETE_BUTTON = By.xpath("//button[@type= 'submit']");

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

    public void createFolder() {
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
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(generatedString);
        getDriver().findElement(FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
    }

    private String getRandomName() {

        return RandomStringUtils.randomAlphanumeric(10);
    }

    private void createProjectFromDashboard(By type, String name) {
        getDriver().findElement(CREATE_NEW_ITEM).click();
        getDriver().findElement(INPUT_NAME).sendKeys(name);
        getDriver().findElement(type).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
    }

    private List<String> getProjectNameFromProjectTable() {
        List<WebElement> projectTable = getDriver().findElements(By.xpath("//tr/td/a"));
        List<String> projectTableNames = new ArrayList<>();
        for (WebElement name : projectTable) {
            projectTableNames.add(name.getText());
        }

        return projectTableNames;
    }

    @Test
    public void testCreate() {
        createFolder();
        getDashboard().click();
        String job = getDriver().findElement(By.xpath("//span[text()='" + generatedString + "']")).getText();

        Assert.assertEquals(job, generatedString);
    }


    @Test
    public void testConfigureFolderDisplayName() {
        String secondJobName = "Second job";
        createFolder();
        getDashboard().click();
        getDriver().findElement(By.xpath("//span[text()='" + generatedString + "']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + generatedString + "/configure']")).click();
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(secondJobName);
        getDriver().findElement(By.xpath("//textarea[@name='_.description']")).sendKeys("change name");
        getSaveButton().click();
        getDashboard().click();
        String changedName = getDriver().findElement(By.xpath("//span[text()='" + secondJobName + "']")).getText();

        Assert.assertEquals(changedName, secondJobName);
    }

    @Test
    public void testDeleteFolder() {
        createFolder();
        getDashboard().click();
        getDriver().findElement(By.xpath("//span[text()='" + generatedString + "']")).click();
        getDriver().findElement(By.xpath("//span//*[@class='icon-edit-delete icon-md']")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
        getDashboard().click();
        try {
            getDriver().findElement((By.xpath("//span[text()='" + generatedString + "']")));
            Assert.fail("Folder with name " + generatedString + " expected to not to be found on the screen");
        } catch (NoSuchElementException ignored) {
        }
    }

    @Test
    public void testConfigureFolderDisplayNameSaveFirstName() {
        String secondJobName = "Second name";
        createFolder();
        getDashboard().click();
        getDriver().findElement(By.xpath("//span[text()='" + generatedString + "']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + generatedString + "/configure']")).click();
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(secondJobName);
        getDriver().findElement(By.xpath("//textarea[@name='_.description']")).sendKeys("change name");
        getSaveButton().click();
        getDashboard().click();
        getDriver().findElement(By.xpath("//span[text()='" + secondJobName + "']")).click();
        String[] namesBlock = getDriver().findElement(By.id("main-panel")).getText().split("\n");

        Assert.assertEquals(namesBlock[0], secondJobName);
        Assert.assertEquals(namesBlock[1], "Folder name: " + generatedString);
    }

    @Test
    public void testConfigureFolderAddDescription() {
        String generatedString = UUID.randomUUID().toString().substring(0, 8);
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(generatedString);
        getDriver().findElement(FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(By.xpath("//textarea[@name='_.description']")).sendKeys("Add description");
        getSaveButton().click();
        getDashboard().click();
        getDriver().findElement(By.xpath("//span[text()='" + generatedString + "']")).click();
        String description = getDriver().findElement(By.xpath("//div[text()='Add description']")).getText();

        Assert.assertEquals(description, "Add description");
    }

    @Test
    public void testMoveFolderInFolder() {
        createFolder();
        getDashboard().click();
        String generatedStringFolder2 = UUID.randomUUID().toString().substring(0, 8);
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(generatedStringFolder2);
        getDriver().findElement(FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getSaveButton().click();
        getDashboard().click();
        getDriver().findElement(By.xpath("//span[text()='" + generatedStringFolder2 + "']")).click();
        getDriver().findElement(By.xpath("//span[text()='Move']/..")).click();
        Select select = new Select(getDriver().findElement(By.xpath("//select[@name='destination']")));
        select.selectByValue("/" + generatedString);
        getDriver().findElement(By.xpath("//button[text()='Move']")).click();
        getDashboard().click();
        String job = getDriver().findElement(By.xpath("//span[text()='" + generatedString + "']")).getText();

        Assert.assertEquals(job, generatedString);
    }

    @Test
    public void testNameAfterRenamingFolder() {
        final String expectedResult = "Folder2";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys("Folder1");
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("yui-gen6-button")).click();

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        getDriver().findElement(By.xpath("//a[@href='job/Folder1/']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/Folder1/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys(expectedResult);
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='job/Folder2/']")).getText(), expectedResult);
    }

    @Test
    public void testCreateFreestyleProjectInFolderCreateJob() {
        final String folderName = getRandomName();
        final String freestyleProjectName = getRandomName();

        createProjectFromDashboard(FOLDER, folderName);
        getDriver().findElement(CREATE_A_JOB).click();
        getDriver().findElement(INPUT_NAME).sendKeys(freestyleProjectName);
        getDriver().findElement(FREESTYLE_PROJECT).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD).click();
        getDriver().findElement(By.linkText(folderName)).click();

        Assert.assertTrue(getProjectNameFromProjectTable().contains(freestyleProjectName));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testCreateMultiConfigurationProjectInFolder() {

        final String multiConfigurationProjectName = getRandomName();

        getDriver().findElement(By.xpath("//span[text()='" + generatedString + "']")).click();
        getDriver().findElement(CREATE_A_JOB).click();
        getDriver().findElement(INPUT_NAME).sendKeys(multiConfigurationProjectName);
        getDriver().findElement(By.xpath("//span[text()='Multi-configuration project']")).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(By.xpath("//a[text()='" + generatedString + "']")).click();

        Assert.assertTrue(getProjectNameFromProjectTable().contains(multiConfigurationProjectName));
    }

    @Test
    public void testCreateFolderInFolder() {

        final String folderName = getRandomName();
        final String subFolderName = getRandomName();
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

    @Test(dependsOnMethods = "testCreate")
    public void testDeleteFolder2() {
        getDriver().findElement(By.xpath("//span[text() = '" + generatedString + "']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/job/" + generatedString + "/delete']")).click();
        getDriver().findElement(SUBMIT_DELETE_BUTTON).click();

        List<String> foldersList = getDriver()
                .findElements(By.xpath("//tr/td[3]/a/span"))
                .stream()
                .map(element -> element.getText())
                .collect(Collectors.toList());

        Assert.assertFalse(foldersList.contains(generatedString));
    }

    @Test
    public void testMoveFreestyleProjectInFolderUsingDropDownMenu() {
        final String folderName = getRandomName();
        final String freestyleProjectName = getRandomName();

        createProjectFromDashboard(FOLDER, folderName);
        getDriver().findElement(DASHBOARD).click();
        createProjectFromDashboard(FREESTYLE_PROJECT, freestyleProjectName);
        getDriver().findElement(DASHBOARD).click();

        Actions action = new Actions(getDriver());
        action
                .moveToElement(getDriver().findElement(By.linkText(freestyleProjectName)))
                .moveToElement(getDriver().findElement(By.xpath("//tr[@id = 'job_" + freestyleProjectName + "']//td/a/button")))
                .click()
                .build().perform();
        getDriver().findElement(By.xpath("//span[contains(text(),'Move')]")).click();

        getDriver().findElement(By.xpath("//option[@value='/" + folderName + "']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(DASHBOARD).click();

        getDriver().findElement(By.linkText(folderName)).click();

        Assert.assertTrue(getProjectNameFromProjectTable().contains(freestyleProjectName));
    }

    @Test
    public void testConfigureFolderDisplayNameWithDropdownMenu() {

        String folderName = getRandomName();
        String displayName = getRandomName();
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

        final String folderName = getRandomName();

        createProjectFromDashboard(FOLDER, folderName);
        getDashboard().click();
        getAction().
                moveToElement(getDriver().findElement(By.linkText(folderName)))
                .moveToElement(getDriver().findElement(By.xpath("//tr[@id = 'job_" + folderName + "']//td/a/button")))
                .click()
                .build()
                .perform();
        getDriver().findElement(By.xpath("//a[@href = '/job/" + folderName + "/delete']")).click();
        getDriver().findElement(By.cssSelector("#yui-gen1-button")).click();

        Assert.assertFalse(getProjectNameFromProjectTable().contains(folderName));
    }

    @Test
    public void testAddFolderDescription() {
        String folderName = getRandomName();
        String folderDescription = getRandomName();

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
    public void testCreateFreestyleProjectInFolderNewItem() {
        final String folderName = getRandomName();
        final String freestyleProjectName = getRandomName();

        createProjectFromDashboard(FOLDER, folderName);
        getDriver().findElement(CREATE_NEW_ITEM).click();
        getDriver().findElement(INPUT_NAME).sendKeys(freestyleProjectName);
        getDriver().findElement(FREESTYLE_PROJECT).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(By.linkText(folderName)).click();

        Assert.assertTrue(getProjectNameFromProjectTable().contains(freestyleProjectName));
    }

    @Test
    public void testCreateFreestyleProjectInFolderByNewItemDropDownInCrambMenu(){
        final String folderName = getRandomName();
        final String freestyleProjectName = getRandomName();

        createProjectFromDashboard(FOLDER, folderName);

        getDriver().findElement(By.xpath("//a[text()='" + folderName + "']//following-sibling::button")).click();
        getDriver().findElement(By.xpath("//li/a/span[text()='New Item']")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(freestyleProjectName);
        getDriver().findElement(FREESTYLE_PROJECT).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(By.xpath("//a[text()='" + folderName + "']")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("#job_"+ freestyleProjectName)).isEnabled());
    }
}
