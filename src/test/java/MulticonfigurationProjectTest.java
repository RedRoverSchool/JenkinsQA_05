import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class MulticonfigurationProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "FirstMultiProject";
    private static final By FIRST_MC_PROJECT =
            By.xpath("//table[@id = 'projectstatus']//td/a/span[contains(text(),'" + PROJECT_NAME + "')]");

    public void createMulticonfigurationProject() {
        getDriver().findElement(By.className("task-icon-link")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Multi-configuration project')]")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.cssSelector(".icon-up")).click();
    }

    public void deleteDescription() {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.id("yui-gen2-button")).click();
        getDriver().findElement(By.cssSelector("li .model-link")).click();
    }

    private void deleteNewMCProject() {
        getDriver().findElement(FIRST_MC_PROJECT).click();
        getDriver().findElement(
                By.xpath("//div[@id = 'tasks']//span[contains(text(), 'Delete Multi-configuration project')]")).click();
        getDriver().switchTo().alert().accept();
    }

    @Test
    public void testCreateMultiConfigurationProjectWithValidName_HappyPath() {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.cssSelector("li .model-link")).click();

        Assert.assertEquals(getDriver().findElement(FIRST_MC_PROJECT).getText(), PROJECT_NAME);

        deleteNewMCProject();
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