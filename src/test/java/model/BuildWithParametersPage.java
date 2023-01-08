package model;

import model.base.BasePage;
import model.base.BaseStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class BuildWithParametersPage<StatusPage extends BaseStatusPage<?, ?>> extends BasePage {

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(xpath = "//div[@id='main-panel']/p")
    private WebElement descriptionText;

    @FindBy(xpath = "//div[@name='parameter']/input[@name='name']")
    private List<WebElement> listInputParameterNames;

    @FindBy(xpath = "//div[@name='parameter']/input[@name='value']")
    private List<WebElement> listInputParameterValues;

    @FindBy(xpath = "//select[@name='value']")
    private WebElement selectedParameter;

    @FindBy(xpath = "//input[@type='checkbox']")
    private WebElement defaultValueCheckbox;

    @FindBy(id = "yui-gen1-button")
    private WebElement buildButton;

    private final StatusPage statusPage;


    public BuildWithParametersPage(WebDriver driver, StatusPage statusPage) {
        super(driver);
        this.statusPage = statusPage;
    }

    public String getNameText() {
        return getWait(5).until(ExpectedConditions.elementToBeClickable(header)).getText();
    }

    public String getDescriptionText() {
        return descriptionText.getText();
    }

    public String getNthParameterName(int n) {
        return listInputParameterNames.get(n - 1).getAttribute("value");
    }

    public String getNthParameterValue(int n) {
        return listInputParameterValues.get(n - 1).getAttribute("value");
    }

    public String getSelectedParameterValue() {
        return selectedParameter.getText().substring(0, selectedParameter.getText().indexOf("\n"));
    }

    public boolean isBooleanParameterSetByDefault() {
        return defaultValueCheckbox.isSelected();
    }

    public BuildWithParametersPage<StatusPage> getSelectParameterByText(String text) {
        new Select(selectedParameter).selectByVisibleText(text);

        return this;
    }

    public StatusPage clickBuildButton() {
        buildButton.click();

        return statusPage;
    }

}
