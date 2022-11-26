import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class CreateFreestyleProjectTest extends BaseTest {

    private static final String VALID_FREESTYLE_PROJECT_NAME = "First project";
    private static final Character INVALID_CHAR = '!';
    private static final String INVALID_FREESTYLE_PROJECT_NAME = INVALID_CHAR + "First project";
    private static final String DESCRIPTION = "Description Text";
    private static final By BUTTON_OK = By.xpath("//button[@type = 'submit']");
    private static final By BUTTON_SAVE = By.xpath("//button[@type = 'submit']");

    @Test
    public void testCreateFreestyleProjectWithValidName() {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(VALID_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.xpath("//form/div[2]/div[1]/ul/li[1]")).click();
        getDriver().findElement(BUTTON_OK).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(BUTTON_SAVE).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                "Project " + VALID_FREESTYLE_PROJECT_NAME);
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(),
                DESCRIPTION);
    }
    @Test
    public void testCreateFreestyleProjectWithInvalidName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(INVALID_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.xpath("//form/div[2]/div[1]/ul/li[1]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("itemname-invalid")));

        Assert.assertEquals(getDriver().findElement(By.id("itemname-invalid")).getText(),
                "» ‘" + INVALID_CHAR + "’ is an unsafe character");
    }

    @Test
    public void testFreestyleProjectSideMenu() {

        final Set<String> expectedFreestyleProjectSideMenu = new TreeSet<>(List.of("General", "Source Code Management", "Build Triggers", "Build Environment", "Build Steps", "Post-build Actions"));

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(RandomStringUtils.randomAlphanumeric(3));
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        List<WebElement> freestyleProjectSideMenu = getDriver().findElements(By.cssSelector("button.task-link"));
        Set<String> actualFreestyleProjectSideMenu = new TreeSet<>();
        for(WebElement menu : freestyleProjectSideMenu) {
            actualFreestyleProjectSideMenu.add(menu.getText());
        }
        Assert.assertEquals(actualFreestyleProjectSideMenu, expectedFreestyleProjectSideMenu);
    }
}

