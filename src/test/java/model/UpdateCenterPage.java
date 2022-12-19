package model;

import model.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

import static runner.TestUtils.scrollToElement;

public class UpdateCenterPage extends BasePage {
    public UpdateCenterPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[text() = 'Go back to the top page']")
    private WebElement buttonGoBackToTopPage;

    public String getPluginUpgradeStatus(String pluginName){

        return getWait(5).until(ExpectedConditions.visibilityOf(
                getDriver().findElement(By.xpath("//td[text() = '" + pluginName + "']/following-sibling::td"))))
                .getText();

    }

    public HomePage clickButtonGoBackToTopPage(){
        scrollToElement(getDriver(), buttonGoBackToTopPage);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(buttonGoBackToTopPage));
        getWait(5).until(ExpectedConditions.elementToBeClickable(buttonGoBackToTopPage)).click();

        return new HomePage(getDriver());
    }
}
