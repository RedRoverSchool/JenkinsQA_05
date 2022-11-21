package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;

        if (!"Dashboard [Jenkins]".equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the home page");
        }
    }

    public JobPage createNewItem() {
        driver.findElement(By.linkText("New Item")).click();
        return new JobPage(driver);
    }

    public ProjectPage selectProjectFromList(String projectName) {
        driver.findElement(By.linkText(projectName)).click();
        return new ProjectPage(driver);
    }
}