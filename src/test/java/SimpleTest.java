import org.openqa.selenium.By;
import org.testng.annotations.Test;
import runner.BaseTest;

public class SimpleTest extends BaseTest {

    @Test
    public void testSimple() {
        System.out.println(getDriver().findElement(By.id("description-link")).getText());
    }
}
