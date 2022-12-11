package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class XmlPage extends BasePage {
    @FindBy(css = "body > div.header>span")
    private WebElement textOnPageXML;
    public XmlPage(WebDriver driver) {
        super(driver);
    }

    public String getStructureXML() {

        return textOnPageXML.getText();
    }

}
