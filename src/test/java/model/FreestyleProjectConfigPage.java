package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FreestyleProjectConfigPage extends BasePage {

    @FindBy(tagName = "h1")
    private WebElement headline;

    @FindBy(css = "#main-panel > p")
    private WebElement errorMsg;

    @FindBy(name = "Submit")
    private WebElement saveBtn;

    @FindBy(xpath = "//span/label[text()='Discard old builds']")
    private WebElement discardOldBuildsCheckbox;

    @FindBy(xpath = "//input[@name='_.daysToKeepStr']")
    private WebElement daysToKeepBuilds;

    @FindBy(xpath = "//input[@name='_.numToKeepStr']")
    private WebElement maxNumberOfBuildsToKeep;

    @FindBy(xpath = "//li[@class='item'][2]")
    private WebElement projectButton;

    public FreestyleProjectConfigPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectStatusPage clickSaveBtn() {
        saveBtn.click();

        return new FreestyleProjectStatusPage(getDriver());
    }

    public String getHeadlineText() {
        return headline.getText();
    }

    public String getErrorMsg() {
        return errorMsg.getText();
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
    
    public FreestyleProjectConfigPage typeMaxNumberOfBuildsToKeep(String numberOfBuilds) {
        maxNumberOfBuildsToKeep.sendKeys(numberOfBuilds);

        return this;
    }

    public String getMaxNumberOfBuildsToKeep() {

        return maxNumberOfBuildsToKeep.getAttribute("value");
    }
    
    public String getFreestyleProjectName(String name){

        return projectButton.getText();
        }
}
