package model;

import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.TestUtils;

public class GlobalToolConfigurationPage extends BasePage {

    @FindBy(id = "yui-gen9-button")
    private WebElement addMavenButton;

    @FindBy(css = "input[checkurl$='MavenInstallation/checkName']")
    private WebElement mavenTitleField;

    @FindBy(id = "yui-gen5-button")
    private WebElement applyButton;

    @FindBy(xpath = "//input[contains(@checkurl,'MavenInstallation/checkName')]/parent::div/following-sibling::div")
    private WebElement errorArea;

    public GlobalToolConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public GlobalToolConfigurationPage clickAddMavenButton() {
        TestUtils.scrollToEnd(getDriver());
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(addMavenButton)).click();
        TestUtils.scrollToEnd(getDriver());
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(addMavenButton));

        return this;
    }

    public GlobalToolConfigurationPage setMavenTitleField(String name) {
        mavenTitleField.click();
        mavenTitleField.sendKeys(name);

        return this;
    }

    public GlobalToolConfigurationPage clickApplyButton() {
       applyButton.click();

        return this;
    }

    public String getErrorAreaText() {
        return errorArea.getText();
    }
}
