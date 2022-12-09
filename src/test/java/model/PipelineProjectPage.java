package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PipelineProjectPage extends BasePage{

    @FindBy(xpath = "//div[@id='description']//a")
    private WebElement editDescriptionButton;

    @FindBy(xpath = "//div[@id='description']//textarea")
    private WebElement descriptionArea;

    @FindBy(xpath = "//div[@align='right']/span")
    private WebElement saveButton;

    @FindBy(css = "#description >*:first-child")
    private WebElement description;

    public PipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public PipelineProjectPage editDescription(String text) {
        editDescriptionButton.click();
        descriptionArea.clear();
        descriptionArea.sendKeys(text);

        return this;
    }

    public PipelineProjectPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public String getDescription(String name) {

        return description.getAttribute(name);
    }
}
