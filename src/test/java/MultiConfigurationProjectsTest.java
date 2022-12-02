import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class MultiConfigurationProjectsTest extends BaseTest {
    private static final String PROJECT_RANDOM_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final By DASHBOARD = By.xpath("//img[@id='jenkins-head-icon']");

    @Test
    public void testDisableMultiConfigurationProject(){
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_RANDOM_NAME);
        getDriver().findElement(By.xpath("(//label)[4]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
        getDriver().findElement(DASHBOARD).click();

        Assert.assertTrue(getDriver().findElement
                (By.xpath("(//*[local-name()='svg' and @tooltip='Disabled'])[2]")).isDisplayed());
    }
}
