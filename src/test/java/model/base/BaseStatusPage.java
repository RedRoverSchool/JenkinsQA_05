package model.base;

import model.MovePage;
import model.base.side_menu.BaseStatusSideMenuFrame;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseStatusPage<Self extends BaseStatusPage<?, ?>, StatusSideMenuFrame extends BaseStatusSideMenuFrame<Self>> extends BasePageWithSideMenu<StatusSideMenuFrame> {

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(css = "#description>div:first-child")
    private WebElement description;

    @FindBy(id = "view-message")
    private WebElement additionalDescription;

    @FindBy(linkText = "Move")
    private WebElement moveButton;

    public BaseStatusPage(WebDriver driver) {
        super(driver);
    }

    public String getNameText() {
        return getWait(5).until(ExpectedConditions.elementToBeClickable(header)).getText();
    }

    public String getDescriptionText() {
        return getWait(5).until(ExpectedConditions.visibilityOf(description)).getText();
    }

    public String getAdditionalDescriptionText() {
        return additionalDescription.getText();
    }

    public MovePage<Self> clickMoveButton() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(moveButton)).click();

        return new MovePage<>(getDriver(), (Self)this);
    }
}
