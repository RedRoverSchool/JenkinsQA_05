package model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseConfigPage<StatusPage extends BaseStatusPage<?>, Self extends BaseConfigPage<?, ?, ?>, ConfigSideMenuFrame extends BaseConfigSideMenuFrame<?>> extends BasePage {

    @FindBy(name = "Submit")
    private WebElement saveButton;

    @FindBy(name = "Apply")
    private WebElement applyButton;

    protected abstract StatusPage createStatusPage();

    protected abstract ConfigSideMenuFrame createSideMenuFrame();

    public BaseConfigPage(WebDriver driver) {
        super(driver);
    }

    public StatusPage clickSaveButton() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(saveButton)).click();

        return createStatusPage();
    }

    public Self clickApplyButton() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(applyButton)).click();

        return (Self)this;
    }

    public ConfigSideMenuFrame getSideMenu(){
        return createSideMenuFrame();
    }
}
