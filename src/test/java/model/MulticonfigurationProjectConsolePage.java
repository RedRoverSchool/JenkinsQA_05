package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MulticonfigurationProjectConsolePage extends BasePage{

    @FindBy(xpath = "//pre/a[@href='/user/admin']")
    private WebElement consoleOutputUserName;

    @FindBy(xpath = "//pre")
    private WebElement textConsoleOutput;

    public MulticonfigurationProjectConsolePage(WebDriver driver) {
        super(driver);
    }

    public String getTextConsoleOutputUserName() {
        return consoleOutputUserName.getText();
    }

    public String getTextConsoleOutput() {
        return textConsoleOutput.getText();
    }
}
