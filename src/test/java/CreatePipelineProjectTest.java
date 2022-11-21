import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import static old.GroupBughuntersTest.getRandomDigitAndLetterString;

public class CreatePipelineProjectTest extends BaseTest {

    private static final String PIPELINE_NAME = RandomStringUtils.randomAlphanumeric(10);

    @Test
    public void testCreatePipelineProject(){
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(
                By.xpath("//span[contains(@class, 'label') and text() = 'Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//div[@id='main-panel']/h1")).getText()
                , "Pipeline " + PIPELINE_NAME);
    }
}
