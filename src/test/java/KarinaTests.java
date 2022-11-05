import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import runner.BaseTest;

public class KarinaTests extends BaseTest {

    @Test
    public void testLoginAndPassword() throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        WebElement login = driver.findElement(By.xpath("//div[@class='form_group']/input"));
        login.sendKeys("standard_user");
        Thread.sleep(1000);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");
        Thread.sleep(1000);
        WebElement loginButton = driver.findElement(By.name("login-button"));
        loginButton.click();
    }
}
