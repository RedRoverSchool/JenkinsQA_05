package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import javax.swing.text.View;

public class EditViewPage extends ViewPage {

    @FindBy(css = "input[name=filterQueue]")
    private WebElement filterBuildQueueOptionCheckBox;

    @FindBy(css = "input[name=filterExecutors]")
    private WebElement filterBuildExecutorsOptionCheckBox;

    @FindBy(name = "name")
    private WebElement viewName;

    @FindBy(xpath = "//button[text() = 'OK']")
    private WebElement okButton;

    @FindBy(xpath = "//button[text() = 'Apply']")
    private WebElement applyButton;

    @FindBy(css = ".jenkins-form-description")
    private WebElement uniqueTextOnGlobalViewEditPage;

    @FindBy(css = "div:nth-of-type(5) > .jenkins-section__title")
    private WebElement uniqueSectionOnListViewEditPage;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement description;

    @FindBy(css = "#notification-bar")
    private WebElement confirmAfterClickingApply;

    @FindBy(css = "input[checked='true']")
    private WebElement markedCheckboxNameJob;

    public EditViewPage(WebDriver driver) {
        super(driver);
    }

    public MyViewsPage clickListOrMyViewOkButton() {
        okButton.click();

        return new MyViewsPage(getDriver());
    }

    public EditViewPage renameView(String name) {
        viewName.clear();
        viewName.sendKeys(name);

        return this;
    }

    public ViewPage clickGlobalViewOkButton() {
        okButton.click();

        return new ViewPage(getDriver());
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

    public EditViewPage selectFilterBuildQueueOptionCheckBox() {
        filterBuildQueueOptionCheckBox.findElement(By.xpath("following-sibling::label")).click();

        return this;
    }

    public EditViewPage selectFilterBuildExecutorsOptionCheckBox() {
        filterBuildExecutorsOptionCheckBox.findElement(By.xpath("following-sibling::label")).click();

        return this;
    }

    public boolean isFilterBuildQueueOptionCheckBoxSelected() {

        return filterBuildQueueOptionCheckBox.isSelected();
    }

    public boolean isFilterBuildExecutorsOptionCheckBoxSelected() {

        return filterBuildExecutorsOptionCheckBox.isSelected();
    }

    public EditViewPage clickApplyButton() {
        applyButton.click();

        return this;
    }

    public String getTextConfirmAfterClickingApply() {

        return getWait(15).until(ExpectedConditions.visibilityOf(
                confirmAfterClickingApply)).getText();
    }

    public String getSelectedJobName() {

        return markedCheckboxNameJob.getAttribute("name");
    }
}
