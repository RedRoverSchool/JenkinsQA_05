package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static runner.TestUtils.scrollToElement;

public class FreeStyleProjectConfigALEXPage extends BasePage {

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement descriptionField;
    @FindBy(xpath = "//label[text()='GitHub project']")
    private WebElement githubCheckbox;
    @FindBy(xpath = "//div[@class='jenkins-radio']/following-sibling::a")
    private WebElement gitInfoQuestionMark;
    @FindBy(xpath = "//input[@name='hudson-triggers-TimerTrigger']/following-sibling::label")
    private WebElement timeTriggerCheckbox;
    @FindBy(name = "_.spec")
    private WebElement timeTriggerField;
    @FindBy(xpath = "//input[@name='hudson-tasks-AntWrapper']/following-sibling::label")
    private WebElement antCheckbox;
    @FindBy(name = "Submit")
    private WebElement saveButton;
    @FindBy(xpath = "//a[contains(text(),'Dashboard')]")
    private WebElement dashboardButton;


    public FreeStyleProjectConfigALEXPage(WebDriver driver) {
        super(driver);
    }

    public FreeStyleProjectConfigALEXPage sendTextToDescriptionField(String text) {
        descriptionField.sendKeys(text);
        return this;
    }

    public FreeStyleProjectConfigALEXPage clickGithubCheckbox() {
        githubCheckbox.click();
        return this;
    }

    public FreeStyleProjectConfigALEXPage clickGitInfoQuestMark() {
        gitInfoQuestionMark.click();
        return this;
    }

    public FreeStyleProjectConfigALEXPage clickTimeTriggerCheckbox() throws InterruptedException {
        scrollToElement(getDriver(), timeTriggerCheckbox);
        Thread.sleep(1000);
        timeTriggerCheckbox.click();

        return this;
    }

    public FreeStyleProjectConfigALEXPage sendTextToTimeTriggerField(String text) {
        timeTriggerField.sendKeys(text);

        return this;
    }

    public FreeStyleProjectConfigALEXPage clickAntCheckbox() {
        antCheckbox.click();

        return this;
    }

    public FreeStyleProjectConfigALEXPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public HomePage clickDashboardButton() {
        dashboardButton.click();

        return new HomePage(getDriver());
    }

}
