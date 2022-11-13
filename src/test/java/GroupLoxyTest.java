import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GroupLoxyTest extends BaseTest {
    public static final String URL_PARABANK = "https://parabank.parasoft.com/parabank/index.htm";
    @Test
    public void test_customerServiceResponseText_WhenContactFormSubmitted() {
        final String name = "Mary Johnson";
        final String email = "mj@test.com";
        final String phone = "1234567";
        final String message = "test text";

        final List<String> expectedResult = new ArrayList<>();
        expectedResult.add("Customer Care");
        expectedResult.add("Thank you " + name);
        expectedResult.add("A Customer Care Representative will be contacting you.");

        getDriver().get(URL_PARABANK);
        getDriver().findElement(By.xpath("//div[@id = 'headerPanel']//li[@class = 'contact']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.id("email")).sendKeys(email);
        getDriver().findElement(By.id("phone")).sendKeys(phone);
        getDriver().findElement(By.id("message")).sendKeys(message);
        getDriver().findElement(By.xpath("//form[@id = 'contactForm']//input[@type = 'submit']")).click();

        List<String> actualResult = new ArrayList<>();
        actualResult.add(getDriver().findElement(By.xpath("//div[@id = 'rightPanel']/h1")).getText());
        actualResult.add(getDriver().findElement(By.xpath(".//div[@id = 'rightPanel']//p[1]")).getText());
        actualResult.add(getDriver().findElement(By.xpath(".//div[@id = 'rightPanel']//p[2]")).getText());

        Assert.assertEquals(actualResult, expectedResult);
    }
    @Test
    public void testTopPanelLogoSize_OnHomePage_YM(){
        getDriver().get(URL_PARABANK);
        Assert. assertEquals(getDriver().findElement(
                By.className("logo")).getCssValue("width"), "136px");
        Assert.assertEquals(getDriver().findElement(
                By.className("logo")).getCssValue("height"), "31px");
    }

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
