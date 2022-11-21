package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FreestyleConfigurationPage {
    private final WebDriver driver;

    public FreestyleConfigurationPage(WebDriver driver) {
        this.driver = driver;
    }

    public FreestyleProjectPage saveConfiguration() {
        driver.findElement(By.name("Submit")).click();
        return new FreestyleProjectPage(driver);
    }
}
