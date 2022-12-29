package model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseConfigPage<StatusPage extends BaseStatusPage<?>> extends BasePage {

    @FindBy(name = "Submit")
    protected WebElement saveButton;

    protected abstract StatusPage createStatusPage();

    public BaseConfigPage(WebDriver driver) {
        super(driver);
    }

    public StatusPage clickSaveButton() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(saveButton)).click();

        return createStatusPage();
    }
}
