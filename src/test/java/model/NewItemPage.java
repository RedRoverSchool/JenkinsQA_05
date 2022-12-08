package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.TestUtils;

public class NewItemPage extends BasePage {

    @FindBy(id = "name")
    private WebElement itemName;

    @FindBy(className = "btn-decorator")
    private WebElement okButton;

    @FindBy(className = "hudson_model_FreeStyleProject")
    private WebElement freestyleProject;

    @FindBy(className = "com_cloudbees_hudson_plugins_folder_Folder")
    private WebElement folderType;

    @FindBy(xpath = "//li[@class = 'jenkins_branch_OrganizationFolder']")
    private WebElement orgFolder;


    public NewItemPage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage typeName(String name) {
        itemName.sendKeys(name);

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
}
