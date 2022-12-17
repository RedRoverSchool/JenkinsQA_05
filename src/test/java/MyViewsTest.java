import model.HomePage;
import model.MyViewsPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

public class MyViewsTest extends BaseTest {

    @Test
    public void testAddDescription() {

        String actualResult = new HomePage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickAddDescription()
                .clearDescriptionField()
                .sendKeysInDescriptionField("Description")
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualResult, "Description");
    }

    @Test
    public void testEditDescription() {
        testAddDescription();

        String actualResult = new MyViewsPage(getDriver())
                .clickEditDescription()
                .clearDescriptionField()
                .sendKeysInDescriptionField("New Description")
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualResult, "New Description");
    }
}
