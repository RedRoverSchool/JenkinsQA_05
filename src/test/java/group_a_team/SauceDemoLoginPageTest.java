package group_a_team;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SauceDemoLoginPageTest extends SauceDemoBaseTest {
    @Test
    public void testStandardUserLoginIn() {
        loginIn(SauceDemoBaseConsts.STANDARD_USER, SauceDemoBaseConsts.CORRECT_PASSWORD);
        Assert.assertEquals(getDriver().getCurrentUrl(), SauceDemoBaseConsts.INVENTORY_PAGE_URL);
    }

    @Test
    public void testLockedOutUserLoginIn() {
        loginIn("locked_out_user", SauceDemoBaseConsts.CORRECT_PASSWORD);
        assertRaiseException("Epic sadface: Sorry, this user has been locked out.");
    }

    @Test
    public void testNonExistentUserLoginIn() {
        loginIn("not_existent_user", SauceDemoBaseConsts.CORRECT_PASSWORD);
        assertRaiseException(SauceDemoBaseConsts.WRONG_USERNAME_OR_PASSWORD_EXCEPTION_MSG);
    }

    @Test
    public void testLoginInWithWrongPassword() {
        loginIn(SauceDemoBaseConsts.STANDARD_USER, "secret_Sauce");
        assertRaiseException(SauceDemoBaseConsts.WRONG_USERNAME_OR_PASSWORD_EXCEPTION_MSG);
    }

    private void assertRaiseException(String exception) {
        try {
            Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class='error-message-container error']")).isDisplayed());
            Assert.assertEquals(getDriver().findElement(By.cssSelector("h3[data-test=error]")).getText(), exception);
        } catch (NoSuchElementException noSuchElementException) {
            Assert.fail("Exception wasn't raised");
        }
    }
}
