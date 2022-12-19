package model.multibranch_pipeline;

import model.base.BasePage;
import model.HomePage;
import model.folder.FolderStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DeleteMultibranchPipelinePage extends BasePage {
    @FindBy(xpath = "//button[@type = 'submit']")
    private WebElement submitButton;

    public DeleteMultibranchPipelinePage(WebDriver driver) {
        super(driver);
    }

    public FolderStatusPage clickSubmitButton() {
        submitButton.click();

        return new FolderStatusPage(getDriver());
    }

    public HomePage clickSubmitButtonToHomePage() {
        submitButton.click();

        return new HomePage(getDriver());
    }
}
