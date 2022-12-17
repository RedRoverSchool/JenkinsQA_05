package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

public class MulticonfigurationProjectConfigPage extends HomePage {

    @FindBy(css = "#breadcrumbs li a")
    private WebElement dashboard;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    @FindBy(name = "description")
    private WebElement inputDescription;

    @FindBy(className = "textarea-show-preview")
    private WebElement textareaShowPreview;

    @FindBy(xpath = "//div[@class='textarea-preview']")
    private WebElement previewArea;

    @FindBy(xpath = "//div[@class='jenkins-section__title'][@id='build-steps']")
    private WebElement buildStepsSection;

    @FindBy(xpath = "//button[text()='Add build step']")
    private WebElement addBuildStepButton;

    @FindBy(id = "yui-gen29")
    private WebElement executeWindowsFromBuildSteps;

    @FindBy(id = "yui-gen38-button")
    private WebElement advancedBuildStepsButton;

    @FindBy(css = ".jenkins-input.fixed-width")
    private WebElement textAreaBuildSteps;

    public MulticonfigurationProjectConfigPage(WebDriver driver) {
        super(driver);
    }

    public MultiConfigurationProjectStatusPage clickSave() {
        saveButton.click();

        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public HomePage goToDashboard() {
        dashboard.click();

        return new HomePage(getDriver());
    }

    public MulticonfigurationProjectConfigPage inputDescription(String description) {
        inputDescription.sendKeys(description);

        return new MulticonfigurationProjectConfigPage(getDriver());
    }

    public MulticonfigurationProjectConfigPage showPreview() {
        getWait(5).until(ExpectedConditions.visibilityOf(textareaShowPreview)).click();

        return new MulticonfigurationProjectConfigPage(getDriver());
    }

    public MultiConfigurationProjectStatusPage getPreview() {
        getWait(5).until(ExpectedConditions.visibilityOf(previewArea)).getText();

        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public MultiConfigurationProjectStatusPage clickSaveButton() {
        getWait(5).until(ExpectedConditions.visibilityOf(saveButton)).click();

        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public MulticonfigurationProjectConfigPage scrollAndClickBuildSteps() {
        getWait(5).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
        TestUtils.scrollToElement(getDriver(), buildStepsSection);
        getWait(20).until(ExpectedConditions.elementToBeClickable(addBuildStepButton));
        addBuildStepButton.click();

        return this;
    }

    public MulticonfigurationProjectConfigPage selectionAndClickExecuteWindowsFromBuildSteps() {
        executeWindowsFromBuildSteps.click();

        return this;
    }

    public MulticonfigurationProjectConfigPage enterCommandInBuildSteps(String command) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(advancedBuildStepsButton));
        textAreaBuildSteps.sendKeys(command);

        return this;
    }
}
