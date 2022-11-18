import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class FolderTest extends BaseTest {

    private static final By OK_BUTTON = By.id("ok-button");

    private static final By INPUT_NAME = By.xpath("//input [@name = 'name']");

    private static final By SAVE_BUTTON = By.id("yui-gen6-button");

    private static final By FOLDER = By.xpath("//span[text()='Folder']");

    private static final By DASHBOARD = By.xpath("//a[text()='Dashboard']");

    public WebElement getSaveButton() {
        return getDriver().findElement(SAVE_BUTTON);
    }

    public WebElement getDashboard() {
        return getDriver().findElement(DASHBOARD);
    }

    String generatedString = UUID.randomUUID().toString().substring(0, 8);
    String generatedString2 = UUID.randomUUID().toString().substring(0, 8);

    public void createFolder() {
        List<String> hrefs = getDriver()
                .findElements(By.xpath("//table[@id='projectstatus']/tbody/tr/td/a"))
                .stream()
                .map(element -> element.getAttribute("href"))
                .collect(Collectors.toList());
        for (String href : hrefs) {
            getDriver().get(href + "/delete");
            getDriver().findElement(By.id("yui-gen1-button")).click();
        }
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(generatedString);
        getDriver().findElement(FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
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
    public void testCreateSubFolder(){
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(generatedString);
        getDriver().findElement(FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(INPUT_NAME).sendKeys(generatedString2);
        getDriver().findElement(FOLDER).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        WebElement breadcrumbsElement = getDriver().findElement(By.xpath("//ul[@id=\"breadcrumbs\"]/li[last()]"));
        System.out.println(breadcrumbsElement.getText());
        String breadCrumbsElementHref = getDriver().findElement(By.xpath("//ul[@id=\"breadcrumbs\"]/li[last()]")).getAttribute("href");
        System.out.println(breadCrumbsElementHref);
        Assert.assertEquals(breadCrumbsElementHref, "/job/" + generatedString + "/job/" + generatedString2 + "/");
    }
}