package model;

import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BuildStatusPage extends BasePage {

    @FindBy(linkText = "Console Output")
    private WebElement consoleOutput;

    public BuildStatusPage(WebDriver driver) {
        super(driver);
    }

    public ConsoleOutputPage clickConsoleOutput() {
        consoleOutput.click();

        return new ConsoleOutputPage(getDriver());
    }
}
