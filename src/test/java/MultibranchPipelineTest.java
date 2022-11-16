import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class MultibranchPipelineTest extends BaseTest {

    @Test
    public void testCreateMbPipelineEmptyName() {

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//span[text()='Multi-configuration project']")).click();
        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(getDriver().findElement(By.xpath("//button[@type='submit']")).isEnabled());
    }
}
