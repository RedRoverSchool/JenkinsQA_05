package model;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.TestUtils;

public class EditViewPage extends BasePage {

    @FindBy(css = "#yui-gen6-button")
    private WebElement okButton;

    public EditViewPage(WebDriver driver) {
        super(driver);
    }

    public EditViewPage addJobToView(String name) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView({block: 'center'})",
                getDriver().findElement(By.xpath("//label[@title='" + name + "']")));
        getWait(10).until(TestUtils.ExpectedConditions.elementIsNotMoving(
                By.xpath("//label[@title='" + name + "']"))).click();

        return this;
    }

    public ViewPage clickOk() {
        okButton.click();

        return new ViewPage(getDriver());
    }
}
