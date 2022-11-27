import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class BuildHistoryTest extends BaseTest {


    @Test
    public void testVerifyRedirectToMainPage() {
        getDriver().findElement(
                By.linkText("Build History")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description-link")).getText(),
                "Add description");
    }
}
