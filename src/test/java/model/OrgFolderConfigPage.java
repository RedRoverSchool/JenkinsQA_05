package model;

import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrgFolderConfigPage extends BasePage {

    @FindBy(id = "yui-gen15-button")
    private WebElement saveButton;

    public OrgFolderConfigPage(WebDriver driver) {
        super(driver);
    }

    public OrgFolderStatusPage clickSaveButton() {
        saveButton.click();

        return new OrgFolderStatusPage(getDriver());
    }
}
