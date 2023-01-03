package model.base;

import model.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import runner.BaseModel;

public abstract class BasePage extends BaseModel {

    @FindBy(id = "breadcrumbs")
    public WebElement breadcrumbs;

    @FindBy(css = "#breadcrumbs li a")
    protected WebElement topMenuRoot;

    protected Header headerComponent;

    protected Footer footerComponent;

    protected Breadcrumbs breadcrumbsComponent;

    public BasePage(WebDriver driver) {
        super(driver);
        this.headerComponent = new Header(getDriver());
        this.footerComponent = new Footer(getDriver());
        this.breadcrumbsComponent = new Breadcrumbs(getDriver());
    }

    public String getCurrentURL() {

        return getDriver().getCurrentUrl();
    }

    public String getTextBreadcrumbs() {
        return breadcrumbs.getText();
    }

    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }
}
