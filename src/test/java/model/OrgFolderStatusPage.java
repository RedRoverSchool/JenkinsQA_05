package model;

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
}
