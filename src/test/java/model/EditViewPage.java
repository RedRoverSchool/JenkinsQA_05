package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditViewPage extends HomePage {

    @FindBy(xpath = "//button[text() = 'OK']")
    private WebElement okButton;

    @FindBy(css = "input[name=filterQueue]+label")
    private WebElement filterBuildQueueOptionCheckBox;

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
}
