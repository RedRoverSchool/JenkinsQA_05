package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ManageJenkinsPage extends BasePage {

    @FindBy(xpath = "//a[@href='configureTools']")
    private WebElement configureTools;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public GlobalToolConfigurationPage clickConfigureTools() {
        configureTools.click();

        return new GlobalToolConfigurationPage(getDriver());
    }
}
