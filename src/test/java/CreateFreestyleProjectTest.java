import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.time.Duration;

public class CreateFreestyleProjectTest extends BaseTest {

    private static final Character INVALID_CHAR = '!';
    private static final String INVALID_FREESTYLE_PROJECT_NAME = INVALID_CHAR + "First project";

    @Test
    public void testCreateFreestyleProjectWithInvalidName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(INVALID_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.xpath("//form/div[2]/div[1]/ul/li[1]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("itemname-invalid")));

        Assert.assertEquals(getDriver().findElement(By.id("itemname-invalid")).getText(),
                "» ‘" + INVALID_CHAR + "’ is an unsafe character");
    }
}

