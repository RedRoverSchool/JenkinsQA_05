import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;


public class MultiConfigurationProjectTest extends BaseTest {
    private static final String projectName = "test1";
    private WebDriverWait wait;

    private WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        }
        return wait;
    }

    @Test
    public void testMultiConfigurationProjectDelete(){
        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("New Item"))).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Multi-configuration project')]")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[@class='yui-button primary large-button']")).click();
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();
        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        getDriver().findElement(By.xpath("//tr[@id = 'job_test1']/descendant::td//button")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Delete Multi-configuration project')]")).click();
        getWait().until(ExpectedConditions.alertIsPresent());
        Alert alert = getDriver().switchTo().alert();
        alert.accept();

        List<String> foldersList = getDriver()
                .findElements(By.xpath("//tr/td[3]/a/span"))
                .stream()
                .map(element -> element.getText())
                .collect(Collectors.toList());

        Assert.assertFalse(foldersList.contains(projectName));
    }
}
