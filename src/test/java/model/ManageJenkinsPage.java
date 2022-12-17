package model;

import model.base.Header;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ManageJenkinsPage extends Header {

    @FindBy(xpath = "//a[@href='configureTools']")
    private WebElement configureTools;

    @FindBy(xpath = "//a[@href='securityRealm/']")
    private WebElement manageUsers;

    @FindBy(xpath = "//div//h1")
    private WebElement header1;

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

    public String getTextHeader1ManageJenkins(){
        getWait(10).until(ExpectedConditions.visibilityOf(header1));

        return header1.getText();
    }
}
