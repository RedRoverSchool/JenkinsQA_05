package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewItemPage extends HomePage {

    @FindBy(className = "jenkins-input")
    private WebElement itemName;
    @FindBy(className = "com_cloudbees_hudson_plugins_folder_Folder")
    private WebElement folderType;
    @FindBy(className = "btn-decorator")
    private WebElement okButton;
    @FindBy(className = "hudson_model_FreeStyleProject")
    private WebElement freeStyleProjectType;

    public NewItemPage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage typeName(String name) {
        itemName.sendKeys(name);

        return this;
    }

    public FolderConfigPage selectFolderAndClickOk() {
        folderType.click();
        okButton.click();

        return new FolderConfigPage(getDriver());
    }

    public FreeStyleProjectConfigPage selectFreeStyleProjectAndClickOk() {
        freeStyleProjectType.click();
        okButton.click();

        return new FreeStyleProjectConfigPage(getDriver());
    }
}
