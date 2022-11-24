import org.openqa.selenium.By;
import runner.BaseTest;

public class CreateListViewTest extends BaseTest {

    private static final By DASHBOARD = By.id("jenkins-name-icon");

    private void createFreestyleProject(String name) {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(name);
        getDriver().findElement(By.xpath("//img[@class='icon-freestyle-project icon-xlg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(DASHBOARD).click();
    }
}
