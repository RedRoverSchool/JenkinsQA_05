package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FreeStyleProjectMenuALEXPage extends BasePage {

    @FindBy(css = "#breadcrumbs li a")
    private WebElement topMenuRoot;
    @FindBy(xpath = "//input[@checkdependson='newName']")
    private WebElement renameField;
    @FindBy(id = "yui-gen1")
    private WebElement renameConfirmButton;
    @FindBy(tagName = "p")
    private WebElement errorText;

    public FreeStyleProjectMenuALEXPage(WebDriver driver) {
        super(driver);
    }

    public FreeStyleProjectMenuALEXPage clearRenameField() {
        renameField.clear();

        return this;
    }

    public FreeStyleProjectMenuALEXPage sendTextToRenameField(String text) {
        renameField.sendKeys(text);

        return this;
    }

    public FreeStyleProjectMenuALEXPage clickConfirmButton() {
        renameConfirmButton.click();

        return this;
    }

    public String getErrorText() {
        return errorText.getText();
    }

    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }
}
