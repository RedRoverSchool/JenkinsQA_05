package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultiConfigurationProjectStatusPage extends BasePage{

    @FindBy(css = "#breadcrumbs li a")
    private WebElement dashboard;

    @FindBy(id = "yui-gen1-button")
    private WebElement disableButton;

    public MultiConfigurationProjectStatusPage(WebDriver driver) {
        super(driver);
    }

    public HomePage goToDashboard() {
        dashboard.click();

        return new HomePage(getDriver());
    }

    public MultiConfigurationProjectStatusPage clickDisableButton(){
        disableButton.click();

        return new MultiConfigurationProjectStatusPage(getDriver());
    }
}
