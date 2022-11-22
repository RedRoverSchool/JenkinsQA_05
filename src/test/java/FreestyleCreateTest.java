import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.UUID;

public class FreestyleCreateTest extends BaseTest {
    String x = UUID.randomUUID().toString().substring(0, 2);
    private final String name = "project-" + x;

    private static final By NEW_ITEM = By.xpath("//a[@href='/view/all/newJob']");
    private static final By ITEM_NAME = By.xpath("//input[@id='name']");
    private static final By PROJECT_TYPE = By.xpath("//span[normalize-space()='Freestyle project']");
    private static final By OK_BUTTON = By.cssSelector(".yui-button.primary.large-button");
    private static final By SAVE_BUTTON = By.xpath("//*[@id='yui-gen23']");
    private static final By DASHBOARD = By.xpath("//a[normalize-space()='Dashboard']");
    private static final By JOB_IN = By.xpath("//a[@class='jenkins-table__link model-link inside']");
    private static final By DELETE_PROJECT = By.xpath("//span[contains(text(),'Delete Project')]");
    private static final By ACTUAL_RESULT = By.cssSelector(".jenkins-table__link.model-link.inside");

    @Test
    public void testCreateFreestyleProject() {
        getDriver().findElement(NEW_ITEM).click();
        getDriver().findElement(ITEM_NAME).sendKeys(name);
        getDriver().findElement(PROJECT_TYPE).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
        getDriver().findElement(DASHBOARD).click();

        Assert.assertEquals(getDriver().findElement(ACTUAL_RESULT).getText(), name);

        getDriver().findElement(JOB_IN).click();
        getDriver().findElement(DELETE_PROJECT).click();
        getDriver().switchTo().alert().accept();
    }
}
