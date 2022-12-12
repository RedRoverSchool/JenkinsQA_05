package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.TestUtils;


public class EditViewPage extends HomePage {

    @FindBy(css = "input[name=filterQueue]+label")
    private WebElement filterBuildQueueOptionCheckBox;

    @FindBy(name = "name")
    private WebElement viewName;

    @FindBy(css = "#yui-gen6-button")
    private WebElement okButton;

    public EditViewPage(WebDriver driver) {
        super(driver);
    }

    public MyViewsPage clickOkButton() {
        okButton.click();

        return new MyViewsPage(getDriver());
    }

    public EditViewPage filterBuildQueueOptionCheckBoxSelect() {
        filterBuildQueueOptionCheckBox.click();

        return new EditViewPage(getDriver());
    }

    public EditViewPage renameView(String name) {
        viewName.clear();
        viewName.sendKeys(name);

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
}
