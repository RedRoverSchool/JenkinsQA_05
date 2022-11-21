package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProjectPage {
    protected final WebDriver driver;

    protected ProjectPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getHeadline() {
        return driver.findElement(By.xpath("//h1")).getText();
    }

    public ChangesPage viewChanges() {
        driver.findElement(By.linkText("Changes")).click();
        return new ChangesPage(driver);
    }

    public ProjectPage buildNow() {
        driver.findElement(By.linkText("Build Now")).click();
        return this;
    }

    public List<WebElement> getBuildsFromHistory() {
        return driver.findElements(By.xpath("//table[@class='pane jenkins-pane stripped']//tr[@page-entry-id]"));
    }

    public boolean hasBuildInHistory() {
        return getBuildsFromHistory().size() > 0;
    }
}
