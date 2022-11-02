import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class AlinaAutomationTestStoreTest extends BaseTest {

    @Test
    public void testLoginOrRegister() {
        String url = "https://automationteststore.com/";
        String expectedResult1 = "Create Account";
        String expectedResult2 = "https://automationteststore.com/index.php?rt=account/create";

        getDriver().get(url);
        getDriver().findElement(By.xpath("//ul[@id= 'customer_menu_top']/li/a")).click();
        getDriver().findElement(By.xpath("//form[@id= 'accountFrm']/fieldset/button")).click();
        String actualResult1 = getDriver().getTitle();
        String actualResult2 = getDriver().getCurrentUrl();

        Assert.assertEquals(actualResult1, expectedResult1);
        Assert.assertEquals(actualResult2, expectedResult2);
    }

}
