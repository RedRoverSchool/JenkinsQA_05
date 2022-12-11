package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FreestyleProjectConfigPage extends BasePage {

    @FindBy(name = "Submit")
    private WebElement saveBtn;

    @FindBy(xpath = "//span/label[text()='Discard old builds']")
    private WebElement discardOldBuildsCheckbox;

    @FindBy(xpath = "//input[@name='_.daysToKeepStr']")
    private WebElement daysToKeepBuilds;

    @FindBy(linkText = "Dashboard")
    private WebElement dashboard;

    @FindBy(xpath = "//div[@class = 'jenkins-app-bar__content']/h1")
    private WebElement headLine;

    @FindBy (xpath = "//a [@rel = 'noopener noreferrer']")
    private WebElement jenkinsCurrentVersion;

    public FreestyleProjectConfigPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectStatusPage clickSaveBtn() {
        saveBtn.click();

        return new FreestyleProjectStatusPage(getDriver());
    }

    public FreestyleProjectConfigPage clickDiscardOldBuildsCheckbox() {
        discardOldBuildsCheckbox.click();

        return this;
    }

    public FreestyleProjectConfigPage typeDaysToKeepBuilds(String numberOfDays) {
        daysToKeepBuilds.sendKeys(numberOfDays);

        return this;
    }

    public String getNumberOfDaysToKeepBuilds() {

        return daysToKeepBuilds.getAttribute("value");
    }

    public HomePage goDashboard() {
        dashboard.click();

        return new HomePage(getDriver());
    }

    public String getHeadTextFreeStyleConfigPage() {

        return headLine.getText();
    }

    public String getJenkinsCurrentVersion() {

        return jenkinsCurrentVersion.getText();
    }
}
