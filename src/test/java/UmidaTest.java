import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;


public class UmidaTest extends BaseTest {


    @Test
    public void testChangeLanguage() {
        getDriver().get("https://www.chess.com/");

        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        getDriver().manage().window().maximize();
        jse.executeScript("window.scrollBy(0,document.body.scrollHeight)");

        getDriver().findElement(By.xpath("//button[@aria-label='Change language']")).click();
        getDriver().findElement(By.xpath("//a[@href='/ru']")).click();

        Assert.assertEquals(getDriver().getCurrentUrl(), "https://www.chess.com/ru");
    }
}