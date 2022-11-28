import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;


public class BuildHistoryTest extends BaseTest {


    @Test
    public void testVerifyRedirectToMainPage() {
        getDriver().findElement(
                By.linkText("Build History")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description-link")).getText(),
                "Add description");
    }

    @Test
    public void testVerifyDefaultIconSize() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("PROJECT_NAME");
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//body[1]/div[3]/div[1]/div[1]/div[5]/span[1]")).click();
        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        getDriver().findElement(By.linkText("Build History")).click();

        int width = getDriver().findElement(By.
                xpath("//a[@class='jenkins-table__button']//*[name()='svg']")).getSize().getWidth();
        int height = getDriver().findElement(By.
                xpath("//a[@class='jenkins-table__button']//*[name()='svg']")).getSize().getHeight();
        String size = getDriver().findElement(By.className("jenkins-icon-size__items-item")).getText();

        switch (size) {
            case "S\nmall":
                Assert.assertEquals(width, 16);
                Assert.assertEquals(height, 16);
                break;
            case "M\nedium":
                Assert.assertEquals(width, 20);
                Assert.assertEquals(height, 20);
                break;
            case "L\narge":
                Assert.assertEquals(width, 24);
                Assert.assertEquals(height, 24);
                break;
        }
    }
}
