package model;

import model.base.BasePage;
import model.folder.FolderStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ConfigurationGeneralPage extends BasePage {
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    @FindBy(name = "_.displayNameOrNull")
    private WebElement displayName;

    @FindBy(name = "_.description")
    private WebElement description;

    public ConfigurationGeneralPage(WebDriver driver) {
        super(driver);
    }

    public ConfigurationGeneralPage setProjectName(String name) {
        getWait(5).until(ExpectedConditions.visibilityOf(displayName)).sendKeys(name);

        return this;
    }

    public ConfigurationGeneralPage setDescription(String text) {
        getWait(5).until(ExpectedConditions.visibilityOf(description)).sendKeys(text);

        return this;
    }

    public FolderStatusPage clickSaveButton() {
        saveButton.click();

        return new FolderStatusPage(getDriver());
    }
}
