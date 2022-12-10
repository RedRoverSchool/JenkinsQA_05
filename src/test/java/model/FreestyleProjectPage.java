package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FreestyleProjectPage extends BasePage {

    @FindBy(tagName = "h1")
    private WebElement headline;

    @FindBy(linkText = "Configure")
    private WebElement sideMenuConfigure;

    @FindBy(css = "#breadcrumbs li a")
    private WebElement topMenuRoot;

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
}
