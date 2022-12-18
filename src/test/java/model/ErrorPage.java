package model;

import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ErrorPage extends BasePage {

    @FindBy(xpath = "//*[@id='main-panel']/p")
    private WebElement errorText;

    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorText() {

        return errorText.getText();
    }
}
