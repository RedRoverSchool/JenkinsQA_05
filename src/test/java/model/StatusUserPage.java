package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StatusUserPage extends BasePage {

    @FindBy(css = ".model-link > .hidden-xs.hidden-sm")
    private WebElement pageHeaderUserName;

    @FindBy(xpath = "//li[@class='item'][last()]")
    private WebElement breadcrumbsUserName;

    @FindBy(xpath = "//h1")
    private WebElement h1Title;

    @FindBy(id = "description-link")
    private WebElement addDescriptionLink;

    @FindBy(xpath = "//textarea[@name = 'description']")
    private WebElement descriptionInputField;

    @FindBy(xpath = "//a[@class='textarea-show-preview']")
    private WebElement previewLink;

    @FindBy(xpath = "//div[@class='textarea-preview']")
    private WebElement previewField;

    public StatusUserPage(WebDriver driver) {
        super(driver);
    }

    public StatusUserPage refreshPage(){
        getDriver().navigate().refresh();

        return this;
    }

    public String getPageHeaderUserName() {
        return pageHeaderUserName.getText();
    }

    public String getBreadcrumbsUserName() {
        return breadcrumbsUserName.getText();
    }

    public String getH1Title() {
        return h1Title.getText();
    }

    public StatusUserPage clickAddDescriptionLink() {
        addDescriptionLink.click();

        return this;
    }

    public StatusUserPage clearDescriptionInputField() {
        getWait(10).until(ExpectedConditions.visibilityOf(descriptionInputField));
        descriptionInputField.clear();

        return this;
    }
    public StatusUserPage inputTextInDescriptionField(String text) {
        descriptionInputField.sendKeys(text);

        return this;
    }

    public StatusUserPage clickPreviewLink() {
        previewLink.click();

        return this;
    }

    public String getPreviewText() {

        return previewField.getText();
    }
}