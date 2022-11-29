import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class NewFullUserNameTest extends BaseTest {
    private static final By MANAGE_JENKINS_BUTTON = By.xpath("//div[4]/span/a");
    private static final By MANAGE_USER_BUTTON = By.xpath("//section[3]/div/div[4]/a[@href='securityRealm/']");
    private static final By PINION_BUTTON = By.xpath("//td[4]/div/a");
    private static final By FULL_NAME_FIELD = By.name("_.fullName");
    private static final By HEADER = By.cssSelector("a[class='model-link'] span[class='hidden-xs hidden-sm']");
    private static final String NEW_FULL_NAME = String.format("%s %s", RandomStringUtils.randomAlphanumeric(5), RandomStringUtils.randomAlphanumeric(7));
    private String actualResult = "";

    @Test
    public void testNewUsername() {
        getDriver().findElement(MANAGE_JENKINS_BUTTON).click();
        getDriver().findElement(MANAGE_USER_BUTTON).click();
        getDriver().findElement(PINION_BUTTON).click();
        getDriver().findElement(FULL_NAME_FIELD).clear();
        getDriver().findElement(FULL_NAME_FIELD).sendKeys(NEW_FULL_NAME);
        getDriver().findElement(By.name("Submit")).click();

        actualResult = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(actualResult, NEW_FULL_NAME);
    }

    @Test(dependsOnMethods = "testNewUsername")
    public void testNewUsernameAfterReLogging() {
        getDriver().findElement(By.xpath("//div[3]/a[2][@href='/logout']")).click();
        loginWeb();

        actualResult = getDriver().findElement(HEADER).getText();

        Assert.assertEquals(actualResult, NEW_FULL_NAME);
    }
}