package model;

import model.base.BasePage;
import model.base.BaseStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RenameItemPage<StatusPage extends BaseStatusPage<?, ?>> extends BasePage {

    private final StatusPage statusPage;

    public RenameItemPage(WebDriver driver, StatusPage statusPage) {
        super(driver);
        this.statusPage = statusPage;
    }

    @FindBy(css = "input[name='newName']")
    private WebElement fieldInputtingNewName;

    @FindBy(xpath = "//button[@type = 'submit']")
    private WebElement buttonSubmit;

    public RenameItemPage<StatusPage> clearFieldAndInputNewName(String newName) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(fieldInputtingNewName)).clear();
        fieldInputtingNewName.sendKeys(newName);

        return this;
    }

    public RenameItemErrorPage clickSaveButtonAndGetError() {
        buttonSubmit.click();

        return new RenameItemErrorPage(getDriver());
    }

    public StatusPage clickRenameButton() {
        buttonSubmit.click();

        return statusPage;
    }
}
