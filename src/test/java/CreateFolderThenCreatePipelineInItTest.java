import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.UUID;

public class CreateFolderThenCreatePipelineInItTest extends BaseTest {

    @Ignore
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

        getDriver().findElement(By.xpath("//ul[@id='breadcrumbs']/li/a")).click();
        getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr/td[3]/a")).click();
        getDriver().findElement(By.xpath("//ul[@id='breadcrumbs']/li/a")).click();
        getDriver().findElement(By.xpath("//table[@id='projectstatus']/tbody/tr/td[3]/a")).click();
        getWait(10).until(ExpectedConditions.presenceOfElementLocated(By.linkText("Delete Folder")));
        getDriver().findElement(By.linkText("Delete Folder")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
        getDriver().findElement(By.id("search-box")).sendKeys(folderName + Keys.ENTER);

        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@id='page-body']/div/div")).isDisplayed());
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
