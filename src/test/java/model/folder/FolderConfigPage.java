package model.folder;

import model.base.BaseConfigPage;
import model.HomePage;
import model.MainConfigSideMenuFrame;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FolderConfigPage extends BaseConfigPage<FolderStatusPage, FolderConfigPage, MainConfigSideMenuFrame<FolderConfigPage>> {

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

    @Override
    protected MainConfigSideMenuFrame<FolderConfigPage> createConfigSideMenuFrame() {
        return new MainConfigSideMenuFrame<>(getDriver(), this);
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
