package model.pipeline;

import model.BuildWithParametersPage;
import model.HomePage;
import model.base.BasePage;
import model.base.Breadcrumbs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class PipelineStatusPage extends Breadcrumbs {

    @FindBy(xpath = "//div[@id='description']//a")
    private WebElement editDescriptionButton;

    @FindBy(xpath = "//div[@id='description']//textarea")
    private WebElement descriptionArea;

    @FindBy(xpath = "//div[@align='right']/span")
    private WebElement saveButton;

    @FindBy(css = "#description >*:first-child")
    private WebElement description;

    @FindBy(xpath = "//span[text()='Delete Pipeline']")
    private WebElement deletePipelineButton;

    @FindBy(xpath = "(//a[contains(@class,'task-link')])[7]")
    private WebElement gitHubSideMenu;

    @FindBy(xpath = "//a[contains(text(), 'Dashboard')]")
    private WebElement dashboardLink;

    @FindBy(css = ".job-index-headline")
    private WebElement pipelineName;

    @FindBy(id = "yui-gen1-button")
    private WebElement disableProjectButton;

    @FindBy(id = "yui-gen1")
    private WebElement enableProjectButton;

    @FindBy(xpath = "//span[@class='task-link-wrapper ']//span[2]")
    private List<WebElement> pipelineSideMenuLinks;

    @FindBy(className = "duration")
    private WebElement stageView;

    @FindBy(linkText = "Build with Parameters")
    private WebElement buildWithParameters;

    @FindBy(linkText = "Configure")
    private WebElement configureLink;

    @FindBy(id = "enable-project")
    private WebElement messageDisabledProject;

    @FindBy(id = "description-link")
    private WebElement editDescriptionLink;

    public PipelineStatusPage(WebDriver driver) {
        super(driver);
    }

    public PipelineStatusPage editDescription(String text) {
        editDescriptionButton.click();
        getWait(5).until(ExpectedConditions.visibilityOf(descriptionArea)).clear();
        descriptionArea.sendKeys(text);

        return this;
    }

    public PipelineStatusPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public String getDescription() {

        return description.getAttribute("textContent");
    }

    public HomePage clickDeletePipelineButton() {
        deletePipelineButton.click();
        getDriver().switchTo().alert().accept();

        return new HomePage(getDriver());
    }

    public HomePage clickDashboard() {
        dashboardLink.click();

        return new HomePage(getDriver());
    }

    public boolean isDisplayedGitHubOnSideMenu() {
        return gitHubSideMenu.isDisplayed();
    }

    public String getAttributeGitHubSideMenu(String attribute) {
        return gitHubSideMenu.getAttribute(attribute);
    }

    public String getPipelineName() {
        return pipelineName.getAttribute("textContent").substring(pipelineName.getAttribute("textContent").indexOf(" ") + 1);
    }

    public PipelineStatusPage clickDisableProject() {
        disableProjectButton.click();

        return new PipelineStatusPage(getDriver());
    }

    public PipelineStatusPage clickEnableProject() {
        enableProjectButton.click();

        return new PipelineStatusPage(getDriver());
    }

    public List<String> getPipelineSideMenuLinks() {
        List<String> pipelineProjectText = new ArrayList<>();
        for (WebElement list : pipelineSideMenuLinks) {
            pipelineProjectText.add(list.getText());
        }

        return pipelineProjectText;
    }

    public PipelineStatusPage clickBuildNow(String name) {
        getDriver().findElement(By.xpath(String.format("//a[@href='/job/%s/build?delay=0sec']", name))).click();
        getWait(20).until(ExpectedConditions.visibilityOf(stageView));

        return this;
    }

    public BuildWithParametersPage clickBuildWithParameters() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(buildWithParameters)).click();

        return new BuildWithParametersPage(getDriver());
    }

    public PipelineConfigPage clickConfigure() {
        configureLink.click();

        return new PipelineConfigPage(getDriver());
    }

    public String getMessageDisabledProject() {
        return messageDisabledProject.getText().split("\n")[0];
    }

    public String getPipelineTitle() {
        return pipelineName.getText();
    }

    public PipelineStatusPage clickEditDescriptionLink() {
        editDescriptionLink.click();

        return new PipelineStatusPage(getDriver());
    }
}