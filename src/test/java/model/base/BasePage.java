package model.base;

import model.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.BaseModel;

public abstract class BasePage extends BaseModel {

    @FindBy(id = "breadcrumbs")
    public WebElement breadcrumbs;

    @FindBy(css = "#breadcrumbs li a")
    protected WebElement topMenuRoot;

//    protected abstract FooterFrame createFooterFrame();

    public BasePage(WebDriver driver) {
        super(driver);
    }

//    public FooterFrame getFooter(){
//        return createFooterFrame();
//    }

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
