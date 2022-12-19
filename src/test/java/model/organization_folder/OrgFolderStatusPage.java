package model.organization_folder;

import model.base.BaseStatusPage;
import model.HomePage;
import model.MovePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrgFolderStatusPage extends BaseStatusPage {

    @FindBy(linkText = "Rename")
    private WebElement renameButton;

    @FindBy(name = "newName")
    private WebElement newNameLine;

    @FindBy(id = "yui-gen1-button")
    private WebElement renameButtonOnMainPanel;

    @FindBy(linkText = "Configure")
    private WebElement configureButton;

    @FindBy(xpath = "//div[@id='tasks']//a[contains(@href, 'delete')]")
    private WebElement deleteButton;

    @FindBy(xpath = "//button[@type= 'submit']")
    private WebElement saveButton;

    @FindBy(linkText = "Move")
    private WebElement moveButton;

    @FindBy(linkText = "Up")
    private WebElement buttonUp;

    public OrgFolderStatusPage(WebDriver driver) {
        super(driver);
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

    public OrgFolderStatusPage clickDeleteOrganizationFolder() {
        deleteButton.click();

        return this;
    }

    public HomePage clickSaveButton() {
        saveButton.click();

        return new HomePage(getDriver());
    }

    public MovePage clickMoveButton(){
        getWait(5).until(ExpectedConditions.elementToBeClickable(moveButton)).click();

        return new MovePage(getDriver());
    }

    public HomePage clickButtonUp(){
        getWait(5).until(ExpectedConditions.elementToBeClickable(buttonUp)).click();

        return new HomePage(getDriver());
    }
}
