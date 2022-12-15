package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class FreestyleProjectStatusPage extends BasePage {

    @FindBy(tagName = "h1")
    private WebElement headline;

    @FindBy(linkText = "Configure")
    private WebElement sideMenuConfigure;

    @FindBy(css = "#breadcrumbs li a")
    private WebElement topMenuRoot;

    @FindBy(xpath = "//li[@class='item'][last()-1]")
    private WebElement breadcrumbsParentFolderLink;

    @FindBy(name = "Submit")
    private WebElement disableOrEnableBtn;

    @FindBy(linkText = "Rename")
    private WebElement buttonRename;

    @FindBy(id = "description-link")
    private WebElement buttonAddDescription;

    @FindBy(xpath = "//textarea[@name = 'description']")
    private WebElement fieldDescriptionText;

    @FindBy(id = "yui-gen2")
    private WebElement buttonSave;

    @FindBy(xpath = "//div[@id = 'description'] /div[1]")
    private WebElement description;

    @FindBy(xpath = "//span[contains(text(),'Delete Project')]")
    private WebElement buttonDeleteProject;

    @FindBy(xpath = "//li[@class='item'][2]")
    private WebElement projectButton;

    @FindBy(css = ".collapse")
    private WebElement buttonOpenBuildHistoryOnSidePanel;

    @FindBy(css = "#no-builds")
    private WebElement buildsInformationOnSidePanel;

    @FindBy(css = ".build-row-cell")
    private List<WebElement> buildRowsOnBuildsInformation;

    @FindBy(linkText = "Build Now")
    private WebElement buttonBuildNowOnSidePanel;

    @FindBy(css = ".build-status-icon__outer>[tooltip = 'Success &gt; Console Output']")
    private WebElement buildLoadingIconSuccess;

    @FindBy(linkText = "Build Now")
    private WebElement buttonBuildNow;

    @FindBy(xpath = "//span[contains(@class, 'build-status-icon')]/span/child::*")
    private WebElement buildStatusIcon;

    @FindBy(css = "tr:nth-child(2)  a.display-name")
    private WebElement buildName;

    public FreestyleProjectStatusPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleConfigSideMenuPage clickSideMenuConfigure() {
        sideMenuConfigure.click();

        return new FreestyleConfigSideMenuPage(getDriver());
    }

    public String getHeadlineText() {
        return headline.getText();
    }

    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }

    public FolderStatusPage clickParentFolderInBreadcrumbs() {
        breadcrumbsParentFolderLink.click();

        return new FolderStatusPage(getDriver());
    }

    public FreestyleProjectStatusPage clickDisableOrEnableSwitchBtn() {
        disableOrEnableBtn.click();

        return this;
    }

    public RenameItemPage clickRenameButton() {
        buttonRename.click();

        return new RenameItemPage(getDriver());
    }

    public FreestyleProjectStatusPage clickButtonAddDescription() {
        getWait(10).until(ExpectedConditions.elementToBeClickable(buttonAddDescription)).click();

        return this;
    }

    public FreestyleProjectStatusPage inputAndSaveDescriptionText(String description) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(fieldDescriptionText)).sendKeys(description);
        getWait(10).until(ExpectedConditions.elementToBeClickable(buttonSave)).click();

        return this;
    }

    public String getDescriptionText() {

        return description.getText();
    }

    public FreestyleProjectStatusPage clickButtonDeleteProject() {
        getWait(10).until(ExpectedConditions.elementToBeClickable(buttonDeleteProject)).click();

        return this;
    }

    public HomePage confirmAlertAndDeleteProject() {
        getDriver().switchTo().alert().accept();

        return new HomePage(getDriver());
    }

    public FreestyleProjectConfigPage clickSideMenuConfigureLink() {
        sideMenuConfigure.click();

        return new FreestyleProjectConfigPage(getDriver());
    }

    public String getFreestyleProjectName(String name) {

        return projectButton.getText();
    }

    public String getProjectName() {
        return headline.getText().substring(8);
    }

    public FreestyleProjectStatusPage openBuildHistoryOnSidePanel() {
        if (buttonOpenBuildHistoryOnSidePanel.getAttribute("title").equals("expand")) {
            buttonOpenBuildHistoryOnSidePanel.click();
        }

        return this;
    }

    public int countBuildsOnSidePanel() {
        getWait(10).until(ExpectedConditions.attributeContains(buildsInformationOnSidePanel, "style", "display"));
        int countBuilds = 0;
        if (buildsInformationOnSidePanel.getAttribute("style").equals("display: none;")) {
            countBuilds = buildRowsOnBuildsInformation.size();
        }

        return countBuilds;
    }

    public FreestyleProjectStatusPage clickBuildNowOnSidePanel() {
        buttonBuildNowOnSidePanel.click();
        getWait(20).until(ExpectedConditions.visibilityOf((buildLoadingIconSuccess)));
        getWait(10).until(ExpectedConditions.attributeToBe(buildsInformationOnSidePanel, "style", "display: none;"));

        return this;
    }

    public FreestyleProjectStatusPage clickButtonBuildNowAndWaitBuildComplete() {
        buttonBuildNow.click();
        getWait(60).until(ExpectedConditions.not(ExpectedConditions
                .attributeContains(buildStatusIcon, "tooltip", "In progress &gt; Console Output")));

        return this;
    }
}
