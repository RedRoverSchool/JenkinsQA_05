package model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

public abstract class BaseConfigPage<T> extends BasePage {

    @FindBy(name = "Submit")
    protected WebElement saveBtn;

    public BaseConfigPage(WebDriver driver) {
        super(driver);
    }

    public <K extends BaseStatusPage<K>> K clickSaveBtn(Class<K> statusPageClass) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(saveBtn)).click();

        try {
            return statusPageClass.getDeclaredConstructor(WebDriver.class).newInstance(getDriver());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
