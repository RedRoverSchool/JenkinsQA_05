package model.base;

import model.HomePage;
import model.SearchResultPage;
import model.StatusUserPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

public abstract class Header extends BasePage {

    public Header(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "jenkins-head-icon")
    private WebElement jenkinsHeadIcon;

    @FindBy(id="jenkins-name-icon")
    private WebElement jenkinsNameIcon;

    @FindBy(xpath = "//div/a[@class='model-link']")
    private WebElement iconUserName;

    @FindBy(id = "search-box")
    private WebElement searchField;

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

    public StatusUserPage clickUserIcon() {
        iconUserName.click();

        return new StatusUserPage(getDriver());
    }

    public String getUserNameText() {

        return iconUserName.getText();
    }

    public SearchResultPage setSearchFieldAndClickEnter(String request) {
        searchField.sendKeys(request);
        getWait(3).until(TestUtils.ExpectedConditions.elementIsNotMoving(searchField)).sendKeys(Keys.ENTER);

        return new SearchResultPage(getDriver());
    }
}
