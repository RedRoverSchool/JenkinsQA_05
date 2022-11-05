import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class KatyaKoksTest extends BaseTest {

    @Test
    public void testKateSuccessOpenUpMenu () {
        getDriver().get("https://katalon-demo-cura.herokuapp.com/");
        String menu = "//body/a[@id='menu-toggle']";
        getDriver().findElement(By.xpath(menu)).click();

        String menuHeader = "//body/nav//a[@href='./']";
        String actualResult = getDriver().findElement(By.xpath(menuHeader)).getText();
        String expectedResult = "CURA Healthcare";

        Assert.assertEquals(actualResult, expectedResult);
    }

}