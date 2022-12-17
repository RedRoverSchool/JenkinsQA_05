package model;

import model.base.BaseConfigPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FolderConfigPage extends BaseConfigPage {

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButtonForDeleteFolder;

    @FindBy(xpath = "//input[@name='_.displayNameOrNull']")
    private WebElement displayName;

    @FindBy(xpath = "//textarea[@name='_.description']")
    private WebElement description;

    public FolderConfigPage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickSubmitDeleteProject() {
        submitButtonForDeleteFolder.click();

        return new HomePage(getDriver());
    }

    public FolderConfigPage clickDisplayName(String secondJobName) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(displayName));
        displayName.sendKeys(secondJobName);

        return this;
    }

    public FolderConfigPage clickDescription(String inputDescription) {
        getWait(5).until(ExpectedConditions.visibilityOf(description)).click();
        description.sendKeys(inputDescription);

        return this;
    }
}
