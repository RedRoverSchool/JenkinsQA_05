package model;

import model.base.BlankFooterFrame;
import model.base.FooterComponent;
import model.base.MainBasePage;
import model.base.MainFooterFrame;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RestApiPage extends MainBasePage<BlankFooterFrame> {
    @FindBy(xpath = "//dt/a[@href='xml']")
    private WebElement xmlApiLink;
    @FindBy(xpath = "//*[@id='main-panel']/h1")
    private WebElement h1RestApi;
//    @FindBy(xpath = "//div/a[@href = 'api/']")
//    private WebElement restApi;

    @Override
    protected BlankFooterFrame createFooterFrame() {
        return new BlankFooterFrame(getDriver());
    }

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

//    public RestApiPage clickRestApiLink() {
//        restApi.click();
//        return new RestApiPage(getDriver());
//    }
}