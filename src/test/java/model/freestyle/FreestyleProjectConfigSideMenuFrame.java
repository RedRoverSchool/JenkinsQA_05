package model.freestyle;

import model.base.BaseConfigSideMenuFrame;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FreestyleProjectConfigSideMenuFrame extends BaseConfigSideMenuFrame<FreestyleProjectConfigPage> {

    @FindBy(xpath = "//button[@data-section-id='source-code-management']")
    private WebElement linkSourceCodeManagement;

    public FreestyleProjectConfigSideMenuFrame(WebDriver driver, FreestyleProjectConfigPage configPage) {
        super(driver, configPage);
    }

    public FreestyleProjectConfigPage clickLinkSourceCodeManagement() {
        linkSourceCodeManagement.click();

        return configPage;
    }
}