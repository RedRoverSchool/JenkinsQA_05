package old;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import runner.BaseTest;

public abstract class GroupATeamSauceDemoBaseTest extends BaseTest {
    private Actions action;

    protected final Actions getAction() {
        if (action == null) {
            return new Actions(getDriver());
        }
        return action;
    }

    protected final void loginIn(String username, String password) {
        getDriver().get(GroupATeamSauceDemoUtils.URL_SAUCE_DEMO);
        getAction().
                moveToElement(getDriver().findElement(By.id("user-name"))).click().sendKeys(username).
                moveToElement(getDriver().findElement(By.id("password"))).click().sendKeys(password).
                moveToElement(getDriver().findElement(By.id("login-button"))).click().perform();
    }
}
