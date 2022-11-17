import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class HeaderLogoTest extends BaseTest {

    @Test
    public void testSeeNameIcon() {

        boolean actualResult = getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']"))
                .isDisplayed();

        Assert.assertTrue(actualResult);
    }
}
