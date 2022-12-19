package model;

import model.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ManageOldDataPage extends BasePage{
    public ManageOldDataPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "main-panel")
    private WebElement mainPanel;

    public String getMainPanelNoticeText(){
        String[] actualText = mainPanel.getText().split("\n");

        return actualText[actualText.length - 1];
    }

}
