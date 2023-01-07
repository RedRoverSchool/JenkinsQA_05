package model;

import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ExternalJenkinsPage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement textHeaderJenkins;

    public ExternalJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public String getHeaderText() {
        return textHeaderJenkins.getText();
    }


}
