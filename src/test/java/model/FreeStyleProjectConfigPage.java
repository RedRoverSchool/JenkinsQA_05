package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static runner.TestUtils.scrollToElement;

public class FreeStyleProjectConfigPage extends BasePage {

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


    public FreeStyleProjectConfigPage(WebDriver driver) {
        super(driver);
    }

    public FreeStyleProjectConfigPage sendTextToDescriptionField(String text) {
        descriptionField.sendKeys(text);
        return this;
    }

    public FreeStyleProjectConfigPage clickGithubCheckbox() {
        githubCheckbox.click();
        return this;
    }

    public FreeStyleProjectConfigPage clickGitInfoQuestMark() {
        gitInfoQuestionMark.click();
        return this;
    }

    public FreeStyleProjectConfigPage clickTimeTriggerCheckbox() throws InterruptedException {
        scrollToElement(getDriver(), timeTriggerCheckbox);
        Thread.sleep(1000);
        timeTriggerCheckbox.click();

        return this;
    }

    public FreeStyleProjectConfigPage sendTextToTimeTriggerField(String text) {
        timeTriggerField.sendKeys(text);

        return this;
    }

    public FreeStyleProjectConfigPage clickAntCheckbox() {
        antCheckbox.click();

        return this;
    }

    public FreeStyleProjectConfigPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public HomePage clickDashboardButton() {
        dashboardButton.click();

        return new HomePage(getDriver());
    }

}
