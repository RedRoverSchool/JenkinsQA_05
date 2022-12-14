package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConsolePage extends BasePage {
    public ConsolePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(tagName = "h1")
    private WebElement pageHeader;

    @FindBy(xpath = "//span[contains(@class, 'build-status-icon')]/span/child::*")
    private WebElement buildStatusIcon;

    @FindBy(xpath = "//pre[@class = 'console-output']")
    private WebElement consoleOutputText;

    public String getPageHeader(){

        return pageHeader.getText();
    }

    public String getJobBuildStatus(){

        return buildStatusIcon.getAttribute("tooltip");
    }

    public String getConsoleOutputText(){

        return consoleOutputText.getText();
    }
}
