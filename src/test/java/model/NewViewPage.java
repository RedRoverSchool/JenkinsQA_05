package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewViewPage extends BasePage {

    @FindBy(css = "#name")
    private WebElement nameView;

    @FindBy(xpath = "//label[@for='hudson.model.ListView']")
    private WebElement listViewRadiobutton;

    @FindBy(css = "#ok")
    private WebElement createButton;

    public NewViewPage(WebDriver driver) {
        super(driver);
    }

    public NewViewPage setViewName(String name) {
        nameView.sendKeys(name);

        return new NewViewPage(getDriver());
    }

    public NewViewPage selectListView() {
        listViewRadiobutton.click();

        return new NewViewPage(getDriver());
    }

    public EditViewPage clickCreate() {
        createButton.click();

        return new EditViewPage(getDriver());
    }
}
