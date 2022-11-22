import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class FreestyleProjectSecondTest extends BaseTest {
    private static final String FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final String NEW_FREESTYLE_NAME = RandomStringUtils.randomAlphanumeric(10);
    public void createFreestyleProject() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(FREESTYLE_NAME);
        getDriver().findElement(By.xpath("//img[@class='icon-freestyle-project icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testCreateFreestyleProject(){

        createFreestyleProject();

        Assert.assertEquals(getDriver().findElement(By.xpath("//li/a[@href='/job/" + FREESTYLE_NAME + "/']"))
                .getText(), FREESTYLE_NAME);
    }
}
