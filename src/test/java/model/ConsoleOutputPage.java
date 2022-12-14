package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConsoleOutputPage extends BasePage {

    @FindBy(className = "console-output")
    private WebElement consoleOutput;

    public ConsoleOutputPage(WebDriver driver) {
        super(driver);
    }

    public String getConsoleOutputText() {
        return consoleOutput.getText();
    }
}
