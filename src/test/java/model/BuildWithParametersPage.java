package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BuildWithParametersPage extends FreestyleProjectStatusPage{
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

    public BuildWithParametersPage clickButtonBuildWithParameters(){
        buttonBuildWithParameters.click();

        return this;
    }

    public String getPageNotificationText(){

        return pageNotification.getText();
    }

    public String getFirstParameterName(){

        return  firstParameter.getText();
    }

    public String getFirstParamName(){

        return listInputtingValues.get(0).getAttribute("value");
    }

    public String getFirstParamValue(){

        return listInputtingValues.get(1).getAttribute("value");
    }

    public String getSecondParamName(){

        return listInputtingValues.get(2).getAttribute("value");
    }

    public String getThirdParamName(){

        return listInputtingValues.get(3).getAttribute("value");
    }

    public String selectedParametersValues(){

        return  selectedParameters.getText();
    }

    public boolean isBooleanParameterDefaultOn(){

        return checkBoxDefaultValue.isSelected();
    }
}
