import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.List;
import java.util.ArrayList;

public class MulticonfigurationProjectTest extends BaseTest {
    private static final String PROJECT_NAME = "FirstMultiProject";
    private static final By FIRST_MC_PROJECT =
            By.xpath(String.format ("//table[@id = 'projectstatus']//td/a/span[contains(text(),'%s')]",PROJECT_NAME));
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By DASHBOARD = By.xpath("//img[@id='jenkins-head-icon']");
    private static final By NEW_ITEM = By.xpath ("//a[@href='/view/all/newJob']");
    private static final By SAVE_BUTTON = By.xpath ("//button[@type='submit']");
    private static final By INPUT_NAME = By.id ("name");
    public WebElement getFirstMCProject() {
        return getDriver().findElement(FIRST_MC_PROJECT);
    }
    private String getRandomName() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
    public void createMulticonfigurationProject(){
        getDriver().findElement(By.className("task-icon-link")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Multi-configuration project')]")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(PROJECT_NAME);
        getDriver().findElement (By.className("hudson_matrix_MatrixProject")).click ();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD).click();
    }
    public void deleteDescription() {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.id("yui-gen2-button")).click();
//        getDriver().findElement (DASHBOARD);
    }
    private List<String> getListExistingProjectsNames() {
        List<WebElement> existingJobs = getDriver().findElements(By.xpath("//tr/td/a"));
        List<String> existingJobsNames = new ArrayList<>();
        for (int i = 0; i < existingJobs.size(); i++) {
            existingJobsNames.add(i, existingJobs.get(i).getText());
        }
        return existingJobsNames;
    }

    @Test
    public void testCreateMultiConfigurationProjectWithValidName_HappyPath() {

        getDriver ().findElement (NEW_ITEM).click ();
        getDriver().findElement(INPUT_NAME).sendKeys(PROJECT_NAME);
        getDriver ().findElement (By.className("hudson_matrix_MatrixProject")).click ();
        getDriver ().findElement (OK_BUTTON).click ();
        getDriver ().findElement (SAVE_BUTTON).click ();
        getDriver ().findElement (DASHBOARD).click ();

        Assert.assertEquals(getDriver().findElement(FIRST_MC_PROJECT).getText(), PROJECT_NAME);

        deleteNewMCProject();
    }
    @Test
    public void testMulticonfigurationProjectAddDescription() {
        final String description = "Description";
        createMulticonfigurationProject();

        getDriver().findElement(By.xpath(String.format ("//span[contains(text(),'%s')]",PROJECT_NAME))).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(description);
        getDriver().findElement(By.id("yui-gen2-button")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description div")).getText(), description);

        deleteDescription();
        getDriver ().findElement(DASHBOARD).click ();
        deleteNewMCProject();
    }
    private void deleteNewMCProject() {
        getFirstMCProject().click();
        getDriver().findElement(By.xpath("//a[@href = contains(., 'FirstMultiProject')]/button")).click();
        getDriver().findElement(
                By.xpath("//div[@id = 'tasks']//span[contains(text(), 'Delete Multi-configuration project')]")).click();
        getDriver().switchTo().alert().accept();
    }
    @Test
    public void testMultiConfigurationProjectRenameProjectViaDropDownMenu(){
        final String newName = getRandomName ();
        createMulticonfigurationProject ();

        getDriver ().findElement (By.xpath (String.format (" //a[@href='job/%s/']/button",PROJECT_NAME))).click ();
        getDriver ().findElement (By.xpath (String.format ("//a[@href='/job/%s/confirm-rename']", PROJECT_NAME))).click ();
        getDriver ().findElement (By.xpath (String.format ("//input[@value='%s']",PROJECT_NAME))).clear ();
        getDriver ().findElement (By.xpath (String.format ("//input[@value='%s']",PROJECT_NAME))).sendKeys (newName);
        getDriver ().findElement (By.id ("yui-gen1-button")).click ();

        Assert.assertEquals (getDriver ().findElement (
                By.xpath (String.format ("//h1[contains(text(),'Project %s')]", newName))).getText (),String.format ("Project %s",newName));
    }

    @Test
    public void testMultiConfigurationProjectDelete(){
        createMulticonfigurationProject ();

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        getDriver().findElement(By.xpath("//tr[@id = 'job_FirstMultiProject']/descendant::td//button")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Delete Multi-configuration project')]")).click();

        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        Assert.assertFalse(getListExistingProjectsNames().contains(PROJECT_NAME));
    }
}
