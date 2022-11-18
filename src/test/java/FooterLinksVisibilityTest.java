import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class FooterLinksVisibilityTest extends BaseTest {

    private static final By REST_API_LINK = By.xpath("//*[@id='jenkins']/footer/div/div/div[2]/a");
    private static final By JENKINS_LINK = By.xpath("//*[@id='jenkins']/footer/div/div/div[3]/a");

    @Test
    public void testFooterLinkRestIsDisplayed(){

        Assert.assertTrue(getDriver().findElement(REST_API_LINK).isDisplayed());
    }

    @Test
    public void testFooterLinkRestRedirectToPage(){

        getDriver().findElement(REST_API_LINK).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("api"));
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(), "REST API");
    }

    @Test
    public void testFooterLinkJenkinsIdDisplayed(){

        Assert.assertTrue(getDriver().findElement(JENKINS_LINK).isDisplayed());
    }

    @Test
    public void testFooterLinkJenkinsRedirectToPage(){

        getDriver().findElement(JENKINS_LINK).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("jenkins"));
        Assert.assertEquals(getDriver().findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[3]/h1/span")).getText(), "Jenkins");
    }
}
