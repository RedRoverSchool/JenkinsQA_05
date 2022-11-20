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
    public void testToVerifyPipelineInFolderCreationAndBuildRunNtimes(String folderName, String pipelineName) throws InterruptedException {

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
        getDriver().findElement(By.xpath("//ul[@id='breadcrumbs']/li/a")).click();
        getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr/td[3]/a")).click();
        getDriver().findElement(By.xpath("//ul[@id='breadcrumbs']/li/a")).click();
        getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr/td[3]/a")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Delete Folder")));
        getDriver().findElement(By.linkText("Delete Folder")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h2[text()='Start building your software project']")).isDisplayed());


    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @DataProvider(name = "jobs2run")
    public Object[][] data() {
        return new Object[][]{
                {getUUID(), getUUID()}
        };
    }


}
