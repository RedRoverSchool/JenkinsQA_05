package model.base;

import org.openqa.selenium.WebDriver;
import runner.BaseModel;

public abstract class BasePage extends BaseModel {

    public BasePage(WebDriver driver) {
        super(driver);
    }
}
