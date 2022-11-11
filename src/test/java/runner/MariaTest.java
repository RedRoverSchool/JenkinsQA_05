package runner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Random;

public class MariaTest extends BaseTest {
    @Test
    public void testSignUp() {
        getDriver().get("https://www.incollect.com/");
        getDriver().findElement(By.cssSelector("[href=\"https://www.incollect.com/cms_login/sign_up\"]")).click();
        getDriver().findElement(By.id("edit-first-name")).sendKeys("Maria");
        getDriver().findElement(By.id("edit-last-name")).sendKeys("Shchepina");
        Random random = new Random();
        int n = random.nextInt(30) + 1;
        String email = "Shchepina" + n + "@mail.ru";
    }
}