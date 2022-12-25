package model;

import java.util.List;
import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

public class GlobalToolConfigurationPage extends BasePage {

    @FindBy(xpath = "//button[text()='Add Maven']")
    private List<WebElement> addMavenButtons;

    @FindBy(css = "input[checkurl$='MavenInstallation/checkName']")
    private List<WebElement> mavenTitleFields;

    @FindBy(xpath = "//button[text()='Apply']")
    private WebElement applyButton;

    @FindBy(xpath = "//input[contains(@checkurl,'MavenInstallation/checkName')]/parent::div/following-sibling::div")
    private WebElement errorArea;

    public GlobalToolConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public GlobalToolConfigurationPage clickFirstAddMavenButton() {
        TestUtils.scrollToEnd(getDriver());
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(addMavenButtons.get(0)));
        getWait(5).until(ExpectedConditions.elementToBeClickable(addMavenButtons.get(0))).click();

        return this;
    }

    public GlobalToolConfigurationPage setFirstMavenTitleField(String name) {
        TestUtils.scrollToEnd(getDriver());
        getAction().scrollToElement(mavenTitleFields.get(0)).moveToElement(mavenTitleFields.get(0)).perform();
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(mavenTitleFields.get(0)));
        getWait(5).until(ExpectedConditions.elementToBeClickable(mavenTitleFields.get(0))).click();
        mavenTitleFields.get(0).sendKeys(name);

        return this;
    }

    public GlobalToolConfigurationPage clickApplyButton() {
        TestUtils.scrollToEnd(getDriver());
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(applyButton));
        getWait(5).until(ExpectedConditions.elementToBeClickable(applyButton)).click();

        return this;
    }

    public String getErrorAreaText() {
        return getWait(5).until(ExpectedConditions.visibilityOf(errorArea)).getText();
    }
}
