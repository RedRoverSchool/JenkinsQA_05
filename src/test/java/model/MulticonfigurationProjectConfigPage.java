package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MulticonfigurationProjectConfigPage extends BasePage {

    @FindBy(css = "#breadcrumbs li a")
    private WebElement dashboard;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    public MulticonfigurationProjectConfigPage(WebDriver driver) {
        super(driver);
    }

    public MultiConfigurationProjectStatusPage clickSave() {
        saveButton.click();

        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public HomePage goToDashboard() {
        dashboard.click();

        return new HomePage(getDriver());
    }
}
