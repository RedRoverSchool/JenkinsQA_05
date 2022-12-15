package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewViewPage extends MyViewsPage {

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

    public EditViewPage clickCreateButton() {
        createButton.click();

        return new EditViewPage(getDriver());
    }

    public EditViewPage clickCreateListView() {
        createButton.click();

        return new EditViewPage(getDriver());
    }
}
