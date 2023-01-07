package model.freestyle;

import model.base.BaseStatusPage;
import model.ChangesBuildsPage;
import model.HomePage;
import model.RenameItemPage;
import model.folder.FolderStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class FreestyleProjectStatusPage extends BaseStatusPage<FreestyleProjectStatusPage> {

    @FindBy(linkText = "Configure")
    private WebElement sideMenuConfigure;

    @FindBy(xpath = "//li[@class='item'][last()-1]")
    private WebElement breadcrumbsParentFolderLink;

    @FindBy(linkText = "Rename")
    private WebElement renameButton;

    @FindBy(id = "description-link")
    private WebElement buttonAddDescription;

    @FindBy(xpath = "//textarea[@name = 'description']")
    private WebElement fieldDescriptionText;

    @FindBy(id = "yui-gen2")
    private WebElement buttonSave;

    @FindBy(xpath = "//span[contains(text(),'Delete Project')]")
    private WebElement buttonDeleteProject;

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

    @FindBy(id = "enable-project")
    private WebElement warningForm;

    @FindBy(name = "Submit")
    private WebElement disableProjectBtn;

    @FindBy(linkText = "Changes")
    private WebElement linkChanges;

    @FindBy(linkText = "Edit description")
    private WebElement buttonEditDescription;

    public FreestyleProjectStatusPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectConfigPage clickSideMenuConfigure() {
        sideMenuConfigure.click();

        return new FreestyleProjectConfigPage(getDriver());
    }

    public FolderStatusPage clickParentFolderInBreadcrumbs() {
        breadcrumbsParentFolderLink.click();

        return new FolderStatusPage(getDriver());
    }

    public FreestyleProjectStatusPage clickDisableProjectBtn() {
        disableProjectBtn.click();

        return this;
    }

    public RenameItemPage<FreestyleProjectStatusPage> clickRenameSideMenu() {
        renameButton.click();

        return new RenameItemPage<>(getDriver(), new FreestyleProjectStatusPage(getDriver()));
    }

    public FreestyleProjectStatusPage clickButtonAddDescription() {
        getWait(10).until(ExpectedConditions.elementToBeClickable(buttonAddDescription)).click();

        return this;
    }

    public FreestyleProjectStatusPage inputAndSaveDescriptionText(String description) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(fieldDescriptionText)).clear();
        fieldDescriptionText.sendKeys(description);
        getWait(10).until(ExpectedConditions.elementToBeClickable(buttonSave)).click();

        return this;
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
        getWait(5).until(ExpectedConditions.elementToBeClickable(sideMenuConfigure)).click();

        return new FreestyleProjectConfigPage(getDriver());
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
        getWait(60).until(ExpectedConditions.visibilityOf((buildLoadingIconSuccess)));
        getWait(10).until(ExpectedConditions.attributeToBe(buildsInformationOnSidePanel, "style", "display: none;"));

        return this;
    }

    public HomePage clickButtonBuildNowAndRedirectToDashboardAfterBuildCompleted() {
        buttonBuildNow.click();
        getWait(60).until(ExpectedConditions.not(ExpectedConditions
                .attributeContains(buildStatusIcon, "tooltip", "progress")));
        topMenuRoot.click();

        return new HomePage(getDriver());
    }

    public String getWarningMsg() {
        return warningForm.getText().substring(0, warningForm.getText().indexOf("\n"));
    }

    public ChangesBuildsPage clickLinkChanges() {
        linkChanges.click();

        return new ChangesBuildsPage(getDriver());
    }

    public FreestyleProjectStatusPage clickButtonEditDescription() {
        buttonEditDescription.click();

        return this;
    }

    public FolderStatusPage confirmAlertAndDeleteProjectFromFolder() {
        getDriver().switchTo().alert().accept();

        return new FolderStatusPage(getDriver());
    }
}
