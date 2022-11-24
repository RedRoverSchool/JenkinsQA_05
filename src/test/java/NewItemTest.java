import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class NewItemTest extends BaseTest {

    private static final String PROJECT_NAME = "New_" + getRandomName(7);

    private static String getRandomName(int length) {
        return RandomStringUtils.random(length,
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
    }

    @Test
    public void testNewItemsPageContainsItemsWithoutCreatedProject() {
        final List<String> expectedResult = List.of("Freestyle project", "Pipeline", "Multi-configuration project",
                "Folder", "Multibranch Pipeline", "Organization Folder");

        getDriver().findElement(By.linkText("New Item")).click();

        List<WebElement> newItemsElements = getDriver().findElements(By.xpath("//label/span[@class='label']"));
        List<String> newItemsName = newItemsElements.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertEquals(newItemsName, expectedResult);
    }

    @Test
    @Ignore
    public void testGoToNewItemPage() {
        final String expectedResult = "Enter an item name";

        getDriver().findElement(By.linkText("New Item")).click();

        Assert.assertEquals(getDriver().findElement(By.className("h3")).getText(), expectedResult);

    }

    @Test
    public void testCreateFolder() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[@class='label' and text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("yui-gen4-button")).click();
        getDriver().findElement(By.xpath("//a[@href='/' and  @class= 'model-link']")).click();

        Assert.assertEquals(getDriver().findElement
                (By.xpath("//table[@id='projectstatus']//a[@href='job/"+ PROJECT_NAME + "/']")).getText(), PROJECT_NAME);
    }

    @Test
    public void testCreateNewItemFreestyleProject() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(getRandomName(15));
        getDriver().findElement(By.xpath("(//button[normalize-space()='Save'])[1]")).click();
        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();

        Assert.assertTrue(getDriver().findElement(By.id("job_" + PROJECT_NAME)).isDisplayed());
    }
}
