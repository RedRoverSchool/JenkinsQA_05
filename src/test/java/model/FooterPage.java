package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FooterPage extends HomePage {
    @FindBy(xpath = "//dt/a[@href='xml']")
    private WebElement xmlApiLink;
    public FooterPage(WebDriver driver) {
        super(driver);
    }

    public XmlPage clickXmlApi() {
        xmlApiLink.click();

        return new XmlPage(getDriver());
    }


}
