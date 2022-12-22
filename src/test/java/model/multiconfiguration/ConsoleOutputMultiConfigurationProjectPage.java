package model.multiconfiguration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConsoleOutputMultiConfigurationProjectPage extends MultiConfigurationProjectStatusPage {

    @FindBy(xpath = "//pre/a[@href='/user/admin']")
    private WebElement consoleOutputUserName;

    @FindBy(xpath = "//pre")
    private WebElement textConsoleOutput;

    @FindBy(xpath = "//div/a[@class='model-link']")
    private WebElement iconUserName;

    public ConsoleOutputMultiConfigurationProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getTextConsoleOutputUserName() {
        return consoleOutputUserName.getText();
    }

    public String getTextConsoleOutput() {
        return textConsoleOutput.getText();
    }

    public String getUserNameText() {

        return iconUserName.getText();
    }
}
