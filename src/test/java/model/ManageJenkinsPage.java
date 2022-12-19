package model;

import model.base.Header;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

import static runner.TestUtils.*;

public class ManageJenkinsPage extends Header {

    @FindBy(xpath = "//a[@href='configureTools']")
    private WebElement configureTools;

    @FindBy(xpath = "//a[@href='securityRealm/']")
    private WebElement manageUsers;

    @FindBy(xpath = "//div//h1")
    private WebElement header1;

    @FindBy(xpath = "//a[@href  = 'administrativeMonitor/OldData/']/parent::div")
    private WebElement linkManageOldData;

    @FindBy(xpath = "//a[@href='pluginManager']")
    private WebElement linkPluginManager;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public GlobalToolConfigurationPage clickConfigureTools() {
        configureTools.click();

        return new GlobalToolConfigurationPage(getDriver());
    }

    public ManageUsersPage clickManageUsers() {
        scrollToElement(getDriver(), manageUsers);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(manageUsers)).click();

        return new ManageUsersPage(getDriver());
    }

    public String getTextHeader1ManageJenkins(){
        getWait(10).until(ExpectedConditions.visibilityOf(header1));

        return header1.getText();
    }

    public ManageOldDataPage clickLinkManageOldData(){
        scrollToElement(getDriver(), linkManageOldData);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(linkManageOldData)).click();;

        return new ManageOldDataPage(getDriver());
    }

    public PluginManagerPage clickLinkManagePlugins(){
        scrollToElement(getDriver(), linkPluginManager);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(linkPluginManager)).click();;

        return new PluginManagerPage(getDriver());
    }
}
