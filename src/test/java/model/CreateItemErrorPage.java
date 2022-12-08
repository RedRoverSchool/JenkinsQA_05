package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateItemErrorPage extends BasePage {

   @FindBy(xpath = "//div[@id = 'main-panel']/p")
   private WebElement errorMessage;

    public CreateItemErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
