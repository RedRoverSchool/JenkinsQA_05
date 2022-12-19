package model;

import model.base.Footer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RestApiPage extends Footer {
    @FindBy(xpath = "//dt/a[@href='xml']")
    private WebElement xmlApiLink;
    @FindBy(xpath = "//*[@id='main-panel']/h1")
    private WebElement h1RestApi;
    public RestApiPage(WebDriver driver) {
        super(driver);
    }
    public XmlPage clickXmlApi() {
        xmlApiLink.click();

        return new XmlPage(getDriver());
    }

    public String getTextH1RestApi() {
        return h1RestApi.getText();
    }
}