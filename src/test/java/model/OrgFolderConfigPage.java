package model;

import model.base.BaseConfigPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrgFolderConfigPage extends BaseConfigPage {

    @FindBy(xpath = "//input  [@name='_.displayNameOrNull']")
    private WebElement displayName;

    @FindBy(xpath = "//textarea [@name='_.description']")
    private WebElement description;

    public OrgFolderConfigPage(WebDriver driver) {
        super(driver);
    }

    public OrgFolderConfigPage inputDisplayName(String name) {
        displayName.sendKeys(name);

        return this;
    }

    public OrgFolderConfigPage inputDescription(String name) {
        description.sendKeys(name);

        return this;
    }
}
