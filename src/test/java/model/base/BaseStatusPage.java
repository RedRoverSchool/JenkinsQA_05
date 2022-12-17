package model.base;

import org.openqa.selenium.WebDriver;

public abstract class BaseStatusPage<T> extends BasePage {

    public BaseStatusPage(WebDriver driver) {
        super(driver);
    }
}
