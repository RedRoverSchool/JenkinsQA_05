import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.time.Duration;

public class UserProfileTest extends BaseTest {

    private static final By USER_ICON = By.xpath("//a[contains(@href, 'user')]");
    private static final By ADD_DES = By.id("description-link");
    private static final By INPUT_FIELD = By.tagName("textarea");
    private static final By SAVE_BUTTON = By.id("yui-gen1-button");
    private static final String  TEXT = RandomStringUtils.randomAlphanumeric(50);

    private WebDriverWait wait;

    private WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        }
        return wait;
    }
        @Test
        public void testUserProfileAddDescription () {
            getWait().until(ExpectedConditions.elementToBeClickable(USER_ICON)).click();
            getWait().until(ExpectedConditions.elementToBeClickable(ADD_DES)).click();
            getDriver().findElement(INPUT_FIELD).clear();
            getDriver().findElement(INPUT_FIELD).sendKeys(TEXT);
            getDriver().findElement(SAVE_BUTTON).click();

            Assert.assertTrue(getDriver().
                    findElement(By.xpath("//div[contains(text(),'"+TEXT+"')]")).isDisplayed());

            getWait().until(ExpectedConditions.elementToBeClickable(ADD_DES)).click();
            getDriver().findElement(INPUT_FIELD).clear();
            getDriver().findElement(SAVE_BUTTON).click();
        }
    }