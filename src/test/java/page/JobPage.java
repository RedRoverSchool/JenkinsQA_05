package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class JobPage {
    private final WebDriver driver;

    public JobPage(WebDriver driver) {
        this.driver = driver;

        if (!"New Item [Jenkins]".equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the job page");
        }
    }

    public JobPage typeNewJobName(String name) {
        driver.findElement(By.id("name")).sendKeys(name);
        return this;
    }

    public FreestyleConfigurationPage asFreestyleProject(String projectName) {
        typeNewJobName(projectName);
        driver.findElement(By.cssSelector("li.hudson_model_FreeStyleProject")).click();
        driver.findElement(By.id("ok-button")).click();

        return new FreestyleConfigurationPage(driver);
    }
}
