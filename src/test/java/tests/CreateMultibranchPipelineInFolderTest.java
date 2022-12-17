package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

public class CreateMultibranchPipelineInFolderTest extends BaseTest {
    private final String randomName = TestUtils.getRandomStr(10);

    @Test
    public void testCreateMultibranchPipelineInFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(randomName);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getWait(2);
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']"))
                .sendKeys(randomName + "1");
        getDriver().findElement(By.xpath("//textarea[@name='_.description']"))
                .sendKeys(randomName + "2");
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//span[@class='trailing-icon']")).click();
        getDriver().findElement(By.id("name")).sendKeys(randomName + "3");
        getDriver().findElement((By.xpath("//span[text() = 'Multibranch Pipeline']"))).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']"))
                .sendKeys(randomName + "3");
        getDriver().findElement(By.xpath("//textarea[@name='_.description']"))
                .sendKeys(randomName + "4");
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), randomName + "3");

        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.linkText(randomName + "1")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), randomName + "1");
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='view-message']")).getText(), randomName + "2");

        getDriver().findElement(By.linkText(randomName + "3")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='view-message']")).getText(), randomName + "4");
    }
}
