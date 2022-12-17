package model.base;

import model.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.BaseModel;

public abstract class BasePage extends BaseModel {

    @FindBy(css = "#breadcrumbs li a")
    protected WebElement topMenuRoot;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public String getCurrentURL() {

        return getDriver().getCurrentUrl();
    }

    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }
}
