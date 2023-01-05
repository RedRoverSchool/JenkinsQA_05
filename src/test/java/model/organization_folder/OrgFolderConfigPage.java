package model.organization_folder;

import model.base.BaseConfigPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrgFolderConfigPage extends BaseConfigPage<OrgFolderStatusPage, OrgFolderConfigPage> {

    @FindBy(xpath = "//input  [@name='_.displayNameOrNull']")
    private WebElement displayName;

    @FindBy(xpath = "//textarea [@name='_.description']")
    private WebElement description;

    @FindBy(id = "itemname-required")
    private WebElement errorMessageEmptyField;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @Override
    protected OrgFolderStatusPage createStatusPage() {
        return new OrgFolderStatusPage(getDriver());
    }

    public OrgFolderConfigPage(WebDriver driver) {
        super(driver);
    }

    public OrgFolderConfigPage inputDisplayName(String name) {
        displayName.sendKeys(name);

        return this;
    }

    public OrgFolderConfigPage inputDescription(String name) {
        description.sendKeys(name);

        return this;
    }
    public String getErrorMessageEmptyField() {
        return errorMessageEmptyField.getText();
    }

    public boolean isOkButtonEnabled() {
        return okButton.isEnabled();
    }
}
