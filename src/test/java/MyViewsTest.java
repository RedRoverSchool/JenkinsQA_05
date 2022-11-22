import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class MyViewsTest extends BaseTest {
    final private static String TEST_DESCRIPTION_NAME = "Test";

    public void createDescription() {
        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(TEST_DESCRIPTION_NAME);
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
    }

    public void deleteDescription() {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).clear();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
    }

    @Test
    public void testAddDescription() {
        createDescription();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), TEST_DESCRIPTION_NAME);

        deleteDescription();
    }
}