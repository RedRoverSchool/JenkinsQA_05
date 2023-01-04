package model;

import model.base.BasePage;
import model.base.FooterComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ExternalJenkinsPage extends BasePage {
    @FindBy(xpath = "//a[@class='navbar-brand']")
    private WebElement textJenkins;

    @FindBy(xpath = "//h1")
    private WebElement textHeaderJenkins;

    public ExternalJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public String getTextJenkins() {
        return textJenkins.getText();
    }

    public String getHeaderText() {
        return textHeaderJenkins.getText();
    }

}
