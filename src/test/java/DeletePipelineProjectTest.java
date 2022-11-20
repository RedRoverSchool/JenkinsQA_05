import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class DeletePipelineProjectTest extends BaseTest {
    private static final String PIPELINE_NAME = RandomStringUtils.randomAlphanumeric(10);

    @Test
    public void testDeletePipelineProject() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(
                By.xpath("//span[contains(@class, 'label') and text() = 'Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("yui-gen6-button")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + PIPELINE_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//span[text() = 'Delete Pipeline']")).click();
        getDriver().switchTo().alert().accept();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'empty-state-block']/h1"))
                .getText(), "Welcome to Jenkins!");

    }
}
