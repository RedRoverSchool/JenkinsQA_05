package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ManageJenkinsPage extends BasePage {

    @FindBy(xpath = "//a[@href='configureTools']")
    private WebElement configureTools;

    @FindBy(xpath = "//a[@href='securityRealm/']")
    private WebElement manageUsers;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public GlobalToolConfigurationPage clickConfigureTools() {
        configureTools.click();

        return new GlobalToolConfigurationPage(getDriver());
    }

    public ManageUsersPage clickManageUsers() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(manageUsers)).click();

        return new ManageUsersPage(getDriver());
    }
}
