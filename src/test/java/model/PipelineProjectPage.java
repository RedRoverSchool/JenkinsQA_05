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

    @FindBy(xpath = "//span[text()='Delete Pipeline']")
    private WebElement deletePipelineButton;

    @FindBy(id = "yui-gen1-button")
    private WebElement disableProjectButton;

    @FindBy(id = "yui-gen1")
    private WebElement enableProjectButton;

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

    public String getDescription() {

        return description.getAttribute("textContent");
    }

    public HomePage clickDeletePipelineButton() {
        deletePipelineButton.click();
        getDriver().switchTo().alert().accept();

        return new HomePage(getDriver());
    }

    public PipelineProjectPage clickDisableProject() {
        disableProjectButton.click();

        return new PipelineProjectPage(getDriver());
    }

    public PipelineProjectPage clickEnableProject() {
        enableProjectButton.click();

        return new PipelineProjectPage(getDriver());
    }
}
