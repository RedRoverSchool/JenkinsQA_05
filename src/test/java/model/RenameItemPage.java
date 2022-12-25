package model;

import model.base.BasePage;
import model.freestyle.FreestyleProjectStatusPage;
import model.multibranch_pipeline.MultibranchPipelineStatusPage;
import model.multiconfiguration.MultiConfigurationProjectStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RenameItemPage extends BasePage {

    public RenameItemPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "input[name='newName']")
    private WebElement fieldInputtingNewName;

    @FindBy(xpath = "//button[@type = 'submit']")
    private WebElement buttonSubmit;

    public RenameItemPage clearFieldAndInputNewName(String newName) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(fieldInputtingNewName)).clear();
        fieldInputtingNewName.sendKeys(newName);

        return this;
    }

    public FreestyleProjectStatusPage clickSubmitButton() {
        buttonSubmit.click();

        return new FreestyleProjectStatusPage(getDriver());
    }

    public RenameItemErrorPage clickSaveButton() {
        buttonSubmit.click();
        return new RenameItemErrorPage(getDriver());
    }

    public MultiConfigurationProjectStatusPage clickRenameButton() {
        buttonSubmit.click();

        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public MultibranchPipelineStatusPage clickRenameButtonMBPipeline() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(buttonSubmit)).click();

        return new MultibranchPipelineStatusPage(getDriver());
    }
}
