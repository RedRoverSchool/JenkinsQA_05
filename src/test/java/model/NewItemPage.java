package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;
import java.util.List;

public class NewItemPage extends BasePage {

    @FindBy(className = "item")
    private WebElement rootMenuDashboardLink;

    @FindBy(id = "name")
    private WebElement itemName;

    @FindBy(xpath = "//div[@class='icon']")
    private List<WebElement> itemsList;

    @FindBy(id = "ok-button")
    private WebElement okButton;

    @FindBy(className = "hudson_model_FreeStyleProject")
    private WebElement freestyleProject;

    @FindBy(className = "com_cloudbees_hudson_plugins_folder_Folder")
    private WebElement folderType;

    @FindBy(xpath = "//li[@class = 'jenkins_branch_OrganizationFolder']")
    private WebElement orgFolder;

    @FindBy(xpath = "//span[contains(text(), 'Multi-configuration project')]")
    private WebElement multiConfigurationProject;

    @FindBy(xpath = "//span[contains(text(), 'Multibranch Pipeline')]")
    private WebElement multibranchPipeline;

    @FindBy(id = "itemname-required")
    private WebElement nameRequiredMessage;

    @FindBy(xpath = "//span[text() = 'Pipeline']")
    private WebElement pipeline;


    public NewItemPage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage setProjectName(String name) {
        getWait(2).until(ExpectedConditions.visibilityOf(itemName)).sendKeys(name);

        return this;
    }

    public FreestyleProjectConfigPage selectFreestyleProjectAndClickOk() {
        freestyleProject.click();
        okButton.click();

        return new FreestyleProjectConfigPage(getDriver());
    }

    public FolderConfigPage selectFolderAndClickOk() {
        folderType.click();
        okButton.click();

        return new FolderConfigPage(getDriver());
    }

    public OrgFolderConfigPage selectOrgFolderAndClickOk() {
        TestUtils.scrollToElement(getDriver(), orgFolder);
        orgFolder.click();
        okButton.click();

        return new OrgFolderConfigPage(getDriver());
    }

    public MulticonfigurationProjectConfigPage selectMultiConfigurationProjectAndClickOk() {
        multiConfigurationProject.click();
        okButton.click();

        return new MulticonfigurationProjectConfigPage(getDriver());
    }

    public HomePage rootMenuDashboardLinkClick() {
        rootMenuDashboardLink.click();

        return new HomePage(getDriver());
    }

    public CreateItemErrorPage setItemAndClickOk(int index) {

        getAction().scrollByAmount(0, 250).perform();
        itemsList.get(index).click();
        okButton.click();

        return new CreateItemErrorPage(getDriver());
    }

    public int getItemsListSize() {
        getWait(5).until(ExpectedConditions.visibilityOfAllElements(itemsList));
        return itemsList.size();
    }

    public NewItemPage selectMultibranchPipeline() {
        getWait(1).until(ExpectedConditions.visibilityOf(multibranchPipeline));
        multibranchPipeline.click();

        return this;
    }

    public String getNameRequiredMessageText() {
        return nameRequiredMessage.getText();
    }

    public WebElement getOkButton() {
        return okButton;
    }

    public PipelineConfigPage selectPipelineAndClickOk() {
        pipeline.click();
        okButton.click();

        return new PipelineConfigPage(getDriver());
    }
}
