package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FreestyleProjectConfigPage extends BasePage {

    @FindBy(tagName = "h1")
    private WebElement headline;

    @FindBy(css = "#main-panel > p")
    private WebElement errorMsg;

    @FindBy(name = "Submit")
    private WebElement saveBtn;

    public FreestyleProjectConfigPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectPage clickSaveBtn() {
        saveBtn.click();

        return new FreestyleProjectPage(getDriver());
    }

    public String getHeadlineText() {
        return headline.getText();
    }

    public String getErrorMsg() {
        return errorMsg.getText();
    }
}
