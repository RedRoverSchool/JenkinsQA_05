package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewViewPage extends BasePage {

    @FindBy(id = "name")
    private WebElement viewName;

    @FindBy(css = "label[for='hudson.model.ProxyView']")
    private WebElement globalViewType;

    @FindBy(css = "label[for='hudson.model.ListView']")
    private WebElement listViewType;

    @FindBy(css = "label[for='hudson.model.MyView']")
    private WebElement myViewType;

    @FindBy(id = "ok")
    private WebElement createButton;

    @FindBy(xpath = "//button[text() = 'OK']")
    private WebElement okButton;


    public NewViewPage(WebDriver driver) {
        super(driver);
    }

    public NewViewPage setViewName(String name) {
        getWait(2).until(ExpectedConditions.visibilityOf(viewName)).sendKeys(name);

        return this;
    }

    public NewViewPage setGlobalViewType() {
        globalViewType.click();

        return this;
    }

    public NewViewPage setListViewType() {
        listViewType.click();

        return this;
    }

    public NewViewPage setMyViewType() {
        myViewType.click();

        return this;
    }

    public MyViewsPage clickCreateButton() {
        createButton.click();

        return new MyViewsPage(getDriver());
    }

    public HomePage clickOkButton() {
        okButton.click();

        return new HomePage(getDriver());
    }
}
