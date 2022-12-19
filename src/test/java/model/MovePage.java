package model;

import model.base.BasePage;
import model.folder.FolderStatusPage;
import model.organization_folder.OrgFolderStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class MovePage extends BasePage {

    @FindBy(css = ".select.setting-input")
    private WebElement dropdown;

    @FindBy(xpath = "//button[text()='Move']")
    private WebElement moveButton;

    public MovePage(WebDriver driver) {
        super(driver);
    }

    public MovePage selectFolder(String name) {
        new Select(dropdown).selectByVisibleText("Jenkins » " + name);

        return this;
    }

    public FolderStatusPage clickMove() {
        moveButton.click();

        return new FolderStatusPage(getDriver());
    }

    public OrgFolderStatusPage clickMoveForOrgFolder() {
        moveButton.click();

        return new OrgFolderStatusPage(getDriver());
    }

}
