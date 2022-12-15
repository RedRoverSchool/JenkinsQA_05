package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateItemErrorPage extends BasePage {

    @FindBy(xpath = "//div[@id = 'main-panel']/p")
    private WebElement errorMessage;

    @FindBy(xpath = "//div[@id='error-description']//h2")
    private WebElement errorDescription;

    @FindBy(xpath = "//img[contains(@src,'rage.svg')]")
    private WebElement errorPicture;

    public CreateItemErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public String getErrorDescription(){
        return errorDescription.getText();
    }

    public Boolean isErrorPictureDisplayed(){
        return errorPicture.isDisplayed();
    }

    public String getPageUrl(){
        return getDriver().getCurrentUrl();
    }
}
