import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;


public class JkTest extends BaseTest {
        @Test
        public void testManageCookies_MainPage() throws InterruptedException {
            WebDriver driver = getDriver();

            String url = "https://openweathermap.org/";

            String expectedResult = "We use cookies which are essential for the site to work." +
                    " We also use non-essential cookies to help us improve our services. " +
                    "Any data collected is anonymised. " +
                    "You can allow all cookies or manage them individually.";

            driver.get(url);
            Thread.sleep(5000);

            WebElement acceptCookiesField = driver.findElement(
                    By.xpath("//div[@id='stick-footer-panel']//p")
            );

            String actualResult = acceptCookiesField.getText();

            Assert.assertEquals(actualResult, expectedResult);

        }
    }

