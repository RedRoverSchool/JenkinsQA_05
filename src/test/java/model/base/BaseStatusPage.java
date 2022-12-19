package model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseStatusPage extends BasePage {

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(css = "#description>div:first-child")
    private WebElement description;

    public BaseStatusPage(WebDriver driver) {
        super(driver);
    }

    public String getNameText() {

        return header.getText();
    }

    public String getDescriptionText() {

        return description.getText();
    }
}
