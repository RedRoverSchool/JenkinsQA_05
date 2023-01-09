package model.base;

import model.ExternalJenkinsPage;
import model.RestApiPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.BaseModel;

import java.util.ArrayList;

public class FooterComponent extends BaseModel {
    @FindBy(xpath = "//a[@href='https://www.jenkins.io/']")
    private WebElement jenkinsFooterLink;

    @FindBy(id = "footer")
    private WebElement footer;

    @FindBy(xpath = "//div/a[@href = 'api/']")
    private WebElement restApi;

    public FooterComponent(WebDriver driver) {
        super(driver);
    }

    public ExternalJenkinsPage clickJenkinsVersion() {
        jenkinsFooterLink.click();
        ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(1));
        return new ExternalJenkinsPage(getDriver());
    }

    public boolean isDisplayedFooter() {
        return footer.isDisplayed();
    }

    public WebElement getJenkinsFooterLink() {
        return jenkinsFooterLink;
    }

    public RestApiPage clickRestApiLink() {
        restApi.click();
        return new RestApiPage(getDriver());
    }
}
