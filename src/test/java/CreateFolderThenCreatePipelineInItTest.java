import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class CreateFolderThenCreatePipelineInItTest extends BaseTest {
    private String folderName = getRandomDigitAndLetterString();
    private String pipelineName = getRandomDigitAndLetterString();
    private final int NUMBER_OF_JOBS_TO_RUN = 3;

    @Test
    public void testToVerifyPipelineInFolderCreationAndBuildRunNtimes() throws InterruptedException {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("yui-gen6-button")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + folderName + "/../../']")).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + folderName + "/']")).click();
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        WebElement selectHelloWorld = getDriver().findElement(By.xpath("//div[@class='jquery-ui-1']/div/select"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", selectHelloWorld);
        Select select = new Select(selectHelloWorld);
        select.selectByVisibleText("Hello World");
        getDriver().findElement(By.id("yui-gen6-button")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(9));
        int i = 1;
        while (i <= NUMBER_OF_JOBS_TO_RUN) {
            getDriver().findElement(By.xpath("//div[@id='tasks']/div[4]/span/a")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='/job/" + folderName + "/job/" + pipelineName + "/" + i + "/'][1]")));
            i++;
        }
        List<WebElement> listWithTr = getDriver().findElements(By.xpath("//table[@class='pane jenkins-pane stripped']//tr"));

        Assert.assertEquals(listWithTr.size(), NUMBER_OF_JOBS_TO_RUN + 1);


    }

    public static String getRandomDigitAndLetterString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
