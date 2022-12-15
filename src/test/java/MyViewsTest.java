import model.HomePage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class MyViewsTest extends BaseTest {
    final private static String TEST_DESCRIPTION_NAME = "Test";
    final private static String DESCRIPTION_NAME_EDIT = "Test1";

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

        String actualResult = new HomePage(getDriver())
                .clickMyViews()
                .clickAddDescription()
                .clearDescriptionField()
                .sendKeysInDescriptionField("Description")
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualResult, "Description");
    }

    @Ignore
    @Test
    public void testEditDescription() {
        createDescription();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).clear();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(DESCRIPTION_NAME_EDIT);
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), DESCRIPTION_NAME_EDIT);

        deleteDescription();
    }
}
