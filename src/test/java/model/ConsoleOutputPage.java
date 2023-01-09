package model;

import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConsoleOutputPage extends BasePage {

    @FindBy(className = "console-output")
    private WebElement consoleOutput;
    @FindBy(xpath = "//pre/a[@href='/user/admin']")
    private WebElement consoleOutputUserName;

    public ConsoleOutputPage(WebDriver driver) {
        super(driver);
    }

    public String getConsoleOutputText() {
        return consoleOutput.getText();
    }
    public String getTextConsoleOutputUserName() {
        return consoleOutputUserName.getText();
    }
}
