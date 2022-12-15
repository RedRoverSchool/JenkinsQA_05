package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseConfigPage extends BasePage {

    @FindBy(name = "Submit")
    protected WebElement saveBtn;

    public BaseConfigPage(WebDriver driver) {
        super(driver);
    }

    public abstract BaseStatusPage clickSaveBtn();
}
