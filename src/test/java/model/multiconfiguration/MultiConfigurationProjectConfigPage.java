package model.multiconfiguration;

import model.base.BaseConfigPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

public class MultiConfigurationProjectConfigPage extends BaseConfigPage<MultiConfigurationProjectStatusPage, MultiConfigurationProjectConfigPage> {

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

    @FindBy(xpath = "//a[text()='Execute Windows batch command']")
    private WebElement executeWindowsFromBuildSteps;

    @FindBy(css = ".jenkins-input.fixed-width")
    private WebElement textAreaBuildSteps;

    @FindBy (xpath = "//label[@for='enable-disable-project']")
    private WebElement enableOrDisableButton;

    @Override
    protected MultiConfigurationProjectStatusPage createStatusPage() {
        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public MultiConfigurationProjectConfigPage(WebDriver driver) {
        super(driver);
    }

    public MultiConfigurationProjectConfigPage inputDescription(String description) {
        inputDescription.sendKeys(description);

        return new MultiConfigurationProjectConfigPage(getDriver());
    }

    public MultiConfigurationProjectConfigPage showPreview() {
        getWait(5).until(ExpectedConditions.visibilityOf(textareaShowPreview)).click();

        return new MultiConfigurationProjectConfigPage(getDriver());
    }

    public MultiConfigurationProjectStatusPage getPreview() {
        getWait(5).until(ExpectedConditions.visibilityOf(previewArea)).getText();

        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public MultiConfigurationProjectConfigPage scrollAndClickBuildSteps() {
        getWait(5).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
        TestUtils.scrollToElement(getDriver(), buildStepsSection);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(addBuildStepButton));
        addBuildStepButton.click();

        return this;
    }

    public MultiConfigurationProjectConfigPage selectionAndClickExecuteWindowsFromBuildSteps() {
        executeWindowsFromBuildSteps.click();

        return this;
    }

    public MultiConfigurationProjectConfigPage enterCommandInBuildSteps(String command) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(textAreaBuildSteps));
        textAreaBuildSteps.sendKeys(command);

        return this;
    }

    public MultiConfigurationProjectConfigPage clickEnableOrDisableButton() {
        enableOrDisableButton.click();

        return this;
    }
}
