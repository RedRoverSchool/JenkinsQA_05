package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FreestyleProjectPage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement headline;

    @FindBy(linkText = "Configure")
    private WebElement sideMenuConfigure;

    @FindBy(css = "#breadcrumbs li a")
    private WebElement topMenuRoot;

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

    public FreestyleProjectPage(WebDriver driver) {
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

    public RenameItemPage clickRenameButton(){
        buttonRename.click();

        return new RenameItemPage(getDriver());
    }

    public FreestyleProjectPage clickButtonAddDescription(){
        getWait(10).until(ExpectedConditions.elementToBeClickable(buttonAddDescription)).click();

        return this;
    }

    public FreestyleProjectPage inputAndSaveDescriptionText(String description){
        getWait(10).until(ExpectedConditions.elementToBeClickable(fieldDescriptionText)).sendKeys(description);
        getWait(10).until(ExpectedConditions.elementToBeClickable(buttonSave)).click();

        return this;
    }

    public String getDescriptionText(){

        return description.getText();
    }

    public String getProjectName() {
        return headline.getText().substring(8);
    }
}
