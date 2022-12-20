package model;

import model.freestyle.FreestyleProjectConfigPage;
import model.freestyle.FreestyleProjectStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class BuildWithParametersPage extends FreestyleProjectStatusPage {
    public BuildWithParametersPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(linkText = "Build with Parameters")
    private WebElement buttonBuildWithParameters;

    @FindBy(xpath = "//div[@id = 'main-panel']/p")
    private WebElement pageNotification;

    @FindBy(xpath = "//div[@class= 'parameters']/div[1]")
    private WebElement firstParameter;

    @FindBy(xpath = "//div[@name= 'parameter']/input")
    private List<WebElement> listInputtingValues;

    @FindBy(xpath = "//select[@name= 'value']")
    private WebElement selectedParameters;

    @FindBy(xpath = "//input[@type= 'checkbox']")
    private WebElement checkBoxDefaultValue;

    @FindBy(id = "yui-gen1-button")
    private WebElement buildButton;

    @FindBy(xpath = "//tr[@class='job SUCCESS']")
    private WebElement iconSuccessfulBuild;

    @FindBy(xpath = "//a[@href='lastBuild/']")
    private WebElement lastBuildLink;

    @FindBy(linkText = "Configure")
    private WebElement sideMenuConfigure;

    public BuildWithParametersPage clickButtonBuildWithParameters() {
        buttonBuildWithParameters.click();

        return this;
    }

    public String getPageNotificationText() {

        return pageNotification.getText();
    }

    public String getFirstParameterName() {

        return firstParameter.getText();
    }

    public String getFirstParamName() {

        return listInputtingValues.get(0).getAttribute("value");
    }

    public String getFirstParamValue() {

        return listInputtingValues.get(1).getAttribute("value");
    }

    public String getSecondParamName() {

        return listInputtingValues.get(2).getAttribute("value");
    }

    public String getThirdParamName() {

        return listInputtingValues.get(3).getAttribute("value");
    }

    public String selectedParametersValues() {

        return selectedParameters.getText();
    }

    public boolean isBooleanParameterDefaultOn() {

        return checkBoxDefaultValue.isSelected();
    }

    public BuildWithParametersPage selectParametersBuild() {
        new Select(selectedParameters).selectByVisibleText("Guest");

        return this;
    }

    public BuildWithParametersPage clickBuildButton() {
        buildButton.click();
        getWait(60).until(ExpectedConditions.visibilityOf(iconSuccessfulBuild));
        getDriver().navigate().refresh();

        return this;
    }

    public BuildStatusPage clickLastBuildLink() {
        lastBuildLink.click();

        return new BuildStatusPage(getDriver());
    }

    public FreestyleProjectConfigPage clickConfigureLink() {
        sideMenuConfigure.click();

        return new FreestyleProjectConfigPage(getDriver());
    }
}
