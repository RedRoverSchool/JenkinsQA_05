package model.organization_folder;

import model.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrgFolderConfigPage extends HomePage {

    @FindBy(id = "yui-gen15-button")
    private WebElement saveButton;

    @FindBy(xpath = "//input  [@name='_.displayNameOrNull']")
    private WebElement displayName;

    @FindBy(xpath = "//textarea [@name='_.description']")
    private WebElement description;

    @FindBy(id = "itemname-required")
    private WebElement errorMessageEmptyField;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    public OrgFolderConfigPage(WebDriver driver) {
        super(driver);
    }

    public OrgFolderStatusPage clickSaveButton() {
        saveButton.click();

        return new OrgFolderStatusPage(getDriver());
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
