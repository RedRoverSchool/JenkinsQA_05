import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class CreateFreestyleProjectTest extends BaseTest {

    private static final String VALID_FREESTYLE_PROJECT_NAME = "First project";
    private static final String DESCRIPTION = "Description Text";
    private static final By BUTTON_OK = By.xpath("//button[@type = 'submit']");
    private static final By BUTTON_SAVE = By.xpath("//button[@type = 'submit']");

    @Test
    public void testCreateFreestyleProjectWithValidName() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(VALID_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.xpath("//form/div[2]/div[1]/ul/li[1]")).click();
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(BUTTON_SAVE).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                "Project " + VALID_FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(),
                DESCRIPTION);
    }
}

