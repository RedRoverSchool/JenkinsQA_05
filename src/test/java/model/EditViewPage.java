package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class EditViewPage extends MyViewsPage {

    @FindBy(name = "name")
    private WebElement viewNameField;

    @FindBy(name = "description")
    private WebElement descriptionField;

    @FindBy(css = "a.jenkins-help-button")
    private WebElement helpButton;

    @FindBy(css = ".textarea-preview-container")
    private WebElement descriptionFormatStatus;

    @FindBy(css = ".textarea-show-preview")
    private WebElement showPreviewLink;

    @FindBy(css = "input[name=filterQueue]")
    private WebElement filterBuildQueueOptionCheckBox;

    @FindBy(css = "input[name=filterExecutors]")
    private WebElement filterBuildExecutorsOptionCheckBox;

    @FindBy(xpath = "//button[text() = 'OK']")
    private WebElement okButton;

    @FindBy(xpath = "//button[text() = 'Apply']")
    private WebElement applyButton;

    public EditViewPage(WebDriver driver) {
        super(driver);
    }

    public MyViewsPage clickOkButton() {
        okButton.click();

        return new MyViewsPage(getDriver());
    }

    public EditViewPage selectFilterBuildQueueOptionCheckBox() {
        filterBuildQueueOptionCheckBox.findElement(By.xpath("following-sibling::label")).click();

        return this;
    }

    public EditViewPage selectFilterBuildExecutorsOptionCheckBox() {
        filterBuildExecutorsOptionCheckBox.findElement(By.xpath("following-sibling::label")).click();

        return this;
    }

    public EditViewPage renameView(String name) {
        viewNameField.clear();
        viewNameField.sendKeys(name);

        return this;
    }

    public EditViewPage enterDescription(String name) {
        descriptionField.clear();
        descriptionField.sendKeys(name);

        return this;
    }

    public ViewPage clickOk() {
        okButton.click();

        return new ViewPage(getDriver());
    }

    public boolean isFilterBuildQueueOptionCheckBoxChecked() {

        return isCheckboxChecked(filterBuildQueueOptionCheckBox);
    }

    public boolean isFilterBuildExecutorsOptionCheckBoxChecked() {

        return isCheckboxChecked(filterBuildExecutorsOptionCheckBox);
    }
}
