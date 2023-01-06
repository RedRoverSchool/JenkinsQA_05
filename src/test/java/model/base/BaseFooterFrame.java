package model.base;

import model.ExternalJenkinsPage;
import model.RestApiPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.BaseModel;

import java.util.ArrayList;

public abstract class BaseFooterFrame extends BaseModel {

    @FindBy(xpath = "//a[@href='https://www.jenkins.io/']")
    private WebElement jenkinsLink;

    @FindBy(id = "footer")
    private WebElement footer;

    public BaseFooterFrame(WebDriver driver) {
        super(driver);
    }

    public ExternalJenkinsPage clickJenkinsVersion() {
        jenkinsLink.click();
        ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(1));
        return new ExternalJenkinsPage(getDriver());
    }

    public boolean isDisplayedFooter() {
        return footer.isDisplayed();
    }

    public WebElement getJenkinsLink() {
        return jenkinsLink;
    }
}
