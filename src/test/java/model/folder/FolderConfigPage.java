package model.folder;

import model.base.BlankConfigPage;
import model.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FolderConfigPage extends BlankConfigPage<FolderStatusPage, FolderConfigPage> {

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButtonForDeleteFolder;

    @FindBy(xpath = "//input[@name='_.displayNameOrNull']")
    private WebElement displayName;

    @FindBy(xpath = "//textarea[@name='_.description']")
    private WebElement description;

    @Override
    protected FolderStatusPage createStatusPage() {
        return new FolderStatusPage(getDriver());
    }

    public FolderConfigPage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickSubmitDeleteProject() {
        submitButtonForDeleteFolder.click();

        return new HomePage(getDriver());
    }

    public FolderConfigPage setDisplayName(String secondJobName) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(displayName));
        displayName.sendKeys(secondJobName);

        return this;
    }

    public FolderConfigPage setDescription(String inputDescription) {
        getWait(5).until(ExpectedConditions.visibilityOf(description)).click();
        description.sendKeys(inputDescription);

        return this;
    }
}
