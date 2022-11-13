import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupATeamSauceDemoLoginTest extends GroupATeamSauceDemoBaseTest {
    private static final By ERROR_MSG_LOCATOR = By.cssSelector("h3[data-test=error]");

    @Test
    public void testStandardUserLoginIn() {
        loginIn(GroupATeamSauceDemoUtils.STANDARD_USER, GroupATeamSauceDemoUtils.CORRECT_PASSWORD);
        Assert.assertEquals(getDriver().getTitle(), GroupATeamSauceDemoUtils.TITLE);
        Assert.assertEquals(getDriver().findElement(By.cssSelector("span.title")).getText()
                , GroupATeamSauceDemoUtils.TITLE_PRODUCTS);
    }

    @Test
    public void testLockedOutUserLoginIn() {
        loginIn("locked_out_user", GroupATeamSauceDemoUtils.CORRECT_PASSWORD);
        Assert.assertEquals(getDriver().findElement(ERROR_MSG_LOCATOR).getText()
                , "Epic sadface: Sorry, this user has been locked out.");
    }

    @Test
    public void testNonExistentUserLoginIn() {
        loginIn("not_existent_user", GroupATeamSauceDemoUtils.CORRECT_PASSWORD);
        Assert.assertEquals(getDriver().findElement(ERROR_MSG_LOCATOR).getText()
                , GroupATeamSauceDemoUtils.WRONG_USERNAME_OR_PASSWORD_ERROR_MSG);
    }

    @Test
    public void testLoginInWithWrongPassword() {
        loginIn(GroupATeamSauceDemoUtils.STANDARD_USER, "secret_Sauce");
        Assert.assertEquals(getDriver().findElement(ERROR_MSG_LOCATOR).getText()
                , GroupATeamSauceDemoUtils.WRONG_USERNAME_OR_PASSWORD_ERROR_MSG);
    }
}
