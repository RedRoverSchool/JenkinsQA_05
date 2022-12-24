package model.base;

import model.ExternalJenkinsPage;
import model.RestApiPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;

public abstract class Footer extends BasePage {
    @FindBy(xpath = "//div/a[@href = 'api/']")
    private WebElement restApi;

    @FindBy(xpath = "//a[@href='https://www.jenkins.io/']")
    private WebElement jenkinsLink;

    @FindBy(id = "footer")
    private WebElement footer;


    public Footer(WebDriver driver) {
        super(driver);
    }

    public RestApiPage clickRestApiLink() {
        restApi.click();
        return new RestApiPage(getDriver());
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
