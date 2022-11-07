package group_a_team;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import runner.BaseTest;

public abstract class SauceDemoBaseTest extends BaseTest {
    protected final static String URL = "https://www.saucedemo.com/";
    protected final static String INVENTORY_PAGE_URL = "https://www.saucedemo.com/inventory.html";
    protected final static String STANDARD_USER = "standard_user";
    protected final static String CORRECT_PASSWORD = "secret_sauce";

    private Actions action;

    protected Actions getAction() {
        if (action == null) {
            return new Actions(getDriver());
        }
        return action;
    }

    @BeforeMethod
    protected void maximizeWindow() {
        getDriver().manage().window().maximize();
    }

    protected void loginIn(String username, String password) {
        getDriver().get(URL);
        getAction().
                moveToElement(getDriver().findElement(By.id("user-name"))).click().sendKeys(username).
                moveToElement(getDriver().findElement(By.id("password"))).click().sendKeys(password).
                moveToElement(getDriver().findElement(By.id("login-button"))).click().perform();
    }
}
