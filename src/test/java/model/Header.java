package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class Header extends BasePage {

    public Header(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "jenkins-head-icon")
    private WebElement jenkinsHeadIcon;

    @FindBy(id="jenkins-name-icon")
    private WebElement jenkinsNameIcon;

    public HomePage clickJenkinsHeadIcon() {
        getWait(10).until(ExpectedConditions.elementToBeClickable(jenkinsHeadIcon)).click();

        return new HomePage(getDriver());
    }

    public HomePage clickJenkinsNameIcon() {
        jenkinsNameIcon.click();

        return new HomePage(getDriver());
    }

    public WebElement getJenkinsHeadIcon(){

        return jenkinsHeadIcon;
    }

    public WebElement getJenkinsNameIcon(){

        return jenkinsHeadIcon;
    }
}
