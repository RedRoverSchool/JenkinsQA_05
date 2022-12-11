package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StatusUserPage extends BasePage {

    @FindBy(css = ".model-link > .hidden-xs.hidden-sm")
    private WebElement pageHeaderUserName;

    @FindBy(xpath = "//li[@class='item'][last()]")
    private WebElement breadcrumbsUserName;

    @FindBy(xpath = "//h1")
    private WebElement h1Title;

    public StatusUserPage(WebDriver driver) {
        super(driver);
    }

    public StatusUserPage refreshPage(){
        getDriver().navigate().refresh();

        return this;
    }

    public String getPageHeaderUserName() {
        return pageHeaderUserName.getText();
    }

    public String getBreadcrumbsUserName() {
        return breadcrumbsUserName.getText();
    }

    public String getH1Title() {
        return h1Title.getText();
    }
}