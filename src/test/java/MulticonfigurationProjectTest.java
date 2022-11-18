import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class MulticonfigurationProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "FirstMultiProject";
    private static final By FIRST_MC_PROJECT =
            By.xpath("//table[@id = 'projectstatus']//td/a/span[text() =  'FirstMultiProject']");

    public WebElement getFirstMCProject() {
        return getDriver().findElement(FIRST_MC_PROJECT);
    }

    private void click(By by) {
        getDriver().findElement(by).click();
    }

    public void createMulticonfigurationProject(){
        getDriver().findElement(By.className("task-icon-link")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Multi-configuration project')]")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("yui-gen27-button")).click();
        getDriver().findElement(By.cssSelector(".icon-up")).click();

    }
    public void deleteDescription(){
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.id("yui-gen2-button")).click();
        click(By.xpath("//ul[@id = 'breadcrumbs']//a[@class= 'model-link'][contains(., 'Dashboard')]"));
    }

    @Test
    public void testCreateMultiConfigurationProjectWithValidName_HappyPath() {

        click(By.linkText("New Item"));
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        click(By.className("hudson_matrix_MatrixProject"));
        click(By.id("ok-button"));
        click(By.id("yui-gen27-button"));
        click(By.xpath("//ul[@id = 'breadcrumbs']//a[@class= 'model-link'][contains(., 'Dashboard')]"));

        Assert.assertEquals(getFirstMCProject().getText(), PROJECT_NAME);

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
    public void testMulticonfigurationProjectAddDescription() {
        final String description = "Description";

        createMulticonfigurationProject();

        getDriver().findElement(By.xpath("//span[contains(text(),'" + PROJECT_NAME + "')]")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(description);
        getDriver().findElement(By.id("yui-gen2-button")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description div")).getText(), description);

        deleteDescription();

        deleteNewMCProject();
    }
}
