package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ChangesPage {
    private final WebDriver driver;

    public ChangesPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getText() {
        return driver.findElement(By.id("main-panel")).getText().replace("Changes\n", "");
    }
}
