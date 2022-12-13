package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class MainPage extends BasePage {

    @FindBy(css = "#breadcrumbs li a")
    private WebElement topMenuRoot;

    @FindBy(css = "#search-box")
    private WebElement searchBoxField;

    @FindBy(css = "a[href*='/my-views/']")
    private WebElement myViewsHeaderLink;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }
    public MyViewsPage clickMyViewsHeaderLink() {
        myViewsHeaderLink.click();

        return new MyViewsPage(getDriver());
    }

}
