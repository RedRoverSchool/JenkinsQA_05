import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;


public class GroupCodeRedTest extends BaseTest {
    @Test
    public void testAutocomplete() throws InterruptedException {
        getDriver().get("https://formy-project.herokuapp.com/");
        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/autocomplete']"));
        link.click();
        Thread.sleep(500);
        String actualResult = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(actualResult, "Autocomplete");
    }

    @Test
    public void testAutocompleteAddress() throws InterruptedException {
        getDriver().get("https://formy-project.herokuapp.com/");
        WebElement link = getDriver().findElement(By.xpath("//li/a[@href='/autocomplete']"));
        link.click();
        Thread.sleep(500);
        String actualResult = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(actualResult, "Autocomplete");
//        getDriver().findElement(By.id("autocomplete")).clear();
//        getDriver().findElement(By.id("autocomplete")).sendKeys("Sample text");
        WebElement address = getDriver().findElement(By.xpath("//div/input[@placeholder='Enter address']"));
        address.sendKeys("555 Open road");
        Thread.sleep(500);
        getDriver().findElement(By.xpath("//button[@class='dismissButton']")).click();
        WebElement enteredAddress = getDriver().findElement(By.xpath("//input[@id='autocomplete']"));
//        enteredAddress.clear();
        Thread.sleep(500);
        Assert.assertEquals(enteredAddress.getAttribute("value"),"555 Open road");
    }
}
