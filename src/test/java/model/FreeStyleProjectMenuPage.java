package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FreeStyleProjectMenuPage extends BasePage {

    @FindBy(css = "#breadcrumbs li a")
    private WebElement topMenuRoot;

    @FindBy(xpath = "//input[@checkdependson='newName']")
    private WebElement renameField;

    @FindBy(id = "yui-gen1")
    private WebElement renameConfirmButton;

    @FindBy(tagName = "p")
    private WebElement errorText;

    public FreeStyleProjectMenuPage(WebDriver driver) {
        super(driver);
    }

    public FreeStyleProjectMenuPage clearRenameField() {
        renameField.clear();

        return this;
    }

    public FreeStyleProjectMenuPage sendTextToRenameField(String text) {
        renameField.sendKeys(text);

        return this;
    }

    public FreeStyleProjectMenuPage clickConfirmButton() {
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
