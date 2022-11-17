import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class FooterLinksVisibilityTest extends BaseTest {

    private static final By REST_API_LINK = By.xpath("//*[@id='jenkins']/footer/div/div/div[2]/a");

    @Test
    public void testFooterLinkIsDisplayed(){

        Assert.assertTrue(getDriver().findElement(REST_API_LINK).isDisplayed());
    }

    @Test
    public void testFooterLinkRedirectToPage(){

        getDriver().findElement(REST_API_LINK).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("api"));
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(), "REST API");
    }
}
