import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateFolderThenCreatePipelineInItTest extends BaseTest {

    @Test(dataProvider = "jobs2run")
    public void testToVerifyPipelineInFolderCreationAndBuildRunNtimes(String folderName, String pipelineName, int numberOfJobs2Run) throws InterruptedException {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("yui-gen6-button")).click();
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
        while (i <= numberOfJobs2Run) {
            getDriver().findElement(By.xpath("//div[@id='tasks']/div[4]/span/a")).click();
            Thread.sleep(5000);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='buildHistory']/div[2]/table/tbody/tr/td/div[2]/a")));
            i++;
        }
        List<WebElement> listWithTr = getDriver().findElements(By.xpath("//div[@id='buildHistory']/div[2]/table/tbody/tr/td/div/div/a"));
        wait.until(ExpectedConditions.visibilityOfAllElements(listWithTr));

        Assert.assertEquals(listWithTr.size(), numberOfJobs2Run);

        getDriver().findElement(By.xpath("//div[@id='tasks']/div[6]")).click();
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
        getDriver().findElement(By.xpath("//div[@id='tasks']/div[5]")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='Welcome to Jenkins!']")));
        List<String> lstWithHeaders = getDriver().findElements(By.xpath("//ul[@class='empty-state-section-list']/li/a")).stream().map(e -> e.getText()).collect(Collectors.toList());

        Assert.assertTrue(lstWithHeaders.contains("Set up an agent"));


    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @DataProvider(name = "jobs2run")
    public Object[][] data() {
        return new Object[][]{
                {getUUID(), getUUID(), 8}, {getUUID(), getUUID(), 3}, {getUUID(), getUUID(), 1}
        };
    }


}
