import org.openqa.selenium.WebElement;

import java.time.Duration;

public final class GroupATeamSauceDemoUtils {
    public static final String URL_SAUCE_DEMO = "https://www.saucedemo.com/";
    public static final String STANDARD_USER = "standard_user";
    public static final String CORRECT_PASSWORD = "secret_sauce";
    public static final String WRONG_USERNAME_OR_PASSWORD_ERROR_MSG =
            "Epic sadface: Username and password do not match any user in this service";
    public static final String TITLE_PRODUCTS = "PRODUCTS";
    public static final String TITLE = "Swag Labs";
    public static final Duration MAX_TIMEOUT = Duration.ofSeconds(60);

    public static double getDouble(WebElement webElement, String prefix) {
        return Double.parseDouble(webElement.getText().replace(prefix, ""));
    }
}