import org.openqa.selenium.By;
import org.testng.annotations.Test;
import runner.BaseTest;

public class FreestyleProjectCreate extends BaseTest {
    @Test
    public void testCreateFreestyleProject() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("sit-1");
        getDriver().findElement(By.xpath("//span[normalize-space()='Freestyle project']")).click();
        getDriver().findElement(By.cssSelector(".yui-button.primary.large-button")).click();
        getDriver().findElement(By.cssSelector("textarea[name='description']")).sendKeys("Timestamp script");
        getDriver().findElement(By.id("yui-gen9")).click();
        /*JavascriptExecutor jsScroll = (JavascriptExecutor) getDriver();
        WebElement buildSteps = getDriver().findElement(By.cssSelector("#yui-gen13-button"));
        jsScroll.executeScript("arguments[0].scrollIntoView();", buildSteps);
        buildSteps.click();*/

    }
}

