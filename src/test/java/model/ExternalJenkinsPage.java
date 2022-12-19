package model;

import model.base.Footer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ExternalJenkinsPage extends Footer {
    @FindBy(xpath = "//a[@class='navbar-brand']")
    private WebElement textJenkins;

    public ExternalJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public String getTextJenkins() {
        return textJenkins.getText();
    }


}
