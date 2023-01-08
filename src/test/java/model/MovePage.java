package model;

import model.base.BasePage;
import model.base.BaseStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class MovePage<StatusPage extends BaseStatusPage> extends BasePage {

    @FindBy(css = ".select.setting-input")
    private WebElement dropdown;

    @FindBy(xpath = "//button[text()='Move']")
    private WebElement moveButton;

    private final StatusPage statusPage;

    public MovePage(WebDriver driver, StatusPage statusPage) {
        super(driver);
        this.statusPage = statusPage;
    }

    public MovePage<StatusPage> selectFolder(String name) {
        new Select(dropdown).selectByVisibleText("Jenkins » " + name);

        return this;
    }

    public StatusPage clickMove() {
        moveButton.click();

        return statusPage;
    }

    public MovePage<StatusPage> selectOptionToDashBoard(){
        new Select(dropdown).selectByVisibleText("Jenkins");

        return this;
    }
}
