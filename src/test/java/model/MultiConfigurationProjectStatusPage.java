package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MultiConfigurationProjectStatusPage extends BasePage{

    @FindBy(css = "#breadcrumbs li a")
    private WebElement dashboard;

    @FindBy(id = "description-link")
    private WebElement descriptionLink;

    @FindBy(name = "description")
    private WebElement description;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElement saveDescriptionButton;

    @FindBy(xpath = "//div[@id='description']/div[1]")
    private WebElement fieldDescription;

    @FindBy(linkText = "Build Now")
    private WebElement buildNowButton;

    private final By SUCCESS_BUILD_ICON = By.xpath("//span[@class='build-status-icon__outer']"
            + "/*[name()='svg'][@tooltip='Success &gt; Console Output']");

    @FindBy(css = ".model-link.inside.build-link.display-name")
    private WebElement dropDownBuildIcon;

    @FindBy(xpath = "//li[@id='yui-gen3']/a/*[name()='svg']")
    private WebElement consoleOutputDropDownBuildIcon;

    public MultiConfigurationProjectStatusPage(WebDriver driver) {
        super(driver);
    }

    public HomePage goToDashboard() {
        dashboard.click();

        return new HomePage(getDriver());
    }

    public MultiConfigurationProjectStatusPage clickAddDescription(){
        descriptionLink.click();

        return this;
    }

    public MultiConfigurationProjectStatusPage fillDescription(String desc){
        getWait(5).until(ExpectedConditions.visibilityOf(description));
        description.sendKeys(desc);

        return  this;
    }

    public MultiConfigurationProjectStatusPage clickSave(){
        saveDescriptionButton.click();

        return this;
    }

    public String getDescriptionText(){

        return fieldDescription.getText();
    }

    public MulticonfigurationProjectConfigPage clickCongiguration(String projectName) {
        getDriver().findElement(By.xpath(String.format("//a[@href='/job/%s/configure']", projectName))).click();

        return new MulticonfigurationProjectConfigPage(getDriver());
    }

    public MultiConfigurationProjectStatusPage clickBuildNowButton() {
        buildNowButton.click();

        return this;
    }

    public MultiConfigurationProjectStatusPage clickDropDownBuildIcon() {
        getWait(10).until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_BUILD_ICON));
        dropDownBuildIcon.click();

        return this;
    }

    public MulticonfigurationProjectConsolePage selectAndClickConsoleOutput() {
        consoleOutputDropDownBuildIcon.click();

        return new MulticonfigurationProjectConsolePage(getDriver());
    }


}
