package model.organization_folder;

import model.HomePage;
import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrgFolderStatusPage extends BasePage {

    @FindBy(xpath = "//a[text()='Dashboard']")
    private WebElement dashboard;

    @FindBy(linkText = "Rename")
    private WebElement renameButton;

    @FindBy(name = "newName")
    private WebElement newNameLine;

    @FindBy(id = "yui-gen1-button")
    private WebElement renameButtonOnMainPanel;

    @FindBy(linkText = "Configure")
    private WebElement configureButton;

    @FindBy(id = "view-message")
    private WebElement description;

    @FindBy(xpath = "//h1")
    private WebElement displayName;

    @FindBy(xpath = "//div[@id='tasks']//a[contains(@href, 'delete')]")
    private WebElement deleteButton;

    @FindBy(xpath = "//button[@type= 'submit']")
    private WebElement saveButton;

    public OrgFolderStatusPage(WebDriver driver) {
        super(driver);
    }

    public HomePage goToDashboard() {
        dashboard.click();

        return new HomePage(getDriver());
    }

    public OrgFolderStatusPage clickRenameButton() {
        renameButton.click();

        return new OrgFolderStatusPage(getDriver());
    }

    public OrgFolderStatusPage clearAndInputNewName(String name) {
        newNameLine.clear();
        newNameLine.sendKeys(name);
        renameButtonOnMainPanel.click();

        return new OrgFolderStatusPage(getDriver());
    }

    public OrgFolderConfigPage clickConfigureSideMenu() {
        configureButton.click();

        return new OrgFolderConfigPage(getDriver());
    }

    public String getDescription() {
        return description.getText();
    }

    public String getDisplayName() {
        return displayName.getText();
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
