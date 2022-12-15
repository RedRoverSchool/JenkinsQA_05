package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    @FindBy(xpath = "//span[contains(text(), 'Delete build')]/parent::a")
    private WebElement buttonDeleteBuild;

    @FindBy(xpath = "//button[@type = 'submit']")
    private WebElement buttonSubmit;

    public String getPageHeader(){

        return pageHeader.getText();
    }

    public String getJobBuildStatus(){

        return buildStatusIcon.getAttribute("tooltip");
    }

    public String getConsoleOutputText(){

        return consoleOutputText.getText();
    }

    public FreestyleProjectStatusPage clickButtonDeleteBuild(){
        getWait(5).until(ExpectedConditions.visibilityOf(buttonDeleteBuild)).click();
        getWait(5).until(ExpectedConditions.visibilityOf(buttonSubmit)).click();

        return new FreestyleProjectStatusPage(getDriver());
    }
}
