import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;

public class DeletePipelineProjectTest extends BaseTest {
    private static final String PIPELINE_NAME = RandomStringUtils.randomAlphanumeric(10);
    private static final By TEXT_WELCOME = By.cssSelector("div#main-panel .empty-state-block h1");
    @Test
    public void testDeletePipelineProject() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(
                By.xpath("//span[contains(@class, 'label') and text() = 'Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + PIPELINE_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//span[text() = 'Delete Pipeline']")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(TEXT_WELCOME));

        String actualResult = getDriver().findElement(TEXT_WELCOME).getText();

        Assert.assertEquals(actualResult, "Welcome to Jenkins!");
    }
}
