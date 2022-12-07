package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrgFolderConfigPage extends BasePage{

    @FindBy(id = "yui-gen15-button")
    private WebElement saveButton;

    public OrgFolderConfigPage(WebDriver driver) {
        super(driver);
    }
    public void clickSaveButton(){
        saveButton.click();
    }


}
