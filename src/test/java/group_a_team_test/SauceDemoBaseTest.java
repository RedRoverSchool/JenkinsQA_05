package group_a_team_test;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import runner.BaseTest;

public abstract class SauceDemoBaseTest extends BaseTest {
    private Actions action;

    protected Actions getAction() {
        if (action == null) {
            return new Actions(getDriver());
        }
        return action;
    }

    protected void loginIn(String username, String password) {
        getDriver().get(SauceDemoConsts.URL);
        getAction().
                moveToElement(getDriver().findElement(By.id("user-name"))).click().sendKeys(username).
                moveToElement(getDriver().findElement(By.id("password"))).click().sendKeys(password).
                moveToElement(getDriver().findElement(By.id("login-button"))).click().perform();
    }
}
