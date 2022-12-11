package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

public class MulticonfigurationProjectConfigPage extends BasePage {

    @FindBy(css = "#breadcrumbs li a")
    private WebElement dashboard;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    @FindBy(id = "build-steps")
    private WebElement buildStepsSection;

    @FindBy(id = "yui-gen15")
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

    public MulticonfigurationProjectConfigPage scrollAndClickBuildSteps(){
        TestUtils.scrollToElement(getDriver(), buildStepsSection);
        addBuildStepButton.click();

        return this;
    }

    public MulticonfigurationProjectConfigPage selectionAndClickExecuteWindowsFromBuildSteps() {
        executeWindowsFromBuildSteps.click();

        return this;
    }

    public MulticonfigurationProjectConfigPage enterCommandInBuildSteps(String command) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(advancedBuildStepsButton));//
        textAreaBuildSteps.sendKeys(command);

        return this;
    }
}
