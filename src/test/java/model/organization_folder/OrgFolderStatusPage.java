package model.organization_folder;

import model.RenameItemPage;
import model.base.BaseStatusPage;
import model.HomePage;
import model.MovePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrgFolderStatusPage extends BaseStatusPage<OrgFolderStatusPage> {

    @FindBy(linkText = "Rename")
    private WebElement renameButton;

    @FindBy(linkText = "Configure")
    private WebElement configureButton;

    @FindBy(xpath = "//div[@id='tasks']//a[contains(@href, 'delete')]")
    private WebElement deleteButton;

    @FindBy(xpath = "//button[@type= 'submit']")
    private WebElement saveButton;

    public OrgFolderStatusPage(WebDriver driver) {
        super(driver);
    }

    public RenameItemPage<OrgFolderStatusPage> clickRenameButton() {
        renameButton.click();

        return new RenameItemPage<>(getDriver(), new OrgFolderStatusPage(getDriver()));
    }

    public OrgFolderConfigPage clickConfigureSideMenu() {
        configureButton.click();

        return new OrgFolderConfigPage(getDriver());
    }

    public OrgFolderStatusPage clickDeleteOrganizationFolder() {
        deleteButton.click();

        return this;
    }

    public HomePage clickSaveButton() {
        saveButton.click();

        return new HomePage(getDriver());
    }
}
