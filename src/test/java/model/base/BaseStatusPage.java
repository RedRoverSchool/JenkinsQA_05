package model.base;

import model.MovePage;
import model.organization_folder.OrgFolderStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseStatusPage<Self extends BaseStatusPage<?>> extends BasePage {

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(css = "#description>div:first-child")
    private WebElement ProjectDescription;

    @FindBy(id = "view-message")
    private WebElement folderDescription;

    @FindBy(linkText = "Move")
    private WebElement moveButton;

    public BaseStatusPage(WebDriver driver) {
        super(driver);
    }

    public String getNameText() {
        return header.getText();
    }

    public String getProjectDescriptionText() {
        return ProjectDescription.getText();
    }

    public String getFolderDescriptionText() {
        return folderDescription.getText();
    }

    public MovePage<Self> clickMoveButton() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(moveButton)).click();

        return new MovePage<>(getDriver(), (Self)this);
    }
}
