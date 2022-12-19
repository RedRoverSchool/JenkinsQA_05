package model;

import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PluginManagerPage extends BasePage {

    public PluginManagerPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[@href='./available']")
    private WebElement linkAvailable;

    @FindBy(xpath = "//a[@href='./installed']")
    private WebElement linkInstalled;

    @FindBy(id = "filter-box")
    private WebElement searchRow;

    @FindBy(xpath = "//tr[@data-plugin-id='testng-plugin']//label")
    private WebElement checkBoxTestNGResults;

    @FindBy(id = "yui-gen1-button")
    private WebElement buttonInstallWithoutRestart;

    @FindBy(xpath = "//a[contains(text(), 'TestNG Results Plugin')]/parent::div/following-sibling::div")
    private WebElement resultField;

    public PluginManagerPage clickLinkAvailable(){
        linkAvailable.click();

        return this;
    }

    public PluginManagerPage inputValueToSearchRow(String value){
        getWait(5).until(ExpectedConditions.visibilityOf(searchRow)).sendKeys(value);

        return this;
    }

    public PluginManagerPage clickCheckBoxTestNGResults(){
        getWait(5).until(ExpectedConditions.visibilityOf(checkBoxTestNGResults)).click();

        return this;
    }

    public UpdateCenterPage clickButtonInstallWithoutRestart(){
        getWait(5).until(ExpectedConditions.elementToBeClickable(buttonInstallWithoutRestart)).click();

        return new UpdateCenterPage(getDriver());
    }

    public String getResultFieldText(){

        return getWait(5).until(ExpectedConditions.visibilityOf(resultField)).getText();
    }

    public PluginManagerPage clickLinkInstalled(){
        linkInstalled.click();

        return this;
    }
}
