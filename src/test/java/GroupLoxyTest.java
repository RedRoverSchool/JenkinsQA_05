import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;

public class GroupLoxyTest extends BaseTest {

    @Test
    public void testScheduleVisitFormJK() {
        getDriver().get("https://www.fitzdentaldallas.com/");
        getDriver().findElement(By.xpath("//div[@id='main']/div//ul/li/a[text()='Wisdom Tooth Removal']")).click();
        getDriver().findElement(By.id("name1")).sendKeys("JuliaTestova");
        getDriver().findElement(By.id("email1")).sendKeys("testgmail.com");
        getDriver().findElement(By.id("phone1")).sendKeys("12345");
        getDriver().findElement(By.xpath("//input[@value='Contact Us']")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='wpcf7-f258-o1']/form/div[@class='wpcf7-response-output']")));

        Assert.assertTrue(getDriver().findElement(
                By.xpath("//div[@id='wpcf7-f258-o1']/form/div[@class='wpcf7-response-output']")).isDisplayed());
    }


}
