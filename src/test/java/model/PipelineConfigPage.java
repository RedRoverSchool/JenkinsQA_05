package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PipelineConfigPage extends BasePage {

    @FindBy(xpath = "//label[text()='GitHub project']")
    private WebElement gitHubCheckbox;

    @FindBy(name = "_.projectUrlStr")
    private WebElement gitHubUrl;

    @FindBy(id = "yui-gen6-button")
    private WebElement saveButton;

    @FindBy(css = "#breadcrumbs li a")
    private WebElement topMenuRoot;

    public PipelineConfigPage(WebDriver driver) {
        super(driver);
    }

    public PipelineConfigPage clickGitHubCheckbox() {
        gitHubCheckbox.click();

        return this;
    }

    public PipelineConfigPage setGitHubRepo(String gitHubRepo) {
        getAction().moveToElement(gitHubUrl).click().sendKeys(gitHubRepo).perform();

        return this;
    }

    public PipelineConfigPage saveConfigAndGoToProject() {
        saveButton.click();

        return this;
    }

    public PipelineProjectPage saveConfigAndGoToProjectPage() {
        saveButton.click();

        return new PipelineProjectPage(getDriver());
    }

    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }

    public PipelineProjectPage clickSaveButton() {
        saveButton.click();

        return new PipelineProjectPage(getDriver());
    }
}
