package model.multibranch_pipeline;

import model.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultibranchPipelineConfigPage extends HomePage {

    @FindBy(css = "button[type='submit']")
    private WebElement saveButton;

    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineStatusPage clickSaveButton() {
        saveButton.click();

        return new MultibranchPipelineStatusPage(getDriver());
    }
}
