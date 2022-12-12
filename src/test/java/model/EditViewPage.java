package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.TestUtils;

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

    @FindBy(name = "name")
    private WebElement viewName;

    @FindBy(css = ".jenkins-form-description")
    private WebElement uniqueTextOnGlobalViewEditPage;

    @FindBy(css = "div:nth-of-type(5) > .jenkins-section__title")
    private WebElement uniqueSectionOnListViewEditPage;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement description;

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

    public EditViewPage addJobToView(String name) {
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(
                By.xpath(String.format("//label[@title='%s']", name)))).click();

        return this;
    }

    public String getUniqueTextOnGlobalViewEditPage() {

        return uniqueTextOnGlobalViewEditPage.getText();
    }

    public String getUniqueSectionOnListViewEditPage() {

        return uniqueSectionOnListViewEditPage.getText();
    }

    public EditViewPage addDescription(String desc) {
        description.sendKeys(desc);

        return new EditViewPage(getDriver());
    }


    public boolean isFilterBuildQueueOptionCheckBoxChecked() {

        return isCheckboxChecked(filterBuildQueueOptionCheckBox);
    }

    public boolean isFilterBuildExecutorsOptionCheckBoxChecked() {

        return isCheckboxChecked(filterBuildExecutorsOptionCheckBox);
    }
}
