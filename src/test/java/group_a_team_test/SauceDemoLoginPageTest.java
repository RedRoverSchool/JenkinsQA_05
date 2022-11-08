package group_a_team_test;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SauceDemoLoginPageTest extends SauceDemoBaseTest {
    @Test
    public void testStandardUserLoginIn() {
        loginIn(SauceDemoConsts.STANDARD_USER, SauceDemoConsts.CORRECT_PASSWORD);
        Assert.assertEquals(getDriver().getCurrentUrl(), SauceDemoConsts.INVENTORY_PAGE_URL);
    }

    @Test
    public void testLockedOutUserLoginIn() {
        loginIn("locked_out_user", SauceDemoConsts.CORRECT_PASSWORD);
        assertShowErrorMsg("Epic sadface: Sorry, this user has been locked out.");
    }

    @Test
    public void testNonExistentUserLoginIn() {
        loginIn("not_existent_user", SauceDemoConsts.CORRECT_PASSWORD);
        assertShowErrorMsg(SauceDemoConsts.WRONG_USERNAME_OR_PASSWORD_ERROR_MSG);
    }

    @Test
    public void testLoginInWithWrongPassword() {
        loginIn(SauceDemoConsts.STANDARD_USER, "secret_Sauce");
        assertShowErrorMsg(SauceDemoConsts.WRONG_USERNAME_OR_PASSWORD_ERROR_MSG);
    }

    private void assertShowErrorMsg(String errorMsg) {
        try {
            Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class='error-message-container error']")).isDisplayed());
            Assert.assertEquals(getDriver().findElement(By.cssSelector("h3[data-test=error]")).getText(), errorMsg);
        } catch (NoSuchElementException noSuchElementException) {
            Assert.fail("Error message wasn't raised");
        }
    }
}
