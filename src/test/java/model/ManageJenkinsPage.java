package model;

import model.base.FooterComponent;
import model.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

import static runner.TestUtils.*;

public class ManageJenkinsPage extends MainBasePage {

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

    @FindBy(linkText = "Manage Jenkins")
    private WebElement manageJenkins;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public GlobalToolConfigurationPage clickConfigureTools() {
        configureTools.click();

        return new GlobalToolConfigurationPage(getDriver());
    }

    public ManageUsersPage clickManageUsers() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), manageUsers);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(manageUsers)).click();

        return new ManageUsersPage(getDriver());
    }

    public String getTextHeader1ManageJenkins(){
        getWait(10).until(ExpectedConditions.visibilityOf(header1));

        return header1.getText();
    }

    public ManageOldDataPage clickLinkManageOldData(){
        scrollToElement(getDriver(), linkManageOldData);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(linkManageOldData)).click();

        return new ManageOldDataPage(getDriver());
    }

    public PluginManagerPage clickLinkManagePlugins(){
        scrollToElement(getDriver(), linkPluginManager);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(linkPluginManager)).click();

        return new PluginManagerPage(getDriver());
    }

    public FooterComponent moveToJenkinsVersion() {
        scrollToEnd(getDriver());
        WebElement linkJenkins = new HomePage(getDriver()).getFooter().getJenkinsFooterLink();
        getAction().pause(500).moveToElement(getWait(3).until(ExpectedConditions.elementToBeClickable(linkJenkins)))
                .perform();
      
        return new FooterComponent(getDriver());
    }
}
