package model.multiconfiguration;

import model.base.BlankConfigPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

public class MultiConfigurationProjectConfigPage extends BlankConfigPage<MultiConfigurationProjectStatusPage, MultiConfigurationProjectConfigPage> {

    @FindBy(name = "description")
    private WebElement inputDescription;

    @FindBy(className = "textarea-show-preview")
    private WebElement textareaShowPreview;

    @FindBy(xpath = "//div[@class='jenkins-section__title'][@id='build-steps']")
    private WebElement buildStepsSection;

    @FindBy(xpath = "//button[text()='Add build step']")
    private WebElement addBuildStepButton;

    @FindBy(xpath = "//a[text()='Execute Windows batch command']")
    private WebElement executeWindowsFromBuildSteps;

    @FindBy(xpath = "//a[contains(text(),'Execute shell')]")
    private WebElement executeShellFromBuildSteps;

    @FindBy(css = ".CodeMirror-scroll>div")
    private WebElement activateShellTextArea;

    @FindBy(xpath = "//div[@id='build-steps']/..//div[@class='advancedLink']//button")
    private WebElement advancedBuildStepsButton;

    @FindBy(css = ".jenkins-input.fixed-width")
    private WebElement executeWindowsTextArea;

    @FindBy(css = ".CodeMirror textarea")
    private WebElement executeShellTextArea;

    @FindBy(xpath = "//label[@for='enable-disable-project']")
    private WebElement enableOrDisableButton;

    @FindBy(xpath = "//div[@class='jenkins-section__title'][@id='configuration-matrix']")
    private WebElement configurationMatrixSection;

    @FindBy(xpath = "//button[@suffix='axis']")
    private WebElement buttonAddAxis;

    @FindBy(xpath = "//a[@class='yuimenuitemlabel']")
    private WebElement userDefinedAxis;

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

    public MultiConfigurationProjectConfigPage scrollAndClickBuildSteps() {
        getWait(5).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h2")));
        TestUtils.scrollToElement(getDriver(), buildStepsSection);
        getWait(20).until(TestUtils.ExpectedConditions.elementIsNotMoving(addBuildStepButton));
        addBuildStepButton.click();

        return this;
    }

    public MultiConfigurationProjectConfigPage selectionAndClickExecuteWindowsFromBuildSteps() {
        executeWindowsFromBuildSteps.click();

        return this;
    }
    public MultiConfigurationProjectConfigPage selectionAndClickExecuteShellFromBuildSteps() {
        executeShellFromBuildSteps.click();

        return this;
    }

    public MultiConfigurationProjectConfigPage enterCommandInExecuteWindowsBuildSteps(String command) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(advancedBuildStepsButton));
        executeWindowsTextArea.sendKeys(command);

        return this;
    }
    public MultiConfigurationProjectConfigPage enterCommandInExecuteShellBuildSteps(String command) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(advancedBuildStepsButton));
        getWait(5).until(ExpectedConditions.visibilityOf(activateShellTextArea)).click();
        executeShellTextArea.sendKeys(command);

        return this;
    }

    public MultiConfigurationProjectConfigPage clickEnableOrDisableButton() {
        enableOrDisableButton.click();

        return this;
    }

    public MultiConfigurationProjectConfigPage scrollAndClickButtonAddAxis() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), configurationMatrixSection);
        getWait(3).until(TestUtils.ExpectedConditions.elementIsNotMoving(buttonAddAxis));
        buttonAddAxis.click();

        return this;
    }

    public MultiConfigurationProjectConfigPage selectUserDefinedAxis() {
        userDefinedAxis.click();
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), buttonAddAxis);
        getWait(3).until(TestUtils.ExpectedConditions.elementIsNotMoving(buttonAddAxis));

        return this;
    }

    public MultiConfigurationProjectConfigPage enterNameUserDefinedAxis(String projectName, String name, int numberOfSection) {
        getDriver().findElement(By.xpath
                        (String.format("//div[" + numberOfSection + "]/div/div[3]/div[2]/input[contains(@checkurl,'/job/%s/')]", projectName)))
                .sendKeys(name);

        return this;
    }

    public MultiConfigurationProjectConfigPage enterValueUserDefinedAxis(String value, int numberOfSection) {
        getDriver().findElement(By.xpath("//div[" + numberOfSection+ "]/div/div[4]/div[2]/div/div[1]/input[@name='_.valueString']"))
                .sendKeys(value);

        return this;
    }
}
