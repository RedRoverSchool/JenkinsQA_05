package model;

import model.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class BuildHistoryPage extends HomePage {

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "jenkins-icon-size__items-item")
    private WebElement sizeIcon;

    @FindBy(linkText = "Build Now")
    private WebElement buildNowButton;

    @FindBy(xpath = "//a[@href='/view/all/newJob']")
    private WebElement newJob;

    @FindBy(id = "name")
    private WebElement inputBuildName;

    @FindBy(xpath = "//li[@class='hudson_model_FreeStyleProject']")
    private WebElement newFreeStyleProjectButton;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(name = "description")
    private WebElement descriptionField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    public String getSizeText() {

        return sizeIcon.getText();
    }

    public BuildHistoryPage clickBuildNowButton() {
        buildNowButton.click();

        return this;
    }

    public BuildHistoryPage clickCreateNewJob() {
        newJob.click();

        return this;
    }

    public BuildHistoryPage enterNewBuildName(String newName) {
        inputBuildName.sendKeys(newName);

        return this;
    }

    public BuildHistoryPage clickNewFreestyleProjectButton() {
        newFreeStyleProjectButton.click();

        return this;
    }

    public BuildHistoryPage clickOkButton() {
        okButton.click();

        return this;
    }

    public BuildHistoryPage enterDescriptionField(String description) {
        if (!(description.equals("empty"))) {
            descriptionField.sendKeys(description);
        }

        return this;
    }

    public BuildHistoryPage clickSubmitButton() {
        submitButton.click();

        return this;
    }
}
