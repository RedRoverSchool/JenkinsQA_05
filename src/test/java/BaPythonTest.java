import org.openqa.selenium.By;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.testng.Assert;
        import org.testng.annotations.Test;
        import runner.BaseTest;


public class BaPythonTest extends BaseTest {
    @Test
    public void testPythonOrg() throws InterruptedException {
        getDriver().get("https://www.python.org/");
        WebElement talks = getDriver().findElement(By.xpath("//*[@id='container']/li[3]/ul/li[2]/a"));
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView(true);",talks);
        talks.click();
        Assert.assertTrue(getDriver().findElement(By.xpath("//dd[contains(text(),'A podcast on Python and related technologies.')]")).isDisplayed());


    }
}

