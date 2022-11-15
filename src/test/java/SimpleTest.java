import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class SimpleTest extends BaseTest {

    @Test
    public void testSimple() {
        String text = getDriver().findElement(By.id("description-link")).getText();

        Assert.assertEquals(text, "Add description");
    }
}
