package model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseConfigPage extends BasePage {

    @FindBy(name = "Submit")
    protected WebElement saveBtn;

    public BaseConfigPage(WebDriver driver) {
        super(driver);
    }

    public <R extends BaseStatusPage> R clickSaveBtn(Class<R> statusPageClass) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(saveBtn)).click();

        try {
            return statusPageClass.getDeclaredConstructor(WebDriver.class).newInstance(getDriver());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
