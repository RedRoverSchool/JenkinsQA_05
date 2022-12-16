package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class BuildHistoryPage extends HomePage {

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "jenkins-icon-size__items-item")
    private WebElement sizeIcon;

    @FindBy(linkText = "Build Now")
    private WebElement buildNowButton;

    public String getSizeText() {

        return sizeIcon.getText();
    }

    public BuildHistoryPage clickBuildNowButton() {
        buildNowButton.click();

        return this;
    }
}
